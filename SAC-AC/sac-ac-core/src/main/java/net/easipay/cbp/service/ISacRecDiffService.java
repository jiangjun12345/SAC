package net.easipay.cbp.service;

import java.util.List;

import net.easipay.cbp.form.SacRecDifferencesQueryForm;
import net.easipay.cbp.form.SacRecDifferencesQueryResult;
import net.easipay.cbp.form.SacRecDifferencesUpdateForm;

public interface ISacRecDiffService
{
    public List<SacRecDifferencesQueryResult> querySacRecDifferencesList(SacRecDifferencesQueryForm form);

    public void updateSacRecDifferences(SacRecDifferencesUpdateForm form);

}
