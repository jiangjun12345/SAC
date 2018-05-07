package net.easipay.cbp.service;

import java.util.Map;

import net.easipay.cbp.form.SacFinDetailQueryForm;

public interface ISacFinDetailService
{
    
    public Map<String,Object> queryFinDetail(SacFinDetailQueryForm form);
}
