package net.easipay.cbp;

import net.easipay.cbp.sequence.SequenceAtomicNumber;
import net.easipay.cbp.sequence.SequenceCreatorUtil;

/**
 * 
 * @author mchen
 * @date 2015-12-14
 */

public class FinGenerator
{

    public static String generatePzId()
    {
	return FinPzId.getPzId();
    }

    public static class FinPzId
    {
	private static SequenceAtomicNumber sequence = new SequenceAtomicNumber(9999);

	public final static String getPzId()
	{
	    return String.format("%1$17s%2$03d%3$04d", DateUtil.formatCurrentDate("yyyyMMddHHmmssSSS"), SequenceCreatorUtil.getIpSuffix(), sequence.incrementAndGet());
	}

    }
}
