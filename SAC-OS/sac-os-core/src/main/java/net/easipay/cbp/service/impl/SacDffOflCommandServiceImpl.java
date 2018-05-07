package net.easipay.cbp.service.impl;

import java.util.List;
import java.util.Map;

import net.easipay.cbp.cas.users.SecurityOperator;
import net.easipay.cbp.dao.ISacDffOflCommandDao;
import net.easipay.cbp.model.SacDffOflCommand;
import net.easipay.cbp.service.ISacDffOflCommandService;
import net.easipay.cbp.util.PersonUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sacDffOflCommandService")
public class SacDffOflCommandServiceImpl implements ISacDffOflCommandService
{

	public static final Logger logger = Logger.getLogger(SacDffOflCommandServiceImpl.class);

	@Autowired
	private ISacDffOflCommandDao sacDffOflCommandDao;

	@Override
	public Integer getCommandDetailCounts(Map<String, Object> queryMap) {
		return sacDffOflCommandDao.getCommandDetailCounts(queryMap);
	}

	@Override
	public List<SacDffOflCommand> getCommandDetailByPaging(
			Map<String, Object> queryMap, int pageNo, int pageSize) {
		int start = (pageNo-1)*pageSize;
		int end = pageNo*pageSize;
		queryMap.put("start", start);
		queryMap.put("end", end);
		return sacDffOflCommandDao.getCommandDetailByPaging(queryMap);
	}

	@Override
	public void dealCommandOfFailue(String cmdId) {
		SecurityOperator user = PersonUtil.getUser();
		/*SacB2BCommand cmd = new SacB2BCommand();
		cmd.setCmdId(Long.parseLong(cmdId));
		cmd.setCmdState(CmdState.TransationSuccess.code());
		cmd.setReqSpt1("线下出款成功");
		cmd.setReqSpt2(user.getEmail());
		cmd.setReqSpt3(DateUtil.getDate(new Date()));
		sacDffOflCommandDao.update(cmd);*/
	}

	@Override
	public void updateSacB2bCommand(SacDffOflCommand cmd) {
		sacDffOflCommandDao.updateOflCommandState(cmd);
		
	}
	




	
	
}
