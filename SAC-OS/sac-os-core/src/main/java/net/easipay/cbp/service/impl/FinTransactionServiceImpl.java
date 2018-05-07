package net.easipay.cbp.service.impl;


import java.util.List;

import net.easipay.cbp.constant.Constants;
import net.easipay.cbp.form.FinTasksForm;
import net.easipay.dsfc.ws.jws.JwsClient;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.cbp.service.IFinTransactionService;

import org.springframework.stereotype.Service;



@Service("finTransactionService")
public class FinTransactionServiceImpl implements IFinTransactionService {

	public JwsResult finTasksDeal(List<FinTasksForm> finTasksFormList) {
		JwsClient client = new JwsClient(Constants.JOIN_FIN_TASK);
		client.putParam("finRuleTasks", finTasksFormList);
		JwsResult result = client.call();
		return result;
	}
	
}
