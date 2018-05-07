package net.easipay.cbp.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import net.easipay.cbp.cache.CacheUtil;
import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.constant.EnumConstants.PrestoreOFLConstants.OFLBatchState;
import net.easipay.cbp.constant.EnumConstants.PrestoreOFLConstants.OFLCheckDealState;
import net.easipay.cbp.constant.EnumConstants.PrestoreOFLConstants.OFLDepositDealState;
import net.easipay.cbp.dao.ISacDepositBatchDao;
import net.easipay.cbp.dao.ISacDepositDetailDao;
import net.easipay.cbp.exception.OFLOnloadException;
import net.easipay.cbp.exception.SacException;
import net.easipay.cbp.model.SacChargeApply;
import net.easipay.cbp.model.SacDepositBatch;
import net.easipay.cbp.model.SacDepositDetail;
import net.easipay.cbp.model.form.NotifyPrestoreResultForm;
import net.easipay.cbp.model.form.PrestoreDetailForm;
import net.easipay.cbp.model.form.PrestoreResponseForm;
import net.easipay.cbp.service.INotifyOperResultToB2BService;
import net.easipay.cbp.service.ISacChargeApplyService;
import net.easipay.cbp.service.ISacDepositService;
import net.easipay.cbp.service.ISequenceCreatorService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.ExcelUtil;
import net.easipay.cbp.util.PersonUtil;
import net.easipay.cbp.util.StringUtil;
import net.easipay.cbp.util.XlsExporter;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacDepositService")
public class SacDepositServiceImpl implements ISacDepositService {

	public static final Logger logger = Logger
			.getLogger(SacDepositServiceImpl.class);

	@Autowired
	private ISacChargeApplyService sacChargeApplyService;

	@Autowired
	private ISacDepositDetailDao sacDepositDetailDao;

	@Autowired
	private ISacDepositBatchDao sacDepositBatchlDao;

	@Autowired
	private INotifyOperResultToB2BService notifyOperResultToB2BService;

	@Autowired
	private ISequenceCreatorService sequenceCreatorService;

	@Override
	public String deal(String serialNoListStr, List<PrestoreDetailForm> list)
			throws Exception {

		String msg = "";

		String batchId = sequenceCreatorService.getSerialNo(
				"SAC_DEPOSIT_BATCH_SEQ", 3);

		SecurityOperator person = PersonUtil.getUser();

		SacDepositBatch batch = new SacDepositBatch();
		// 组装预存批次信息准备入库
		Date date = new Date();
		batch.setCreateTime(date);
		batch.setBatchState(OFLBatchState.Init.code());// 未处理
		batch.setOperatorId(Long.parseLong(person.getMobile()));
		batch.setOperatorName(person.getEmail());
		batch.setOflDepositBatchId(Long.parseLong(batchId));
		batch.setCraccNodeCode("BOC0000");
		batch.setCraccBankName("中国银行");
		BigDecimal tmaount = new BigDecimal("0");
		Long count = 0L;
		String[] serialStr = serialNoListStr.split("\\|");
		Map<String,Object> avoidRepeatMap = new HashMap<String, Object>();
		StringBuffer bf = new StringBuffer("");
		for (String str : serialStr) {
			SacDepositDetail preDetail = new SacDepositDetail();
			String[] serialArr = str.split("\\_");
			int length = serialArr.length;
			String applyCode = "";
			String bankSerialNo = "";
			String cusName = "";
			if (length == 2) {
				bankSerialNo = serialArr[0];
				applyCode = serialArr[1];
				applyCode = applyCode.trim();
				applyCode = ToDBC(applyCode);
			} else if (length == 1) {
				bankSerialNo = serialArr[0];
			} else if (length == 3) {
				bankSerialNo = serialArr[0];
				applyCode = serialArr[1];
				if (StringUtils.isNotBlank(applyCode)) {
					applyCode = applyCode.trim();
					applyCode = ToDBC(applyCode);
				}
				cusName = serialArr[2];
			}
			if(StringUtils.isNotBlank(applyCode)&&avoidRepeatMap.containsKey(applyCode)){
				bf.append(bankSerialNo).append("|");
				continue;
			}
			
			Iterator<PrestoreDetailForm> it = list.iterator();
			while (it.hasNext()) {
				PrestoreDetailForm next = it.next();
				String serlNo = next.getBankSerialNo();
				if (serlNo.equals(bankSerialNo)) {
					Boolean flag = false;
					// 组装预存明细信息准备入库
					next.setApplyCode(applyCode);
					if (StringUtils.isNotBlank(cusName)) {
						next.setDraccName(cusName.trim());
					}
					String bankTrxDate = next.getBankTrxDate();
					org.springframework.beans.BeanUtils.copyProperties(next,
							preDetail, "bankTrxDate");
					preDetail.setBankTrxDate(DateUtil.convertStringToDate(
							"yyyy-MM-dd HH:mm:ss", bankTrxDate));
					String detailId = sequenceCreatorService.getSerialNo(
							"SAC_DEPOSIT_DETAIL_SEQ", 5);
					preDetail.setOflDepositId(Long.parseLong(detailId));
					preDetail.setOflDepositBatchId(Long.parseLong(batchId));
					preDetail.setCheckState(OFLCheckDealState.Init.code());
					if (StringUtils.isBlank(preDetail.getDraccNo())) {
						preDetail.setDraccNo("无");
					}
					// 以下非必填
					preDetail.setOperatorId(Long.parseLong(person.getMobile()));
					preDetail.setOperatorName(person.getEmail());
					preDetail.setOperTime(date);
					preDetail.setCreateTime(date);
					preDetail.setLastUpdateTime(date);
					preDetail.setCraccNodeCode("BOC0000");
					preDetail.setPayCurrency("CNY");
					// 检测银行流水是否重复
					if (StringUtils.isNotBlank(bankSerialNo)) {
						Map<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("bankSerialNo", bankSerialNo);
						List<SacDepositDetail> queryedList = findDepositDetailByParam(queryMap);
						if (queryedList != null && queryedList.size() > 0) {
							SacDepositDetail sacDepositDetail = queryedList
									.get(0);
							String state = sacDepositDetail.getDealState();
							if (!OFLDepositDealState.WaitMakeBatch.code()
									.equals(state)) {
								throw new SacException("999999",
										"银行流水号重复制作，流水号：" + bankSerialNo);
							} else {
								flag = true;
								Long id = sacDepositDetail.getOflDepositId();
								preDetail.setOflDepositId(id);
							}

						}
					}

					Boolean[] flagArr = validateDeposit(preDetail);// 自动匹配
					Boolean rtnFlag = flagArr[0];
					Boolean matchFlag = flagArr[1];
					if (rtnFlag) {
						String serial = preDetail.getBankSerialNo();
						msg = msg + serial + "|";
						break;
					}

					if (matchFlag) {
						preDetail.setDealState(OFLDepositDealState.InitSuc
								.code());
					} else {
						preDetail.setDealState(OFLDepositDealState.InitFail
								.code());
					}
					BigDecimal payAmount = preDetail.getPayAmount();
					tmaount = tmaount.add(payAmount);
					if (flag) {
						updateDepositDetailSpecial(preDetail);
					} else {
						saveDetail(preDetail);// 保存
					}
					count++;
					break;
				}
			}
			
			if(StringUtils.isNotBlank(applyCode)){
				avoidRepeatMap.put(applyCode, "");
			}
		}
		if (count > 0) {
			batch.setBatchTamount(tmaount);
			batch.setBatchTcount(count);
			saveBatch(batch);
		}
		if(StringUtils.isNotBlank(bf.toString())){
			if(StringUtils.isNotBlank(msg)){
				msg = msg.substring(0, msg.length()-1);
			}
			msg = msg+"_"+bf.toString().substring(0,bf.length()-1);
		}else{
			if(StringUtils.isNotBlank(msg)){
				msg = msg.substring(0, msg.length()-1);
			}
			msg = msg+"_";

		}
		return msg;
	}

	/**
	 * 全角转半角
	 * 
	 * @param input
	 *            String.
	 * @return 半角字符串
	 */
	public static String ToDBC(String input) {
		if (input.length() < input.getBytes().length) {

			char c[] = input.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] == '\u3000') {
					c[i] = ' ';
				} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
					c[i] = (char) (c[i] - 65248);

				}
			}
			String returnString = new String(c);
			return returnString;
		} else {
			return input;
		}
	}

	private void saveBatch(SacDepositBatch batch) {
		sacDepositBatchlDao.save(batch);

	}

	public void saveDetail(SacDepositDetail preDetail) {
		sacDepositDetailDao.save(preDetail);
	}

	public List<SacDepositDetail> findDepositDetailByParam(
			Map<String, Object> queryMap) {
		return sacDepositDetailDao.findDepositDetailByParam(queryMap);
	}

	public List<SacDepositDetail> findDepositDetailByParamForValid(
			Map<String, Object> queryMap) {
		return sacDepositDetailDao.findDepositDetailByParamForValid(queryMap);
	}

	public Boolean[] validateDeposit(SacDepositDetail preDetail) {
		// 8位码为空时直接返回
		Boolean[] obj = new Boolean[2];
		Boolean rtnFlag = false;
		Boolean matchFlag = false;
		if (StringUtils.isBlank(preDetail.getApplyCode())) {
			rtnFlag = true;
			obj[0] = rtnFlag;
			obj[1] = matchFlag;
			preDetail.setDealMemo("预存申请码错误");
			return obj;
		}
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("applyCode", preDetail.getApplyCode());
		List<SacChargeApply> list = sacChargeApplyService
				.selectApplyByParam(queryMap);

		if (list == null || list.size() == 0) {
			rtnFlag = true;
			obj[0] = rtnFlag;
			obj[1] = matchFlag;
			preDetail.setDealMemo("预存申请码错误");
			return obj;
		}
		SacChargeApply ca = list.get(0);
		if (ca.getApplyState().equals(Constants.CHARGE_STATE_1)
				|| ca.getApplyState().equals(Constants.CHARGE_STATE_2)) {
			rtnFlag = true;
			obj[0] = rtnFlag;
			obj[1] = matchFlag;
			preDetail.setDealMemo("预存申请码已使用");
			return obj;
		}

		int i = 0;// 1为错误
		String msg = "";
		if (ca.getPayAmount().compareTo(preDetail.getPayAmount()) != 0) {
			msg += "金额不符;";
			i = 1;
		}
		if (!ca.getApplyOrgName().equals(preDetail.getDraccName())) {
			msg += "公司名称不符;";
			i = 1;
		}
		/*
		 * Date date = preDetail.getBankTrxDate() == null ? new Date() :
		 * preDetail.getBankTrxDate(); if (date.after(ca.getExpiredDate())) {
		 * msg += "超过有效期;"; i = 1; }
		 */
		preDetail.setChargeApplyId(ca.getChargeApplyId());
		if (1 == i) {
			obj[0] = rtnFlag;
			obj[1] = matchFlag;
			preDetail.setDealMemo(msg);
			return obj;
		}
		obj[0] = rtnFlag;
		matchFlag = true;
		obj[1] = matchFlag;
		return obj;
	}

	@Override
	public int validRepeat(String bankSerialNo) {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("bankSerialNo", bankSerialNo);
		queryMap.put("dealStateN", OFLDepositDealState.WaitMakeBatch.code());
		return sacDepositDetailDao.getCountsByParam(queryMap);

	}

	@Override
	public int getBatchCountsByParam(Map<String, Object> queryMap) {
		return sacDepositBatchlDao.getBatchCountsByParam(queryMap);
	}

	@Override
	public List<SacDepositBatch> getDepositBatchByParam(
			Map<String, Object> queryMap, int pageNo, int pageSize) {
		return sacDepositBatchlDao.getDepositBatchByParam(queryMap, pageNo,
				pageSize);
	}

	@Override
	public void passConfirm(SacDepositDetail detail,
			SacDepositBatch batch,int countDff,BigDecimal amountDff) {
		String applyCode = detail.getApplyCode();
		SacChargeApply chargeApply = new SacChargeApply();
		SacChargeApply sacChargeApply = null;
		String dealState = detail.getDealState();
		//校验
		if(OFLDepositDealState.InitSuc.code().equals(dealState)){
			
			Long chargeApplyId = detail.getChargeApplyId();
			if(chargeApplyId!=null){
				chargeApply.setChargeApplyId(chargeApplyId);
				Map<String,Object> queryMap = new HashMap<String, Object>();
				queryMap.put("chargeApplyId", chargeApplyId);
				List<SacChargeApply> chargeApplyList = sacChargeApplyService.selectApplyByParam(queryMap);
				if(chargeApplyList==null || chargeApplyList.size()==0){
					detail.setDealState(OFLDepositDealState.WaitMakeBatch.code());
					detail.setDealMemo("预存交易无对应的申请信息");
					updateDepositDetailForReMake(detail);
					logger.error("预存交易无对应的申请信息");
					return;
				}
				sacChargeApply = chargeApplyList.get(0);
				String applyState = sacChargeApply.getApplyState();
				if(!Constants.CHARGE_STATE_0.equals(applyState)){
					detail.setDealState(OFLDepositDealState.WaitMakeBatch.code());
					updateDepositDetailForReMake(detail);
					detail.setDealMemo("预存申请信息已经被匹配核销");
					logger.error("预存申请信息已经被匹配核销，请重新针对交易号为["+detail.getBankSerialNo()+"]的预存交易制作批次");
					return;
				}
				
			}
		}
		
		
		if(!applyCode.contains("Z")&&applyCode.trim().length()==8){
			//发送至B2B系统
			notifyToB2B(detail,batch,countDff,amountDff,sacChargeApply,chargeApply);
		}else{
			//发送至东方付系统
			notifyToDff(detail,batch,countDff,amountDff,sacChargeApply);
		}

	}


	private void notifyToDff(SacDepositDetail detail, SacDepositBatch batch,
			int countDff, BigDecimal amountDff, SacChargeApply sacChargeApply) {
		String dealState = detail.getDealState();
		List<NotifyPrestoreResultForm> forms = new ArrayList<NotifyPrestoreResultForm>();
		if(OFLDepositDealState.InitSuc.code().equals(dealState)){
			NotifyPrestoreResultForm form = new NotifyPrestoreResultForm();
			form.setApplyCode(detail.getApplyCode());
			form.setBankSerialNo(detail.getBankSerialNo());
			form.setBankTrxDate(DateUtil.getFomateDate(
					detail.getBankTrxDate(), "yyyyMMdd"));
			form.setBatchTamount(amountDff+"");
			form.setBatchTicount(countDff+"");
			form.setCraccNodeCode(batch.getCraccNodeCode());
			form.setOflDepositSerialNo(detail.getOflDepositId() + "");
			form.setOperType(Constants.PRESTORE_TYPE_01);
			form.setPayTamt(detail.getPayAmount() + "");
			form.setPyerAccId(detail.getDraccNo());
			form.setPyerAccNm(detail.getDraccName());
			form.setPyerBnkNm(detail.getDraccBankName());
			form.setPyerIdNo(sacChargeApply.getApplyDbtCode());
			form.setMsgSndrSysNdCd("SAC0000");
			forms.add(form);
		}
		
		
		
		if(forms.size()>0){
			//调用收单接口
			JwsResult result = notifyOperResultToB2BService.notifyPreStoreResultDff(forms);

			
			//结果处理
			if (result.isSuccess()) {
				List<PrestoreResponseForm> list = result.getList("offlineRechargeConfirmResponse",PrestoreResponseForm.class);

				String oflDepositId = detail.getOflDepositId() + "";
				Long chargeApplyId = detail.getChargeApplyId();
				SecurityOperator person = PersonUtil.getUser();
				Date date = new Date();
				detail.setAuditorId(Long.parseLong(person.getMobile()));
				detail.setAuditorName(person.getEmail());
				detail.setAuditTime(date);
				for (PrestoreResponseForm form : list) {
					if (oflDepositId.equals(form.getOflDepositSerialNo())) {
						detail.setTrxSerialNo(form.getTrxSerialNo());
						break;
					}
				}
				detail.setDealState(OFLDepositDealState.Succeed.code());
				detail.setCheckState(OFLCheckDealState.Succeed.code());
				SacChargeApply apply = new SacChargeApply();
				apply.setChargeApplyId(chargeApplyId);
				apply.setApplyState(Constants.CHARGE_STATE_1);
				apply.setLastUpdateTime(date);
				updateChargeApply(apply);
				updateDepositDetail(detail);
			} else {
				detail.setDealState(OFLDepositDealState.WaitMakeBatch.code());
				detail.setRtnCode(result.getCode());
				detail.setRtnMsg(result.getMessage());
				updateDepositDetailForReMake(detail);
				return;
			}
		}else{
			SecurityOperator person = PersonUtil.getUser();
			Date date = new Date();

			if (OFLDepositDealState.InitFail.code().equals(dealState)) {
				detail.setDealState(OFLDepositDealState.FailPendingProcess.code());
				detail.setAuditorId(Long.parseLong(person.getMobile()));
				detail.setAuditorName(person.getEmail());
				detail.setAuditTime(date);
				updateDepositDetail(detail);
			}
		
		}
		
	
		
	}

	private void notifyToB2B(SacDepositDetail detail, SacDepositBatch batch,
			int countDff, BigDecimal amountDff, SacChargeApply sacChargeApply,
			SacChargeApply chargeApply) {

		String dealState = detail.getDealState();
		
		//调用接口
		Map <String,String> result = null;
		if("0".equals(Constants.INTERFACE_SWITCH)){
			//生产环境
			result = new HashMap<String, String>();
			result.put("rtnCode", "000000");
			result.put("trxSerialNo", detail.getOflDepositId()+"");
		}else if("1".equals(Constants.INTERFACE_SWITCH)){
			//测试 开发环境
			result = 
					notifyOperResultToB2BService.notifyPreStoreResult(detail,batch,sacChargeApply,countDff,amountDff);
		}
	
		if("999999".equals(result.get("rtnCode"))||"999990".equals(result.get("rtnCode"))){
			//需问清B2B那边什么情况下会返回错误
			detail.setDealState(OFLDepositDealState.WaitMakeBatch.code());
			detail.setRtnMsg(result.get("rtnInfo"));
			updateDepositDetailForReMake(detail);
			return;
		}else if("000000".equals(result.get("rtnCode"))){
			String trxSerialNo = result.get("trxSerialNo");//预存交易流水号
			detail.setTrxSerialNo(trxSerialNo);
		}
		if(OFLDepositDealState.InitFail.code().equals(dealState)){
			detail.setDealState(OFLDepositDealState.FailPendingProcess.code());
		}else if(OFLDepositDealState.InitSuc.code().equals(dealState)){
			detail.setDealState(OFLDepositDealState.Succeed.code());
			detail.setCheckState(OFLCheckDealState.Succeed.code());
		}
		SecurityOperator person = PersonUtil.getUser();
		Date date = new Date();
		detail.setAuditorId(Long.parseLong(person.getMobile()));
		detail.setAuditorName(person.getEmail());
		detail.setAuditTime(date);
		
		if(sacChargeApply!=null){
			chargeApply.setApplyState(Constants.CHARGE_STATE_1);
			chargeApply.setLastUpdateTime(date);
			updateChargeApply(chargeApply);
		}
		updateDepositDetail(detail);
	
		
	}

	@Override
	public void passFailue(SacDepositDetail detail) {

		SecurityOperator person = PersonUtil.getUser();
		Date date = new Date();
		detail.setAuditorId(Long.parseLong(person.getMobile()));
		detail.setAuditorName(person.getEmail());
		detail.setAuditTime(date);
		detail.setDealState(OFLDepositDealState.WaitMakeBatch.code());
		detail.setChargeApplyId(null);
		detail.setOflDepositBatchId(null);
		updateDepositDetail(detail);
		updateDepositDetailForReMake(detail);
	}

	@Override
	public void updateDepositDetail(SacDepositDetail detail) {
		sacDepositDetailDao.update(detail);
	}

	public void updateDepositDetailSpecial(SacDepositDetail detail) {
		sacDepositDetailDao.updateDepositDetailSpecial(detail);
	}

	private void updateDepositDetailForReMake(SacDepositDetail detail) {
		sacDepositDetailDao.updateDepositDetailForReMake(detail);
	}

	private void updateChargeApply(SacChargeApply apply) {
		sacChargeApplyService.updateChargeApply(apply);
	}

	@Override
	public void updateDepositBatch(SacDepositBatch batch) {
		SecurityOperator person = PersonUtil.getUser();
		batch.setAuditTime(new Date());
		batch.setAuditorName(person.getEmail());
		batch.setAuditorId(Long.parseLong(person.getMobile()));
		sacDepositBatchlDao.update(batch);

	}

	@Override
	public Integer getDepositDetailCountsByParam(Map<String, Object> queryMap) {
		return sacDepositDetailDao.getCountsByParam(queryMap);
	}

	@Override
	public List<SacDepositDetail> getDepositDetailByPaging(
			Map<String, Object> queryMap, int pageNo, int pageSize) {
		int start = (pageNo - 1) * pageSize;
		int end = pageNo * pageSize;
		queryMap.put("start", start);
		queryMap.put("end", end);
		return sacDepositDetailDao.getDepositDetailByPaging(queryMap);
	}

	@Override
	public Integer getMunualMatchCheckCounts(Map<String, Object> queryMap) {
		return sacDepositDetailDao.getMunualMatchCheckCounts(queryMap);
	}

	@Override
	public List<Map<String, Object>> getMunualMatchCheckInfo(
			Map<String, Object> queryMap, int pageNo, int pageSize) {
		int start = (pageNo - 1) * pageSize;
		int end = pageNo * pageSize;
		queryMap.put("start", start);
		queryMap.put("end", end);
		return sacDepositDetailDao.getMunualMatchCheckInfo(queryMap);
	}

	@Override
	public String checkPassForManualMatch(SacDepositDetail detail) {
		SecurityOperator user = PersonUtil.getUser();
		detail.setAuditorId(Long.parseLong(user.getMobile()));
		detail.setAuditorName(user.getEmail());
		detail.setAuditTime(new Date());
		detail.setDealState(OFLDepositDealState.Succeed.code());

		Map<String, Object> applyMap = new HashMap<String, Object>();
		applyMap.put("chargeApplyId", detail.getChargeApplyId());
		List<SacChargeApply> applyList = sacChargeApplyService
				.selectApplyByParam(applyMap);
		SacChargeApply sacChargeApply = applyList.get(0);
		sacChargeApply.setApplyState(Constants.CHARGE_STATE_2);// 手动核销
		sacChargeApply.setLastUpdateTime(new Date());
		String applyCode = sacChargeApply.getApplyCode();
		if(applyCode.contains("Z")||applyCode.trim().length()>8){
			//通知到东方付系统
			List<NotifyPrestoreResultForm> details = new ArrayList<NotifyPrestoreResultForm>();
			
			NotifyPrestoreResultForm form = new NotifyPrestoreResultForm();
			form.setApplyCode(sacChargeApply.getApplyCode());
			form.setBankSerialNo(detail.getBankSerialNo());
			form.setBankTrxDate(DateUtil.getFomateDate(
					detail.getBankTrxDate(), "yyyyMMdd"));
			form.setBatchTamount(detail.getPayAmount()+"");
			form.setBatchTicount(1+"");
			form.setCraccNodeCode(detail.getCraccNodeCode());
			form.setOflDepositSerialNo(detail.getOflDepositId() + "");
			form.setOperType(Constants.PRESTORE_TYPE_02);
			form.setPayTamt(detail.getPayAmount() + "");
			form.setPyerAccId(detail.getDraccNo());
			form.setPyerAccNm(detail.getDraccName());
			form.setPyerBnkNm(detail.getDraccBankName());
			form.setPyerIdNo(sacChargeApply.getApplyDbtCode());
			form.setMsgSndrSysNdCd("SAC0000");
			details.add(form);
			
			JwsResult jwsResult = notifyOperResultToB2BService
					.notifyPreStoreResultDff(details);
			
			if(jwsResult.isSuccess()){
				sacChargeApplyService.updateChargeApply(sacChargeApply);
				detail.setCheckState(OFLCheckDealState.Succeed.code());
				PrestoreResponseForm bean = jwsResult.getListFirst("offlineRechargeConfirmResponse", PrestoreResponseForm.class);
				String trxSerialNo = bean.getTrxSerialNo();
				detail.setTrxSerialNo(trxSerialNo);
				sacDepositDetailDao.update(detail);
				return "";
			}else{
				return jwsResult.getMessage();
			}
		}else{
			//notifyToB2B
			Map <String,String> result = null;
			if("0".equals(Constants.INTERFACE_SWITCH)){
				//测试 开发环境
				result = new HashMap<String, String>();
				result.put("rtnCode", "000000");
				result.put("trxSerialNo", detail.getOflDepositId()+"");
			}else if("1".equals(Constants.INTERFACE_SWITCH)){
				//生产环境
				result =notifyOperResultToB2BService.notifyPreStoreResultForMunualMatch(detail,applyList.get(0));
			}
			if("999990".equals(result.get("rtnCode"))){
				//需问清B2B那边什么情况下会返回错误
				/*detail.setDealState(OFLDepositDealState.WaitMakeBatch.code());
				detail.setDealMemo(result.get("rtnInfo"));
				updateDepositDetailForReMake(detail);*/
				return result.get("rtnInfo");
			}else if("999999".equals(result.get("rtnCode"))){
				return result.get("rtnInfo");
			}else if("000000".equals(result.get("rtnCode"))){
				sacChargeApplyService.updateChargeApply(sacChargeApply);
				detail.setCheckState(OFLCheckDealState.Succeed.code());
				String trxSerialNo = result.get("trxSerialNo");
				detail.setTrxSerialNo(trxSerialNo);
				sacDepositDetailDao.update(detail);
				return "";
			}else{
				return "系统错误,请联系管理员!";
			}
		}
		
	}

	@Override
	public void checkFailueForManualMatch(SacDepositDetail detail) {
		SecurityOperator user = PersonUtil.getUser();
		detail.setAuditorId(Long.parseLong(user.getMobile()));
		detail.setAuditorName(user.getEmail());
		detail.setAuditTime(new Date());
		detail.setDealState(OFLDepositDealState.FailPendingProcess.code());
		detail.setDealMemo("手工销账复核不通过");
		detail.setChargeApplyId(null);
		sacDepositDetailDao.updateDepositDetailForReMake(detail);

	}

	@Override
	public String spdbBatchMake(String str) throws Exception {
		String msg = "";

		String batchId = sequenceCreatorService.getSerialNo(
				"SAC_DEPOSIT_BATCH_SEQ", 3);

		SecurityOperator person = PersonUtil.getUser();

		SacDepositBatch batch = new SacDepositBatch();
		// 组装预存批次信息准备入库
		Date date = new Date();
		batch.setCreateTime(date);
		batch.setBatchState(OFLBatchState.Init.code());// 未处理
		batch.setOperatorId(Long.parseLong(person.getMobile()));
		batch.setOperatorName(person.getEmail());
		batch.setOflDepositBatchId(Long.parseLong(batchId));
		batch.setCraccNodeCode("SPDB000");
		batch.setCraccBankName("浦发银行");
		BigDecimal tmaount = new BigDecimal("0");
		Long count = 0L;
		String[] trs = str.split("\\|");
		for (String tr : trs) {
			SacDepositDetail preDetail = new SacDepositDetail();
			String[] tds = tr.split("\\_");
			String tdStr0 = tds[0];// 付款银行账号
			String tdStr1 = tds[1];// 付款公司名称
			String tdStr2 = tds[2];// 付款银行名称
			String tdStr3 = tds[3];// 金额
			String tdStr4 = tds[4];// 到账时间
			int length = tds.length;
			String applyCode = "";
			if (length == 6) {
				String tdStr5 = tds[5];// 八位码
				applyCode = tdStr5.trim();
				applyCode = ToDBC(applyCode);
			}

			// 组装预存明细信息准备入库
			preDetail.setDraccNo(StringUtils.isBlank(tdStr0) ? "无" : tdStr0);
			preDetail.setPayAmount(new BigDecimal(tdStr3));
			preDetail.setApplyCode(applyCode);
			preDetail.setBankTrxDate(DateUtil.convertStringToDate("yyyy-MM-dd",
					tdStr4 + " 00:00:00"));
			String detailId = sequenceCreatorService.getSerialNo(
					"SAC_DEPOSIT_DETAIL_SEQ", 5);
			preDetail.setOflDepositId(Long.parseLong(detailId));
			preDetail.setOflDepositBatchId(Long.parseLong(batchId));
			preDetail.setCheckState(OFLCheckDealState.Init.code());
			// 以下非必填
			preDetail.setDraccName(tdStr1);
			preDetail.setDraccBankName(tdStr2);
			preDetail.setOperatorId(Long.parseLong(person.getMobile()));
			preDetail.setOperatorName(person.getEmail());
			preDetail.setOperTime(date);
			preDetail.setCreateTime(date);
			preDetail.setLastUpdateTime(date);
			preDetail.setPayCurrency("CNY");
			preDetail.setCraccNodeCode("SPDB000");

			Boolean[] flagArr = validateDeposit(preDetail);// 自动匹配
			Boolean rtnFlag = flagArr[0];
			Boolean matchFlag = flagArr[1];
			if (rtnFlag) {
				String code = preDetail.getApplyCode();
				msg = msg + code + "|";
				continue;
			}

			if (matchFlag) {
				preDetail.setDealState(OFLDepositDealState.InitSuc.code());
			} else {
				preDetail.setDealState(OFLDepositDealState.InitFail.code());
			}
			BigDecimal payAmount = preDetail.getPayAmount();
			tmaount = tmaount.add(payAmount);
			saveDetail(preDetail);// 保存
			count++;

		}
		if (count > 0) {
			batch.setBatchTamount(tmaount);
			batch.setBatchTcount(count);
			saveBatch(batch);
		}
		return msg;

	}

	@Override
	public void spdbCheckFailue(SacDepositBatch bt) {
		Long oflDepositBatchId = bt.getOflDepositBatchId();
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("oflDepositBatchId", oflDepositBatchId);
		sacDepositDetailDao.deleteDetailByBatchId(deleteMap);

		sacDepositBatchlDao.deleteBatchByBatchId(deleteMap);

	}

	@Override
	public void exportPrestoreDetailToExcel(HttpServletResponse response,
			Map<String, Object> queryMap, List<SacDepositDetail> detailList,
			String tempFile) throws IOException {

		Map<String, Object> statMap = new HashMap<String, Object>();
		statMap.put("query", queryMap);
		statMap.put("presotreList", detailList);

		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			// 导出数据To OutputStream
			XlsExporter.export(statMap, "prestoreDetail", tempFile, stream);

			response.setContentType("application/vnd.ms-excel");
			// 下载文件名
			String downloadFileName = "PrestoreDetail"
					+ new SimpleDateFormat("yyyy-MM-dd_HHmmss")
							.format(new Date()) + ".xls";
			response.addHeader("Content-Disposition", "attachment;filename="
					+ downloadFileName);
			response.addHeader("Content-Length", "" + stream.size());
			response.getOutputStream().write(stream.toByteArray());
		} catch (Exception e) {
			logger.error("Fail to download PrestoreDetail", e);
			throw new RuntimeException("Fail to download PrestoreDetail", e);
		} finally {
			stream.close();
		}
	}

	@Override
	public Object[] readOflXls(String oflExcelFileName,
			InputStream inputStream, SecurityOperator person) throws Exception {
		Object[] obj = new Object[2];
		String msg = "";
		Workbook book = Workbook.getWorkbook(inputStream);
		// 第一个sheet
		Sheet sheet = book.getSheet(0);// ExcelUtil.readSheet(inputStream,0);
		int columns = sheet.getColumns();
		if (columns < 8) {
			throw new SacException("999990", "格式错误！列数不足");
		}
		// 第一行第二列批次号
		String batchSerNo = ExcelUtil.readCell(sheet, 1, 0);
		if (StringUtils.isBlank(batchSerNo) || batchSerNo.length() != 11) {
			throw new SacException("999990", "批次号不存在或格式错误！");
		}
		// 检查是否存在
		Map<String, Object> valMap = new HashMap<String, Object>();
		valMap.put("oflDepositBatchId", batchSerNo);
		List<SacDepositBatch> depBatchList = sacDepositBatchlDao
				.getDepositBatchByParam(valMap, 1, 10);

		if (depBatchList != null && depBatchList.size() > 0) {
			SacDepositBatch depBatch = depBatchList.get(0);
			if (OFLBatchState.ReviewPass.code()
					.equals(depBatch.getBatchState())) {
				throw new SacException("999990", "该批次已存在，不能重复提交！");
			}
			// 先删掉原来的批次
			sacDepositBatchlDao.deleteBatchByBatchId(valMap);
			sacDepositDetailDao.deleteDetailByBatchId(valMap);
		}
		// 新建批次对象
		SacDepositBatch oflbatch = new SacDepositBatch();
		oflbatch.setOflDepositBatchId(Long.parseLong(batchSerNo));
		oflbatch.setCreateTime(new Date());
		oflbatch.setBatchState(OFLBatchState.Init.code());
		oflbatch.setOperatorId(Long.parseLong(person.getMobile()));
		oflbatch.setOperatorName(person.getLoginName());

		Map<String, Object> bankTypeMap = CacheUtil
				.getCacheByTypeToMap(Constants.BANK_TYPE);
		// 第二行第二列收款银行代码
		String bankCode = ExcelUtil.readCell(sheet, 1, 1);
		if (StringUtils.isBlank(bankCode) || !bankTypeMap.containsKey(bankCode)) {
			throw new SacException("999990", "收款银行代码错误！");
		}
		/*
		 * Bank bank = bankService.findByBankCode(bankCode); if(bank==null){
		 * throw new OFLOnloadException("收款银行代码错误！"); }
		 */
		oflbatch.setCraccNodeCode(bankCode);
		// 第二行第四列收款银行名称
		String bankName = ExcelUtil.readCell(sheet, 4, 1);
		oflbatch.setCraccBankName(bankName);
		// 保存获得id
		// oflbatch = sacDepositBatchlDao.save(oflbatch);
		int i = 3;// 初始化从第四行还是读取详细信息
		BigDecimal tmaount = new BigDecimal(0);
		Long count = 0L;
		Set<String> seriaSet = new HashSet<String>();
		Set<String> applyCodeSet = new HashSet<String>();
		for (i = 3; i < sheet.getRows(); i++) {
			SacDepositDetail dep = new SacDepositDetail();// 详细信息
			// 前4向必填检测
			// ----序列号
			String serialNo = ExcelUtil.readCell(sheet, 0, i);
			if (StringUtils.isBlank(serialNo) || serialNo.length() != 13) {
				if (i == 3) {
					throw new SacException("999990", "第" + (i + 1) + "行序列号错误");
				}
				break;// 至少有一条记录，如果发下下一行格式不同一认为记录结束
			}
			if (!serialNo.startsWith(batchSerNo)) {
				throw new SacException("999990", "序列号不是以批次号开头，序列号：" + serialNo);
			}
			if (seriaSet.contains(serialNo)) {
				throw new SacException("999990", "序列号重复，序列号：" + serialNo);
			} else {
				seriaSet.add(serialNo);
				dep.setOflDepositId(Long.parseLong(serialNo));
			}
			// ----8位码
			String applyCode = ExcelUtil.readCell(sheet, 1, i);
			if (StringUtil.isBlank(applyCode) || applyCode.length() != 8) {
				throw new OFLOnloadException("第" + (i + 1) + "行8位码为空或不正确");
			}
			if (applyCodeSet.contains(applyCode)) {
				throw new SacException("999990", "第" + (i + 1) + "行8位码重复");
			} else {
				applyCodeSet.add(applyCode);
				dep.setApplyCode(applyCode);
			}
			// ----金额
			BigDecimal payAmount;
			try {
				payAmount = new BigDecimal(ExcelUtil.readCell(sheet, 2, i));
			} catch (Exception e) {
				throw new SacException("999990", "第" + (i + 1) + "行金额为空或不正确");
			}
			dep.setPayAmount(payAmount);
			// ----企业名称
			String draccName = ExcelUtil.readCell(sheet, 3, i);
			if (StringUtils.isBlank(draccName) || draccName.length() > 80) {
				throw new SacException("999990", "第" + (i + 1)
						+ "行企业名称为空或格式不正确");
			}
			dep.setDraccName(draccName);
			// 以下非必填
			dep.setOflDepositBatchId(oflbatch.getOflDepositBatchId());
			dep.setDraccBankName(ExcelUtil.readCell(sheet, 4, i));
			dep.setBankTrxDate(DateUtil.convertStringToDate(ExcelUtil.readCell(
					sheet, 5, i)));
			dep.setDraccNo(ExcelUtil.readCell(sheet, 6, i));
			dep.setBankSerialNo(ExcelUtil.readCell(sheet, 7, i));
			dep.setOperatorId(Long.parseLong(person.getMobile()));
			dep.setOperatorName(person.getLoginName());
			dep.setPayCurrency("CNY");
			Date d = new Date();
			dep.setOperTime(d);
			dep.setCreateTime(d);
			dep.setLastUpdateTime(d);
			dep.setBatchSerialNo(batchSerNo);
			// 检测银行流水是否重复

			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("bankSerialNo", dep.getBankSerialNo());
			List<SacDepositDetail> queryedList = findDepositDetailByParam(queryMap);
			if (queryedList != null && queryedList.size() > 0) {
				throw new SacException("999990", "银行流水号重复，流水号："
						+ dep.getBankSerialNo());
			}
			// 核对信息
			Boolean[] flagArr = validateDeposit(dep);// 自动匹配
			Boolean rtnFlag = flagArr[0];
			Boolean matchFlag = flagArr[1];
			if (rtnFlag) {
				msg = msg + "第" + (i + 1) + "行,";
				continue;
			}

			if (matchFlag) {
				dep.setDealState(OFLDepositDealState.InitSuc.code());
			} else {
				dep.setDealState(OFLDepositDealState.InitFail.code());
			}
			BigDecimal amount = dep.getPayAmount();
			tmaount = tmaount.add(amount);
			saveDetail(dep);// 保存
			count++;
		}
		if (count > 0) {
			oflbatch.setBatchTamount(tmaount);
			oflbatch.setBatchTcount(count);
			saveBatch(oflbatch);
		}

		obj[0] = msg;
		obj[1] = oflbatch;
		return obj;
	}

}
