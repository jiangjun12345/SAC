/**
 * 
 */
package net.easipay.cbp.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author mchen
 * @date 2015-12-11
 */

public class FinmxlistQueryForm implements Serializable
{
    private static final long serialVersionUID = -2934746546335725187L;

    private String finCode;

    @NotBlank(message = "开始时间不能为空")
    private String beginDate;

    @NotBlank(message = "结束时间不能为空")
    private String endDate;

    private String start;

    private String end;

    @NotBlank(message = "是否显示标识不能为空")
    @Pattern(regexp = "^0|1$", message = "是否显示标识， 默认 1 ， 0: 不显示   1： 显示   ")
    private String isShow;

    public String getFinCode()
    {
	return finCode;
    }

    public void setFinCode(String finCode)
    {
	this.finCode = finCode;
    }

    public String getBeginDate()
    {
	return beginDate;
    }

    public void setBeginDate(String beginDate)
    {
	this.beginDate = beginDate;
    }

    public String getEndDate()
    {
	return endDate;
    }

    public void setEndDate(String endDate)
    {
	this.endDate = endDate;
    }

    public String getStart()
    {
	return start;
    }

    public void setStart(String start)
    {
	this.start = start;
    }

    public String getEnd()
    {
	return end;
    }

    public void setEnd(String end)
    {
	this.end = end;
    }

    public String getIsShow()
    {
	return isShow;
    }

    public void setIsShow(String isShow)
    {
	this.isShow = isShow;
    }

}
