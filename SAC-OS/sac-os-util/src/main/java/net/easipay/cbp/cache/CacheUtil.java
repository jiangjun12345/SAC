package net.easipay.cbp.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.model.SacSysDic;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.dsfc.UnifiedConfig;
import net.easipay.dsfc.UnifiedConfigSimple;

public class CacheUtil
{

	/**
	 * 根据Type取字典数据
	 * @return List
	 */
	public static List<SacSysDic> getCacheByTypeToList(String type){
		List<SacSysDic> sysDicList = new ArrayList<SacSysDic>();
		//获取缓存数据
		List<UnifiedConfig> listUnifiedConfig = UnifiedConfigSimple. getDicTypeConfig (type);
		for(UnifiedConfig config:listUnifiedConfig){
			SacSysDic sysDic = new SacSysDic();
			sysDic.setDicCode(config.getDicValue());
			sysDic.setDicDesc(config.getDicDesc());
			sysDicList.add(sysDic);
		}
		return sysDicList;
	}
	
	/**
	 * 根据Type取字典数据
	 * @return Map
	 */
	public static Map<String,Object> getCacheByTypeToMap(String type){
		Map<String,Object> sysDicMap = new HashMap<String, Object>();
		//获取缓存数据
		List<UnifiedConfig> listUnifiedConfig = UnifiedConfigSimple.getDicTypeConfig (type);
		if(ConstantParams.DIC_CURRENCY_TYPE.equals(type)){
			for(UnifiedConfig config:listUnifiedConfig){
				sysDicMap.put(config.getDicCode(), config.getDicDesc());
			}
		}else{
			for(UnifiedConfig config:listUnifiedConfig){
				sysDicMap.put(config.getDicValue(), config.getDicDesc());
			}
		}
		return sysDicMap;
	}
	
	/**
	 * 币种转义Map
	 * @return Map  CNY转01
	 */
	public static Map<String,Object> getCcyTransferMap(){
		Map<String,Object> sysDicCcyMap = new HashMap<String, Object>();
		//获取缓存数据
		List<UnifiedConfig> listUnifiedConfig = UnifiedConfigSimple.getDicTypeConfig (ConstantParams.DIC_CURRENCY_TYPE);
		for(UnifiedConfig config:listUnifiedConfig){
			sysDicCcyMap.put(config.getDicCode(), config.getDicValue());
		}
		return sysDicCcyMap;
	}
	
	/**
	 * 币种转义Map
	 * @return Map  CNY转01
	 */
	public static Map<String,Object> getCcyTransferMap2(){
		Map<String,Object> sysDicCcyMap = new HashMap<String, Object>();
		//获取缓存数据
		List<UnifiedConfig> listUnifiedConfig = UnifiedConfigSimple.getDicTypeConfig (ConstantParams.DIC_CURRENCY_TYPE);
		for(UnifiedConfig config:listUnifiedConfig){
			sysDicCcyMap.put(config.getDicValue(), config.getDicCode());
		}
		return sysDicCcyMap;
	}
}
