package com.jc.laboratory.web.front.door;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.DoorService;
import com.jc.laboratory.util.CookieUtil;

/**
 * door function controller
 * @author jc
 *
 */

@Controller
@RequestMapping("/front/hardware")
public class DoorController {
	
	@Autowired
	private DoorService doorService;
	
	/**
	 * open door 
	 * @param response
	 * @param request
	 * @return key uuid
	 */
	@RequestMapping(value="/open",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> openDoor(HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		String userId = null;
		try {
			userId = CookieUtil.updateAndGetUserCookieValue(request, response);
			if(userId != null) {
				String openDoorUUID = doorService.openDoor(userId);
				result.put("success", true);
				result.put("uuid",openDoorUUID);
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
	 * open door feedback
	 * 开门的硬件接口访问接口
	 * @param response
	 * @param request
	 * @return key uuid
	 */
	@RequestMapping(value="/feedbackdoor",method=RequestMethod.POST)
	@ResponseBody
	public String openDoor(String key){
		try {
			String status = doorService.feedBack(key);
			return status;
		} catch (LaboratoryException e) {
			return e.getMessage();
		}
	}
}
