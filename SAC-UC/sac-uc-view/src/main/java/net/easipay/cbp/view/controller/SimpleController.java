/**
 * 
 */
package net.easipay.cbp.view.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import net.easipay.cbp.view.form.Bill;
import net.easipay.cbp.view.form.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * @author Administrator
 *
 */

@Controller
@SessionAttributes({"type","name", "id"})
public class SimpleController {
	
	@RequestMapping(value="/simple/{id}")
	public String index(@PathVariable String id,  ModelMap model){
		System.out.println("id=="+id);
		model.addAttribute("type", "huanguan" + id);
		return "index";
	}
	
	//json返回
	@RequestMapping(value="/simpleJson")
	@ResponseBody
	public Map<String,String> getJson(){
		System.out.println("jinru getjson");
		Map<String, String> map = new HashMap<String,String>();
		map.put("id", "1");
		map.put("name", "张三");
		return map;
	}
	
	//xml返回
	@RequestMapping(value="/simpleXml")
	@ResponseBody
	public User getUser(@ModelAttribute("type") String type){
		System.out.println("type===" + type);
		User u = new User();
		u.setAge("12");
		u.setName("zhangsan");
		return u;
	}

	@RequestMapping("/addBill")
	public String addBill(@Valid Bill bill, BindingResult errors){
		System.out.println("bill.name=" + bill.getName());
		if(errors.hasErrors()){
			return "addBill";
		}
		return "success";
	}
	
	@RequestMapping("/viewBill")
	public String viewBill(ModelMap model){
		model.addAttribute("bill", new Bill());
		return "addBill";
	}

	

}
