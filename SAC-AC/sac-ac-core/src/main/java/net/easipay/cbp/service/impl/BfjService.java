package net.easipay.cbp.service.impl;

import net.easipay.cbp.service.IBfjService;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;

import org.springframework.stereotype.Service;

@Service("bfjService")
public class BfjService implements IBfjService
{
    @Override
    public String queryBfjYe(String bankNodeCode)
    {
	JwsClient jwsClient = new JwsClient("DSF-IC-0007");
	JwsResult jwsResult = jwsClient.putParam("bankCode",bankNodeCode).call();
	return jwsResult.getStringValue("availableBal");
    }
    
}
