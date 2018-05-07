package net.easipay.cbp.wss;

import java.util.List;

import net.easipay.cbp.form.FinmxlistQueryForm;
import net.easipay.cbp.model.FinMx;
import net.easipay.cbp.service.IFinMxService;
import net.easipay.cbp.util.ConstantParams;
import net.easipay.dsfc.ws.WsResult;
import net.easipay.dsfc.ws.jws.JwsHttpRequest;
import net.easipay.dsfc.ws.jws.JwsResult;
import net.easipay.dsfc.ws.support.SpringServiceFactory;
import net.easipay.dsfc.ws.wss.WssRequestMapping;
import net.easipay.dsfc.ws.wss.WssRequestMethod;

import org.apache.log4j.Logger;

public class FinMxWss
{

    private static final Logger logger = Logger.getLogger(FinMxWss.class);

    /**
     * 列表显示
     * 
     * @throws Exception
     * */
    @WssRequestMapping(value = "mxlist", service = "SAC-FA-0001", method = WssRequestMethod.POST, desc = "余额明细接口")
    public WsResult mxList(JwsHttpRequest jwsHttpRequest)
    {
	JwsResult jwsResult = new JwsResult();
	try {
	    FinmxlistQueryForm form = jwsHttpRequest.toBean(FinmxlistQueryForm.class);
	    
	    jwsHttpRequest.validate(form);
	    
	    IFinMxService finMxManager = SpringServiceFactory.getBean(IFinMxService.class);
	    // 明细查询
	    List<FinMx> finMxList = finMxManager.getFinMxList(form);
	    // 记录总数目
	    Integer totalCount = 0;// 查询记录总数
	    jwsResult.setValue("finMxList", finMxList);// 数据层
	    if (finMxList.size() != 0) {
		totalCount = finMxList.get(0).getTotCnt();
	    }
	    jwsResult.setValue("totalCount", totalCount);// 数据层
	    jwsResult.setSuccess(ConstantParams.RTN_CODE_SUCCESSS, "成功");// 响应结果
	} catch ( Exception e ) {
	    logger.error(e.getMessage());
	    jwsResult.setError(ConstantParams.RTN_CODE_ERROR, e.getMessage());
	}
	return jwsResult;
    }
}
