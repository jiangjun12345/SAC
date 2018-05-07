package net.easipay.cbp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FIN_PZNO_SEQUENCE", schema = "SAC_SYN")
public class FinPzNoSequence implements java.io.Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8512316795207051371L;
	
	private Integer key;
	private Integer value;
	
	public FinPzNoSequence() {
	}

	public FinPzNoSequence(Integer key) {
		this.key = key;
	}
	
	public FinPzNoSequence(Integer key, Integer value) {
		this.key = key;
		this.value = value;
	}
	
	@Id
	@Column(name = "KEY", unique = true, nullable = false, length = 8)
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	@Column(name = "VALUE", length = 4)
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}

	
}
