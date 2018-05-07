package net.easipay.cbp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.easipay.cbp.constant.SacConstants;
import net.easipay.cbp.dao.ISacChannelParamDao;
import net.easipay.cbp.dao.ISacFinInsRuleDao;
import net.easipay.cbp.dao.ISacTrxCodeRuleDao;
import net.easipay.cbp.model.SacChannelParam;
import net.easipay.cbp.model.SacTrxCodeRule;
import net.easipay.dsfc.cache.CacheConfigurator;
import net.easipay.dsfc.cache.CacheHandler;
import net.easipay.dsfc.cache.CacheRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheHandleInit
{
    /**
     * 交易代码规则表
     */
    public static final String SAC_TRX_CODE_RULE = "SAC_TRX_CODE_RULE";
    /**
     * 渠道参数表
     */
    public static final String SAC_CHANNEL_PARAM = "SAC_CHANNEL_PARAM";

    /**
     * 记账参数规则表
     */
    public static final String SAC_FIN_INS_RULE = "SAC_FIN_INS_RULE";

    @Autowired
    private ISacTrxCodeRuleDao sacTrxCodeRuleDao;

    @Autowired
    private ISacChannelParamDao sacChannelParamDao;

    @Autowired
    private ISacFinInsRuleDao sacFinInsRuleDao;

    @PostConstruct
    public void init()
    {
	CacheRegister.register(SAC_TRX_CODE_RULE, CacheConfigurator.DEFAULT_REFRESH_TIME, new CacheHandler() {
	    @Override
	    public Object execute() throws Exception
	    {
		List<SacTrxCodeRule> list = sacTrxCodeRuleDao.listSacTrxCodeRule(null);
		Map<String, SacTrxCodeRule> map = new HashMap<String, SacTrxCodeRule>();
		for (SacTrxCodeRule rule : list) {
		    map.put(rule.getTrxCode(), rule);
		}
		return map;
	    }
	});

	CacheRegister.register(SAC_CHANNEL_PARAM, CacheConfigurator.DEFAULT_REFRESH_TIME, new CacheHandler() {
	    @Override
	    public Object execute() throws Exception
	    {
		List<SacChannelParam> listSacChannelParam = sacChannelParamDao.listSacChannelParam(null, null, null, SacConstants.IS_VALID_FLAG.YES);
		Map<String, SacChannelParam> map = new HashMap<String, SacChannelParam>();
		for (SacChannelParam sacChannelParam : listSacChannelParam) {
		    map.put(sacChannelParam.getChnCode() + sacChannelParam.getChnType() + sacChannelParam.getCurrencyType(), sacChannelParam);
		}
		return map;
	    }
	});

	CacheRegister.register(SAC_FIN_INS_RULE, CacheConfigurator.DEFAULT_REFRESH_TIME, new CacheHandler() {
	    @Override
	    public Object execute() throws Exception
	    {
		return sacFinInsRuleDao.listSacFinInsRule(null, null);
	    }
	});

    }
}
