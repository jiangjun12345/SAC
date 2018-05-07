package net.easipay.cbp.wss;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cas.auth.UserAuthoritiesMenu;
import net.easipay.cbp.cas.users.OrgnizationInfo;
import net.easipay.cbp.model.SysDic;
import net.easipay.cbp.model.UcChannelParam;
import net.easipay.cbp.model.UcCusParameter;
import net.easipay.cbp.model.UcOperator;
import net.easipay.cbp.model.UcOrgInfo;
import net.easipay.cbp.model.UcUser;
import net.easipay.cbp.sequence.SequenceCreatorUtil;
import net.easipay.cbp.service.ChannelParamService;
import net.easipay.cbp.service.CusParameterService;
import net.easipay.cbp.service.OperatorService;
import net.easipay.cbp.service.OrgInfoService;
import net.easipay.cbp.service.ResourceInfoService;
import net.easipay.cbp.service.SysDicService;
import net.easipay.cbp.service.UcUserService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.cbp.util.MD5Util;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class interfaceCss {
	private static final Logger logger = Logger.getLogger(interfaceCss.class);

	@WssRequestMapping(value = "resourceManageTree", service = "SAC-UC-0014", method = WssRequestMethod.POST, desc = "根据ID查询商户信息")
	public WsResult resourceManageTree(JwsHttpRequest jwsHttpRequest) {
		JwsResult jwsResult = new JwsResult();
		try {
			String userId = jwsHttpRequest.getStringValue("userId");
			String userType = jwsHttpRequest.getStringValue("userType");
			String dicCode = jwsHttpRequest.getStringValue("dicCode");

			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", userId);
			map.put("userType", userType);
			map.put("dicCode", dicCode);
			UserAuthoritiesMenu userAuthoritiesMenu = SpringServiceFactory.getBean(ResourceInfoService.class).selectResourceManageTree(map);
			if (userAuthoritiesMenu == null) throw new RuntimeException("查询的结果为空!");

			jwsResult.setValue("userAuthoritiesMenu", userAuthoritiesMenu);
			jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功!");
		} catch (Exception e) {
			logger.error("resourceManageTree.selectOrgInfoById throw Exception", e);
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
		}
		return jwsResult;
	}

	
	@WssRequestMapping(value = "addChannelParam", service = "SAC-UC-0001", method = WssRequestMethod.POST, desc = "接收渠道参数")
	public WsResult addChannelParam(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcChannelParam form = jwsHttpRequest.getBean("ucChannelParam",UcChannelParam.class);

			if (form != null) {
				SpringServiceFactory.getBean(ChannelParamService.class).insertUcChannelParam(form);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}

	@WssRequestMapping(value = "updateUcChannelParam", service = "SAC-UC-0002", method = WssRequestMethod.POST, desc = "更新渠道参数")
	public WsResult updateUcChannelParam(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcChannelParam form = jwsHttpRequest.getBean("ucChannelParam",UcChannelParam.class);

			if (form != null) {
				SpringServiceFactory.getBean(ChannelParamService.class).updateUcChannelParam(form);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	
	@WssRequestMapping(value = "insertSysDic", service = "SAC-UC-0003", method = WssRequestMethod.POST, desc = "接收字典表数据")
	public WsResult insertSysDic(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			SysDic form = jwsHttpRequest.getBean("ucSysDic", SysDic.class);

			if (form != null) {
				SpringServiceFactory.getBean(SysDicService.class).insertSysDic(form);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	@WssRequestMapping(value = "addCusParameter", service = "SAC-UC-0004", method = WssRequestMethod.POST, desc = "接收客户参数")
	public WsResult addCusParameter(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcCusParameter form = jwsHttpRequest.getBean("ucCusParameter",UcCusParameter.class);

			if (form != null) {
				SpringServiceFactory.getBean(CusParameterService.class).insertUcCusParameter(form);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	
	@WssRequestMapping(value = "updateCusParameter", service = "SAC-UC-0005", method = WssRequestMethod.POST, desc = "更新客户参数")
	public WsResult updateCusParameter(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcCusParameter form = jwsHttpRequest.getBean("ucCusParameter",UcCusParameter.class);

			if (form != null) {
				SpringServiceFactory.getBean(CusParameterService.class).updateUcCusParameter(form);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	@WssRequestMapping(value = "selectUcOperatorById", service = "SAC-UC-0006", method = WssRequestMethod.POST, desc = "根据ID查找操作员")
	public WsResult selectUcOperatorById(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			Long id = jwsHttpRequest.getLongValue("id");

			if (id != null) {
				UcOperator ucOperator = SpringServiceFactory.getBean(OperatorService.class).selectUcOperatorById(id);
				jwsResult.setValue("ucOperator", ucOperator);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	@WssRequestMapping(value = "updateUcOperator", service = "SAC-UC-0007", method = WssRequestMethod.POST, desc = "更新操作员")
	public WsResult updateUcOperator(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcOperator form = jwsHttpRequest.getBean("ucOperator", UcOperator.class);

			if (form != null) {
				SpringServiceFactory.getBean(OperatorService.class).updateUcOperator(form);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	
	@WssRequestMapping(value = "selectUcOperator", service = "SAC-UC-0008", method = WssRequestMethod.POST, desc = "查询操作员")
	public WsResult selectUcOperator(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			String email = jwsHttpRequest.getStringValue("email");
		    String pass = jwsHttpRequest.getStringValue("pass");
			if (pass!= null && email!= null) {
				UcOperator ucOperator = SpringServiceFactory.getBean(OperatorService.class).selectUcOperatorByEmailAndPass(email, pass);
				jwsResult.setValue("ucOperator", ucOperator);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	
	@WssRequestMapping(value = "selectOrgInfoById", service = "SAC-UC-0009", method = WssRequestMethod.POST, desc = "根据Id查询商户信息")
	public WsResult selectOrgInfoById(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			Long orgId = jwsHttpRequest.getLongValue("orgId");
			if (orgId!= null) {
				UcOrgInfo ucOrgInfo = SpringServiceFactory.getBean(OrgInfoService.class).selectOrgInfoById(orgId);
				OrgnizationInfo orgnizationInfo =SpringServiceFactory.getBean(OrgInfoService.class).transFromUcOrgInfo(ucOrgInfo);
				jwsResult.setValue("orgnizationInfo", orgnizationInfo);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	@WssRequestMapping(value = "updateOrgInfo", service = "SAC-UC-0010", method = WssRequestMethod.POST, desc = "更新商户信息")
	public WsResult updateOrgInfo(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcOrgInfo form = jwsHttpRequest.toBean(UcOrgInfo.class);
			if (form!= null) {
				 SpringServiceFactory.getBean(OrgInfoService.class).updateOrgInfo(form);
				
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}

	@WssRequestMapping(value = "selectOrgInfoByParameter", service = "SAC-UC-0011", method = WssRequestMethod.POST, desc = "查询商户信息")
	public WsResult selectOrgInfoByParameter(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcOrgInfo form = jwsHttpRequest.toBean(UcOrgInfo.class);
			if (form!= null) {
				List<UcOrgInfo> ucOrgInfoList = SpringServiceFactory.getBean(OrgInfoService.class).selectOrgInfoByParameter(form);
				jwsResult.setValue("ucOrgInfoList", ucOrgInfoList);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	@WssRequestMapping(value = "selectUcUserById", service = "SAC-UC-0012", method = WssRequestMethod.POST, desc = "根据Id查询用户信息")
	public WsResult selectUcUserById(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			Long id = jwsHttpRequest.getLongValue("id");
			if (id!= null) {
				UcUser ucUser = SpringServiceFactory.getBean(UcUserService.class).selectUcUserById(id);
				jwsResult.setValue("ucUser", ucUser);
				jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS,"成功!");
			} else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	@WssRequestMapping(value = "updateUcUser", service = "SAC-UC-0013", method = WssRequestMethod.POST, desc = "更新用户信息")
	public WsResult updateUcUser(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcUser form = jwsHttpRequest.toBean(UcUser.class);
		    if(form!=null && form.getId()!=null){
		    	SpringServiceFactory.getBean(UcUserService.class).updateUcUser(form);
		    	jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功!");
		    }else {
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"参数为空!");
			}
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
	@WssRequestMapping(value = "createMerchantOperator", service = "SAC-UC-0015", method = WssRequestMethod.POST, desc = "创建商户操作员")
	public WsResult createMerchantOperator(JwsHttpRequest jwsHttpRequest) throws Exception {
		JwsResult jwsResult = new JwsResult();
		try {
			UcOperator form = new UcOperator();
			String orgId = jwsHttpRequest.getStringValue("orgId");
			String loginName = jwsHttpRequest.getStringValue("loginName");
			if(StringUtils.isBlank(orgId)){
				jwsResult.setError(ConstantParams.RTN_CODE_PARAM_IS_NULL,"组织机构号为空!");
			}else{
				form.setOrgId(orgId);
				int count = SpringServiceFactory.getBean(OperatorService.class).selectUcOperatorCounts(form);
				if(count>0){
					jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功!");
				}else{
					form.setId(SequenceCreatorUtil.getTableId());
			    	form.setOrgId(orgId);
			    	form.setLoginName(loginName);
			    	form.setLoginNameCh(loginName);
			    	form.setPassword(MD5Util.getMD5By32bit(orgId, ConstantParams.CODE_FORMAT_UTF8));
			    	form.setCreateTime(new Date());
			    	form.setCreateUserId(99L);
			    	form.setRoleId(99999L);//商户固定角色
			    	form.setUserType("02");
			    	form.setStatus("Y");
			    	SpringServiceFactory.getBean(OperatorService.class).insertUcOperator(form);
			    	jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功!");
				}
			}
		    
		} catch (Exception e) {
			jwsResult.setError(ConstantParams.RTN_CODE_ERROR,e.getMessage());
			e.printStackTrace();
		} 
		return jwsResult;
	}
	
}
