package net.easipay.cbp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.easipay.cbp.dao.ISacB2CExrefundApplyDao;
import net.easipay.cbp.dao.ISacCusParameterDao;
import net.easipay.cbp.dao.ISacOtrxInfoDao;
import net.easipay.cbp.dao.ISacRefundCommandDao;
import net.easipay.cbp.model.SacB2CExrefundApply;
import net.easipay.cbp.model.SacCusParameter;
import net.easipay.cbp.model.SacOtrxInfo;
import net.easipay.cbp.model.SacRefundCommand;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ISacRefundCmdService;
import net.easipay.cbp.util.Utils;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class SacRefundCmdServiceImpl implements ISacRefundCmdService {

  private static final Logger logger = Logger.getLogger(SacRefundCmdServiceImpl.class);

  @Autowired
  private ISacOtrxInfoDao otrxInfoDao;

  @Autowired
  private ISacRefundCommandDao b2bRefundCommandDao;

  @Autowired
  private ISacB2CExrefundApplyDao b2cExrefundApplyDao;

  @Autowired
  private ISacCusParameterDao sacCusParameterDao;

  @Override
  public void insertB2BRefundCmd() {
    //设置扫描时间
    String beginDate = Utils.convertDate(new Date(), -1);//T-1
    String endDate = DateUtil.formatDate(new Date(), "yyyyMMdd");//T
    //扫描原始交易表
    Map paramMap = new HashMap();
    paramMap.put("beginDate", beginDate);
    paramMap.put("endDate", endDate);
    paramMap.put("trxType", "1316");
    List<SacOtrxInfo> otrxInfoList = otrxInfoDao.selectOtrxInfoList(paramMap);
    if (otrxInfoList != null && otrxInfoList.size() > 0) {
      //查询B2B退款指令表该日期段的数据
      List<SacRefundCommand> b2bRefundCommandList = b2bRefundCommandDao.selectSacRefundCommandList(paramMap);
      for (SacOtrxInfo otrxInfo : otrxInfoList) {
        boolean execFlag = true;
        for (SacRefundCommand b2bCmd : b2bRefundCommandList) {
          if (otrxInfo.getTrxSerialNo().equals(b2bCmd.getTrxSerialNo())) {
            execFlag = false;
            break;
          }
        }
        if (execFlag) {
          try {
            b2bRefundCommandDao.insertRefundCommand(installB2BRefundCmd(otrxInfo));
          } catch (Exception e) {
            logger.error("B2B退款指令插入执行出错！流水号：" + otrxInfo.getTrxSerialNo() + "。报错信息：" + e.getMessage());
            e.printStackTrace();
          }
        }
      }
    }
  }

  @Override
  public void insertB2CRefundCmd() {
    //设置扫描时间
    String beginDate = Utils.convertDate(new Date(), -1);//T-1
    String endDate = DateUtil.formatDate(new Date(), "yyyyMMdd");//T
    //扫描原始交易表
    Map paramMap = new HashMap();
    paramMap.put("beginDate", beginDate);
    paramMap.put("endDate", endDate);
    paramMap.put("trxType", "3303");
    List<SacOtrxInfo> otrxInfoList = otrxInfoDao.selectOtrxInfoList(paramMap);
    if (otrxInfoList != null && otrxInfoList.size() != 0) {
      //查询B2C退款指令表该日期段的数据
      List<SacB2CExrefundApply> b2cExrefundApplyList = b2cExrefundApplyDao.selectSacB2CExrefundApplyList(paramMap);
      for (SacOtrxInfo otrxInfo : otrxInfoList) {
        boolean execFlag = true;
        for (SacB2CExrefundApply b2cCmd : b2cExrefundApplyList) {
          if (otrxInfo.getTrxSerialNo().equals(b2cCmd.getRefundSerialNo())) {
            execFlag = false;
            break;
          }
        }
        if (execFlag) {
          try {
            b2cExrefundApplyDao.insertB2CExrefundApply(installB2CRefundCmd(otrxInfo));
          } catch (Exception e) {
            logger.error("B2C退款指令插入执行出错！流水号：" + otrxInfo.getTrxSerialNo() + "。报错信息：" + e.getMessage());
            e.printStackTrace();
          }
        }
      }
    }
  }

  @Override
  public void updateB2BRefundCmd() {
    //设置扫描时间
    String beginDate = Utils.convertDate(new Date(), -1);//T-1
    String endDate = DateUtil.formatDate(new Date(), "yyyyMMdd");//T
    //扫描B2B划款指令表
    Map paramMap = new HashMap();
    paramMap.put("beginDate", beginDate);
    paramMap.put("endDate", endDate);
    paramMap.put("trxState", "2");//待更新状态
    List<SacRefundCommand> refundCommandList = b2bRefundCommandDao.selectSacRefundCommandList(paramMap);
    if (refundCommandList != null && refundCommandList.size() > 0) {
      //扫描原始交易表
      paramMap.put("trxType", "1613");
      List<String> trxSerialNoList = new ArrayList<String>();
      for (SacRefundCommand cmd : refundCommandList) {
        trxSerialNoList.add(cmd.getTrxSerialNo());
      }
      paramMap.put("trxSerialNoList", trxSerialNoList);
      paramMap.remove("beginDate");
      paramMap.remove("endDate");
      List<SacOtrxInfo> otrxInfoList = otrxInfoDao.selectOtrxInfoList(paramMap);
      if (otrxInfoList != null && otrxInfoList.size() > 0) {
        for (SacOtrxInfo otrxInfo : otrxInfoList) {
          try {
            b2bRefundCommandDao.updateSacRefundCommand(installUpdateB2BRefundCmd(otrxInfo));
          } catch (Exception e) {
            logger.error("B2B退款指令更新操作执行出错！流水号：" + otrxInfo.getTrxSerialNo() + "。报错信息：" + e.getMessage());
            e.printStackTrace();
          }
        }
      }
    }
  }

  //组装b2b退款指令
  private SacRefundCommand installB2BRefundCmd(SacOtrxInfo otrxInfo) {
    SacRefundCommand b2bRefundCmd = new SacRefundCommand();
    b2bRefundCmd.setWpRefundId(SequenceCreatorUtil.getTableId());
    b2bRefundCmd.setTrxSerialNo(otrxInfo.getTrxSerialNo());
    b2bRefundCmd.setOtrxSerialNo(otrxInfo.getOtrxSerialNo());
    b2bRefundCmd.setCreateTime(new Date());
    b2bRefundCmd.setPayAmount(otrxInfo.getPayAmount());
    b2bRefundCmd.setCrtCode(otrxInfo.getCraccCardId());
    b2bRefundCmd.setBankNodeCode(otrxInfo.getDraccNodeCode());
    b2bRefundCmd.setTrxState("1");
    b2bRefundCmd.setRtrxSerialNo(null);
    b2bRefundCmd.setAuditState("00");
    b2bRefundCmd.setLastUpdateTime(new Date());
    b2bRefundCmd.setCraccNo(otrxInfo.getCraccNo());
    b2bRefundCmd.setCraccName(otrxInfo.getCraccName());
    b2bRefundCmd.setCraccBankBranch(otrxInfo.getCraccBankName());
    b2bRefundCmd.setPayCurrency(otrxInfo.getPayCurrency());
    b2bRefundCmd.setExpBatch(null);
    return b2bRefundCmd;
  }

  //组装b2c退款指令
  private SacB2CExrefundApply installB2CRefundCmd(SacOtrxInfo otrxInfo) {
    SacB2CExrefundApply b2cRefundCmd = new SacB2CExrefundApply();
    b2cRefundCmd.setExrefundApplyId(SequenceCreatorUtil.getTableId());
    b2cRefundCmd.setRefundSerialNo(otrxInfo.getTrxSerialNo());
    b2cRefundCmd.setRefundTime(otrxInfo.getCreateTime());

    SacCusParameter sacCusParameter = new SacCusParameter();
    sacCusParameter.setCusNo(otrxInfo.getDraccCusCode());
    sacCusParameter.setSacCurrency("CNY");
    sacCusParameter = sacCusParameterDao.selectSacCusParameterById(sacCusParameter);
    b2cRefundCmd.setRecNcode(sacCusParameter.getMerchantNcode());

    b2cRefundCmd.setMerchantName(otrxInfo.getDraccCusName());
    b2cRefundCmd.setOtrxCode("3302");//支付交易代码
    b2cRefundCmd.setOtrxSerialNo(otrxInfo.getOtrxSerialNo());
    b2cRefundCmd.setBankNodeCode(otrxInfo.getCraccNodeCode());
    b2cRefundCmd.setPayCurrency(otrxInfo.getPayCurrency());
    b2cRefundCmd.setPayAmount(otrxInfo.getPayAmount());
    b2cRefundCmd.setRefundAmount(new BigDecimal(0.00));//累计退款总金额
    b2cRefundCmd.setApplyAmount(otrxInfo.getPayAmount());//本次退款总金额
    b2cRefundCmd.setCnyAmount(otrxInfo.getPayAmount());//本次退款非税费人民币收款金额
    b2cRefundCmd.setFrnAmount(otrxInfo.getSacAmount());//购汇金额
    b2cRefundCmd.setGoodsAmount(null);//货款退款金额
    b2cRefundCmd.setTransAmount(otrxInfo.getTransportExpenses());//运费退款金额
    b2cRefundCmd.setTaxAmount(otrxInfo.getTaxAmount());//行邮税退款金额
    if (otrxInfo.getTaxAmount() == null) {
      b2cRefundCmd.setTaxFlag("N");//税费标志
    } else {
      b2cRefundCmd.setTaxFlag("Y");//税费标志
    }
    b2cRefundCmd.setApplyState("00");//退款状态
    b2cRefundCmd.setApplyNo(1);//退款顺序号
    b2cRefundCmd.setRefundBatch("");//退款批次
    b2cRefundCmd.setRspTime(null);//退款应答时间
    b2cRefundCmd.setCreateTime(new Date());
    b2cRefundCmd.setLastUpdateTime(new Date());
    b2cRefundCmd.setEtrxSerialNo(otrxInfo.getEtrxSerialNo());
    b2cRefundCmd.setDraccName(otrxInfo.getDraccCusName());

    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("otrxSerialNo", otrxInfo.getOtrxSerialNo());
    paramMap.put("trxType", "3803");

    List<SacOtrxInfo> trxList = otrxInfoDao.selectOtrxInfoList(paramMap);
    if (trxList != null && !trxList.isEmpty()) {
      b2cRefundCmd.setPurchState("S");//购汇状态
      b2cRefundCmd.setRemState("S");//付汇状态
    } else {
      b2cRefundCmd.setPurchState("N");//购汇状态
      b2cRefundCmd.setRemState("N");//付汇状态
    }
    b2cRefundCmd.setCrtCurrency(otrxInfo.getSacCurrency());
    b2cRefundCmd.setCrtAmount(null);//外币结汇金额
    b2cRefundCmd.setExstState(null);//结汇状态
    b2cRefundCmd.setRfPayAmount(null);//外币金额
    return b2cRefundCmd;
  }

  //组装b2b退款更新指令
  private SacRefundCommand installUpdateB2BRefundCmd(SacOtrxInfo otrxInfo) {
    SacRefundCommand b2bRefundCmd = new SacRefundCommand();
    b2bRefundCmd.setTrxSerialNo(otrxInfo.getTrxSerialNo());
    b2bRefundCmd.setTrxState("4");//退款成功
    b2bRefundCmd.setLastUpdateTime(new Date());
    return b2bRefundCmd;
  }

}
