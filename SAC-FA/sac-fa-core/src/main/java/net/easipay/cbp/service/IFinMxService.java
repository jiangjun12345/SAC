package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.form.FinmxlistQueryForm;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.service.base.GenericManager;

public interface IFinMxService extends GenericManager<FinMx, Long>
{

    public List<FinMx> getFinMxList(FinmxlistQueryForm form);
}
