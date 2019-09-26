package com.jc.laboratory.web.front.manipulation;

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

import com.jc.laboratory.dto.ActivityResourceResult;
import com.jc.laboratory.entity.Manipulation;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.ManipulationService;
import com.jc.laboratory.util.CookieUtil;

@Controller
@RequestMapping("/front/manipulate")
public class ManipulationController {
	
	@Autowired
	private ManipulationService manipulationService;
	
	/**
	 * 查看所有操作，从cookie中获取userId
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/selectall",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> myActivity(HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		String userId = null;
		try {
			userId = CookieUtil.updateAndGetUserCookieValue(request, response);
			if(userId != null) {
				List<Manipulation> list = manipulationService.selectAll(userId);
				result.put("success", true);
				result.put("manipulations",list);
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
