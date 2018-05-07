package net.easipay.cbp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.dao.FinMxDao;
import net.easipay.cbp.form.FinmxlistQueryForm;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.service.IFinMxService;
import net.easipay.cbp.service.base.impl.GenericManagerImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("finMxManager")
public class FinMxService extends GenericManagerImpl<FinMx, Long> implements IFinMxService
{

    @Autowired
    FinMxDao finMxIbatis;

    @Override
    public List<FinMx> getFinMxList(FinmxlistQueryForm form)
    {
	Map<String, String> params = new HashMap<String, String>();
	params.put("codeId", form.getFinCode());
	params.put("beginDate", form.getBeginDate());
	params.put("endDate", form.getEndDate());
	params.put("start", form.getStart());
	params.put("end", form.getEnd());
	params.put("isShow", form.getIsShow());
	return finMxIbatis.getFinMxList(params);
    }

}
