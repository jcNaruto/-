package com.jc.laboratory.web.front.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.laboratory.dto.ToolResult;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.ToolService;
import com.jc.laboratory.util.CookieUtil;

@Controller
@RequestMapping("/front/hardware")
public class ToolController {
	
	@Autowired
	private ToolService toolService;
	
	private static final Integer BORROW_ISUSED = 0;
	private static final Integer RETURN_ISUSED = 1;
	
	private static final String BORROW_FEDDBACK = "borrow";
	
	/**
	 * 查看所有tool
	 * @return
	 */
	@RequestMapping(value="/selectall",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> toolList(){
		Map<String,Object> result = new HashMap<>();
		try {
			List<ToolResult> list = toolService.selectAll();
			result.put("success", true);
			result.put("tools", list);
			result.put("msg", "");
		} catch (LaboratoryException e) {
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		return result;
	}
	
	/**
	 * 借物品生成二维码的接口
	 * @param boxId
	 * @param isUsed 0 borrow 1 return
	 * @return
	 */
	@RequestMapping(value="/borrow",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> borrowTool(Integer boxId,HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		if(boxId == null) {
			result.put("success", false);
			result.put("msg", "请选择箱子");
			return result;
		}
		String userId = null;
		try {
			userId = CookieUtil.updateAndGetUserCookieValue(request, response);
			if(userId != null) {
				String key = toolService.borrowTool(boxId, BORROW_ISUSED, userId);
				result.put("success", true);
				result.put("uuid",key);
				result.put("msg", "");
			}else {
				result.put("success", false);
				result.put("msg", "您未登录");
				return result;
			}
		} catch (LaboratoryException e1) {
			result.put("success", false);
			result.put("msg", e1.getMessage());
		}
		return result;
	}
	
	
	/**
	 * hardware feedback about tool
	 * 借还物品的硬件调用接口
	 * @param key
	 * @param method
	 * @return
	 */
	@RequestMapping(value="/feedback",method=RequestMethod.POST)
	@ResponseBody
	public String borrowFeedBack(String key,String method){
		if(BORROW_FEDDBACK.equals(method)) {
			try {
				String value = toolService.toolFeedBack(key);
				return value;
			} catch (LaboratoryException e) {
				return e.getMessage();
			}
		}else {
			try {
				String value = toolService.toolFeedBack(key);
				return value;
			} catch (LaboratoryException e) {
				return e.getMessage();
			}
		}	
	}
	
	
	
	
	/**
	 * 还物品的生成二维码接口
	 * @param boxId
	 * @param isUsed 0 borrow 1 return
	 * @return
	 */
	@RequestMapping(value="/return",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> returnTool(Integer boxId,HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		if(boxId == null) {
			result.put("success", false);
			result.put("msg", "请选择箱子");
			return result;
		}
		String userId = null;
		try {
			userId = CookieUtil.updateAndGetUserCookieValue(request, response);
			if(userId != null) {
				String key = toolService.backTool(boxId, RETURN_ISUSED, userId);
				result.put("success", true);
				result.put("uuid",key);
				result.put("msg", "");
			}else {
				result.put("success", false);
				result.put("msg", "您未登录");
				return result;
			}
		} catch (LaboratoryException e1) {
			result.put("success", false);
			result.put("msg", e1.getMessage());
		}
		return result;
	}
	
	

	

}
