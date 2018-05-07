/**
 * 
 */
package net.easipay.cbp.view.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Administrator
 *
 */
public class Bill {

//	@NotNull(message="不允许控制")
	@Size(min=100,max=100,message="{bill.name.error}")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
