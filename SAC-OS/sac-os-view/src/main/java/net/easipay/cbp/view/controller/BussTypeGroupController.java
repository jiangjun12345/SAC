package net.easipay.cbp.view.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.easipay.cbp.model.BussTypeGroup;
import net.easipay.cbp.service.IBussTypeGroupService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
* @Description: 客户交易管理控制层
* @author dsy (作者英文名称) 
* @date 2015-7-1 下午02:31:06
* @version V1.0  
* @jdk v1.6
 */
@Controller
public class BussTypeGroupController extends BaseDataController
{

	private Logger logger = LoggerFactory.getLogger(BussTypeGroupController.class);
	
	@Autowired
	public IBussTypeGroupService bussTypeGroupService;
	
	/**
	 * 客户交易明细查询初始化操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/bussTypeQueryInit",method={RequestMethod.GET})
	public ModelAndView bussTypeQueryInit(HttpServletRequest request,HttpServletResponse response){
		return bussTypeQuery(request,response);
	}
	
	/**
	 * 客户交易明细查询操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/bussTypeQuery",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView bussTypeQuery(HttpServletRequest request,HttpServletResponse response){
		ModelAndView mav = new ModelAndView("bussTypeGroup/bussTypeQuery");
		//组装查询参数
		Map<String,Object> paramMap = new HashMap<String, Object>();
		//handlePageAndDateRange(request,paramMap,mav,7,10);//页码和起止时间处理
		
		Integer pageNo = 1;// 默认为第1页
		if (request.getParameter("pageNo") != null && !"".equals(request.getParameter("pageNo")))
		{
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
		}

		Integer pageSize = 10;// 默认每页显示pageSizeNum条
		if (request.getParameter("pageSize") != null && !"".equals(request.getParameter("pageSize")))
		{
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		}
		//起止时间处理
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		String startDate = sdf.format(cal.getTime());
		String startDateTemp = request.getParameter("startDate")==null?startDate:request.getParameter("startDate");
		paramMap.put("bussTypeDate", startDateTemp);//开始日期
		paramMap.put("start", (pageNo-1)*pageSize);//分页起始
		paramMap.put("end", pageSize*pageNo);
		List<BussTypeGroup> bussTypeGroupList = bussTypeGroupService.getBussTypeGroup(paramMap);

		//Map<String,Object> allAmountCount = bussTypeGroupService.(paramMap);
		//返回结果
		mav.addObject("totalCount", bussTypeGroupService.getBussTypeGroupCount(paramMap));//总数
		mav.addObject("bussTypeGroupList", bussTypeGroupList);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("startDate", startDateTemp);
		return mav;
	}

}
