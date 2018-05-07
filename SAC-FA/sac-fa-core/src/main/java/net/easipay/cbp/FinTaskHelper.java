package net.easipay.cbp;

import java.util.ArrayList;
import java.util.List;

import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.form.PzParamsForm;

/**
 * 1.初始化fincode 新用户 保存fincode3 fincode finYeIbatis
 * 
 * 2.保存凭证
 * 
 * 3.更新余额，添加余额明细
 * 
 * 4.
 * 
 * 
 * @author mchen
 * @date 2015-12-14
 */

public class FinTaskHelper
{
    public static String getFinCode1(String codeId)
    {
	return codeId.substring(0, 4);
    }

    public static String getFinCode2(String codeId)
    {
	return codeId.substring(4, 6);
    }

    public static String getFinCode3(String codeId)
    {
	return codeId.substring(6, 25);
    }

    public static String getFinCode4(String codeId)
    {
	return codeId.substring(25, 27);
    }

    public static String getFinCode5(String codeId)
    {
	return codeId.substring(27, 29);
    }

    public static String getFinCode6(String codeId)
    {
	return codeId.substring(29, 31);
    }

    public static String getAccountCode1(String codeId)
    {
	return codeId.substring(0, 29);
    }

    public static String getAccountCode2(String codeId)
    {
	return codeId.substring(0, 27);
    }

    public static List<PzParamsForm> toPzParamsForms(String params)
    {
	String[] strs = params.split("\\|");
	if (strs.length != 2) {
	    throw new RuntimeException("借贷参数格式有误，请确认。");
	}

	List<PzParamsForm> list = new ArrayList<PzParamsForm>();
	PzParamsForm form = null;
	for (int i = 0; i < strs.length; i++) {
	    String[] splits = strs[i].split(";");
	    if (splits.length == 0) {
		throw new RuntimeException(String.format("参数有误[%s]", params));
	    }
	    for (int j = 0; j < splits.length; j++) {
		String[] str = splits[j].split(",");
		if (str.length < 4) {
		    throw new RuntimeException(String.format("参数有误[%s]", splits[j]));
		}
		form = new PzParamsForm();
		form.setCodeId(str[0].trim());
		form.setCode3Id(FinTaskHelper.getFinCode3(form.getCodeId()));
		StringBuffer sb = new StringBuffer();
		for (int k = 1; k < str.length - 2; k++) {
		    sb.append(str[k]);
		}
		form.setCode3Name(sb.toString().trim());
		form.setCurrType(str[str.length -2].trim());
		form.setAmount(str[str.length -1].trim());
		form.setDirection(i == 0 ? Constants.DIRECTION_DEBIT : Constants.DIRECTION_CREDIT);
		list.add(form);
	    }
	}
	return list;
    }

    public static boolean isShowFinYeMx(List<PzParamsForm> pzParamsForms)
    {
	for (PzParamsForm form : pzParamsForms) {
	    for (PzParamsForm _form : pzParamsForms) {
		if (form.getDirection() != _form.getDirection() && getAccountCode1(form.getCodeId()).equals(getAccountCode1(_form.getCodeId()))) {
		    return false;
		}
	    }
	}

	return true;
    }

}
