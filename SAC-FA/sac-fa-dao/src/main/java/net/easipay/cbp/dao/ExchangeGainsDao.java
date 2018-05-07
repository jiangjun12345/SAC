package net.easipay.cbp.dao;

import java.math.BigDecimal;

import net.easipay.cbp.dao.base.GenericDao;
import net.easipay.cbp.model.SacOtrxInfo;

public interface ExchangeGainsDao extends GenericDao<SacOtrxInfo, Long> {
	
	public BigDecimal getExchangeGains(String trxSerialNo);
}
