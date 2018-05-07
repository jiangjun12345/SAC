package net.easipay.cbp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import net.easipay.cbp.dao.FinCode1Dao;
import net.easipay.cbp.dao.FinCodeDao;
import net.easipay.cbp.model.FinCode;
import net.easipay.cbp.model.FinCode1;
import net.easipay.dsfc.cache.CacheConfigurator;
import net.easipay.dsfc.cache.CacheHandler;
import net.easipay.dsfc.cache.CacheManager;
import net.easipay.dsfc.cache.CacheRegister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("cacheContextInitializer")
public class CacheContextHandler
{
    public static final String FIN_CODE = "FIN_CODE";
    public static final String FIN_CODE1 = "FIN_CODE1";

    @Autowired
    private FinCodeDao finCodeDao;

    @Autowired
    private FinCode1Dao finCode1Dao;

    @PostConstruct
    public void init()
    {
	CacheRegister.register(CacheContextHandler.FIN_CODE, CacheConfigurator.TEN_MINUTES_REFRESH_TIME, new CacheHandler() {
	    @Override
	    public Object execute() throws Exception
	    {
		List<FinCode> listFinCode = finCodeDao.getLikeFinCodes(null);
		Map<String, FinCode> map = new HashMap<String, FinCode>();
		for (FinCode finCode : listFinCode) {
		    map.put(finCode.getCodeId(), finCode);
		}
		return map;
	    }
	});

	CacheRegister.register(CacheContextHandler.FIN_CODE1, CacheConfigurator.TEN_MINUTES_REFRESH_TIME, new CacheHandler() {
	    @Override
	    public Object execute() throws Exception
	    {
		List<FinCode1> listFinCode1 = finCode1Dao.getAll();
		Map<String, FinCode1> map = new HashMap<String, FinCode1>();
		for (FinCode1 finCode1 : listFinCode1) {
		    map.put(finCode1.getCode1Id(), finCode1);
		}
		return map;
	    }
	});
    }

    @SuppressWarnings("unchecked")
    public static FinCode1 getFinCode1ByCodeId1(String code1Id)
    {
	Map<String, FinCode1> map = (Map<String, FinCode1>) CacheManager.getCache(CacheContextHandler.FIN_CODE1);
	return map == null ? null : map.get(code1Id);
    }

}
