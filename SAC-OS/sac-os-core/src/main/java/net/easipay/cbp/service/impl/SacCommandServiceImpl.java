package net.easipay.cbp.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.constant.EnumConstants.FundGiveOLConstants.CmdState;
import net.easipay.cbp.dao.ISacCommandDao;
import net.easipay.cbp.model.SacB2BCommand;
import net.easipay.cbp.service.ISacCommandService;
import net.easipay.cbp.util.DateUtil;
import net.easipay.cbp.util.PersonUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacCommandService")
public class SacCommandServiceImpl implements ISacCommandService
{

	public static final Logger logger = Logger.getLogger(SacCommandServiceImpl.class);

	@Autowired
	private ISacCommandDao sacCommandDao;

	@Override
	public Integer getCommandDetailCounts(Map<String, Object> queryMap) {
		return sacCommandDao.getCommandDetailCounts(queryMap);
	}

	@Override
	public List<SacB2BCommand> getCommandDetailByPaging(
			Map<String, Object> queryMap, int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		queryMap.put("start", start);
		queryMap.put("end", end);
		return sacCommandDao.getCommandDetailByPaging(queryMap);
	}

	@Override
	public void dealCommandOfFailue(String cmdId) {
		SecurityOperator user = PersonUtil.getUser();
		SacB2BCommand cmd = new SacB2BCommand();
		cmd.setCmdId(Long.parseLong(cmdId));
		cmd.setCmdState(CmdState.TransationSuccess.code());
		cmd.setReqSpt1("线下出款成功");
		cmd.setReqSpt2(user.getEmail());
		cmd.setReqSpt3(DateUtil.getDate(new Date()));
		sacCommandDao.update(cmd);
	}

	@Override
	public void updateSacB2bCommand(SacB2BCommand cmd) {
		sacCommandDao.updateOflCommandState(cmd);
		
	}
	




	
	
}
