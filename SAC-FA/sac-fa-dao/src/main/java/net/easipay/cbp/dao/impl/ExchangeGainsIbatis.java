package net.easipay.cbp.dao.impl;

import java.math.BigDecimal;

import net.easipay.cbp.dao.ExchangeGainsDao;
import net.easipay.cbp.dao.base.ibatis.GenericDaoiBatis;
import net.easipay.cbp.model.SacOtrxInfo;

import org.springframework.stereotype.Repository;

@Repository("exchangeGainsIbatis")
public class ExchangeGainsIbatis extends GenericDaoiBatis<SacOtrxInfo, Long> implements ExchangeGainsDao
{

    @Override
    public BigDecimal getExchangeGains(String trxSerialNo)
    {
	@SuppressWarnings("deprecation")
	SacOtrxInfo exchangeTrx = (SacOtrxInfo) getSqlMapClientTemplate().queryForObject("getExchangeGains", trxSerialNo);
	if (exchangeTrx == null || exchangeTrx.getPayAmount() == null || exchangeTrx.getSacAmount() == null) {
	    return null;
	}
	else {
	    return new BigDecimal(exchangeTrx.getSacAmount().toString()).subtract(new BigDecimal(exchangeTrx.getPayAmount().toString()));
	}
    }

}
