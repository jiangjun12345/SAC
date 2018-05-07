package net.easipay.cbp.view.taglib;




import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * author:zhangxb
 * select初始化
 */
public class SelectCommonTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private String thisName = ""; //名称
	private boolean flag = false; //是否有全部选项 false 无; true 有  
	private String defaultValue = "0"; //默认值
	private String cssStyle;//样式
	private String onchange;
	private Map valueMap;
	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Map getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map valueMap) {
		this.valueMap = valueMap;
	}

	public SelectCommonTag() {
	}

	public int doStartTag() throws JspException {
		return (SKIP_BODY);
	}

	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {
		HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
		response.setContentType("text/html; charset=utf-8");
		
		try {
			JspWriter out = pageContext.getOut();
			StringBuffer sbf = new StringBuffer();
			sbf.append("<select name='"+thisName+"' id='"+thisName+"' class='"+className+"'");
			if(cssStyle!=null && cssStyle.length()>0){
				sbf.append(" style='"+cssStyle+"' ");
			}
			if(onchange!=null && onchange.length()>0){
				sbf.append(" onchange='"+onchange+"' ");
			}
			sbf.append(">\n");
			if(flag){//有全部选项
				sbf.append("<option value=''>全部</option>\n");
			}
			if(valueMap!=null && valueMap.size()>0){
				Set<Entry> set = valueMap.entrySet();
				Iterator it = set.iterator();
				sbf.append("<option value='"+"-1"+"' selected>"+"请选择"+"</option>\n");
				while(it.hasNext()){
					Entry entry = (Entry) it.next();
					String value = (String) entry.getKey();
					String text = (String) entry.getValue();
					if(value==null){continue;}
					if(defaultValue!=null && defaultValue.equals(value)){
						sbf.append("<option value='"+value+"' selected>"+text+"</option>\n");
					}else{
						
						sbf.append("<option value='"+value+"'>"+text+"</option>\n");
					}

				}
			}
			sbf.append("</select>");
			out.write(sbf.toString());
		} catch (Exception e) {
			throw new JspException(e.getMessage());
		}
		return (EVAL_PAGE);
	}

	public void release() {
		super.release();
	}

	public String getThisName() {
		return thisName;
	}
	public void setThisName(String thisName) {
		this.thisName = thisName;
	}

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}


	public String getDefaultValue() {
		return defaultValue;
	}

	public String getCssStyle() {
		return cssStyle;
	}
	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}

	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
