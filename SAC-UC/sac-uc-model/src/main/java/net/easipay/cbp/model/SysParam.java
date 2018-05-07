package net.easipay.cbp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UC_SYS_PARAM_DEFINE")
public class SysParam implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	  
    private String paramKey;
    
    private String magicType;
    
    private String paramName;
    
    private String paramValue;
    
    private String paramDesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getMagicType() {
		return magicType;
	}

	public void setMagicType(String magicType) {
		this.magicType = magicType;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
}
