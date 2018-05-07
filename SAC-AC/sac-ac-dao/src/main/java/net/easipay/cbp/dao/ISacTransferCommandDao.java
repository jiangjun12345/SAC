package net.easipay.cbp.dao;

import net.easipay.cbp.model.SacDffOflCommand;
import net.easipay.cbp.model.SacTransferCommand;


public interface ISacTransferCommandDao
{
    public void insertSacTransferCommand(SacTransferCommand sacTransferCommand);
    
    public void insertSacDffOflCommand(SacDffOflCommand sacDffOflCommand);
}
