package net.easipay.cbp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class ResourceInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long resourceId;
    
    private String dicCode;
	@Size(max=25,message="{filed.length.error}")
    private String resourceName;
	@Size(max=120,message="{filed.length.error}")
    private String resourceUrl;
	@Size(max=20,message="{filed.length.error}")
	private String resourceCode;
    
    private Long parentId;
    
    private String resourceType;
    
    private String validFlag;
    
    @Size(max=250,message="{filed.length.error}")
    private String description;

    private Long orderNum;
    
    private String createUser;
    
    private Date createTime;
    
    private String updateUser;
    
    private Date updateTime;
    
    private String memo;
    
    private List<ResourceInfo> childs = new ArrayList<ResourceInfo>();

	public List<ResourceInfo> getChilds() {
		return childs;
	}

	public void setChilds(List<ResourceInfo> childs) {
		this.childs = childs;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Long orderNum) {
		this.orderNum = orderNum;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
    
   
	public String getResourceCode() {
		return resourceCode;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public void add(ResourceInfo node) {  
		if (0==node.parentId) { 
			this.childs.add(node);  
		} else if (node.parentId==this.resourceId)
		{    this.childs.add(node);  
		}
              else 
              {    for (ResourceInfo tmp_node : childs)
              {     tmp_node.add(node);     }  
        }  
		}
    
	
    
}
