package net.easipay.cbp.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.ISacCheckInfoDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacCheckInfo;
import net.easipay.cbp.util.BeanUtils;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@SuppressWarnings({"unchecked","deprecation"})
@Repository
public class SacCheckInfoDaoImpl extends GenericDaoiBatis<SacCheckInfo, Long> implements ISacCheckInfoDao {

  public static final Logger logger = Logger.getLogger(SacCheckInfoDaoImpl.class);

@Override
public List<SacCheckInfo> querySacCheckInfo(Map<String, Object> paramMap) {
	return this.getSqlMapClientTemplate().queryForList("querySacCheckInfo", paramMap);
}

@Override
public int getCheckInfoCount(Map<String, Object> paramMap) {
	return (Integer) this.getSqlMapClientTemplate().queryForObject("getCheckInfoCount", paramMap);
}

@Override
public SacCheckInfo selectSacCheckInfoById(Long id) {
	SacCheckInfo sacCheckInfo = new SacCheckInfo();
	Map<String, Object> map = new HashMap<String, Object>(); 
	map.put("id", id);
	map.put("start", 0);
	map.put("end", 10);
	List<SacCheckInfo> sacCheckInfoList = this.getSqlMapClientTemplate().queryForList("querySacCheckInfo", map);
    if(sacCheckInfoList!=null && sacCheckInfoList.size()>0){
    	sacCheckInfo = sacCheckInfoList.get(0);
    }
	return sacCheckInfo;
} 

@Override
public void insertSacCheckInfo(SacCheckInfo sacCheckInfo) {
	this.getSqlMapClientTemplate().insert("insertSacCheckInfo", sacCheckInfo);
}

@Override
public void updateSacCheckInfo(SacCheckInfo sacCheckInfo) {
	this.getSqlMapClientTemplate().update("updateSacCheckInfo", sacCheckInfo);	
}


}
