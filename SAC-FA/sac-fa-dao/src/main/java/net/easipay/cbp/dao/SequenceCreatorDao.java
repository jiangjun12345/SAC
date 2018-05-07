/**
 * 
 */
package net.easipay.cbp.dao;


/**
 * @author Administrator
 *
 */
public interface SequenceCreatorDao  {
	public Long getSequenceNoBySeqName(String sequenceName);
}
