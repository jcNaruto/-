package com.jc.laboratory.web.front.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.laboratory.entity.User;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.UserService;
import com.jc.laboratory.util.CookieUtil;
import com.jc.laboratory.util.Validator;

@Controller
@RequestMapping("/front/user")
public class UserController {
	
	/**
	 * user service interface
	 */
	@Autowired
	private UserService userService;

	
	/**
	 * user information update
	 * @param user
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> update(User user,String captcha,HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		if(user != null && user.getEmail() == null && user.getUserId() == null) {
			try {
				String userId = CookieUtil.updateAndGetUserCookieValue(request,response);
				if(userId == null) {
					result.put("success", false);
					result.put("msg", "用户未登录");
					return result;
				}else {
					user.setUserId(userId);
					User updateUser = userService.update(user,captcha);
					if(updateUser != null) {
						result.put("success", true);
						result.put("user", updateUser);
						result.put("msg", "");
					}
				}
			} catch (LaboratoryException e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}else {
			result.put("success", false);
			result.put("msg", "学号和email不允许更改");
		}
		return result;
	}
	/**
	 * user information update
	 * @param user
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updatepwd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePwd(String captcha,String email,String newPwd,String id){
		Map<String,Object> result = new HashMap<>();
		if(email != null && captcha != null && newPwd != null && id != null) {
			try {
				boolean emailType = Validator.isEmail(email);
				if(!emailType) {
					throw new LaboratoryException("邮箱格式有误");
				}
				User user = new User();
				user.setEmail(email);
				user.setPwdHash(newPwd);
				user.setUserId(id);
				User updateUser = userService.update(user, captcha);
				if(updateUser != null) {
					result.put("success", true);
					result.put("msg", "");
				}
			} catch (LaboratoryException e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}else {
			result.put("success", false);
			result.put("msg", "信息未填写完成");
		}
		return result;
	}
	
	/**
	 * user login
	 * @param id
	 * @param pwd
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/login",method= {RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> login(String id,String pwdHash,HttpServletResponse response){
		Map<String,Object> result = new HashMap<>();
		if(id != null && !"".equals(id) && pwdHash != null && !"".equals(pwdHash)) {
			try {
				User user = userService.login(id, pwdHash);
				//登陆成功回传cookie
				addCookie(response,id);
				result.put("success", true);
				result.put("user", user);
				result.put("msg", "");
			} catch (LaboratoryException e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}else {
			result.put("success", false);
			result.put("msg", "账号或密码为空");
		}
		return result;
	}
	
	
	/**
	 * get email code before user update pwd 
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/changepwdverify",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updatePwdVerify(String email){
		Map<String,Object> result = new HashMap<>();
		if(email != null) {
			try {
				boolean emailType = Validator.isEmail(email);
				if(!emailType) {
					throw new LaboratoryException("邮箱格式有误");
				}
				boolean success = userService.updatePwdVefiry(email);
				if(success) {
					result.put("success", true);
					result.put("msg", "");
				}
			} catch (LaboratoryException e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}else {
			result.put("success", false);
			result.put("msg", "邮箱不能为空!");
		}
		return result;
	}
	
	
	/**
	 * user register
	 * @param user
	 * @param captcha
	 * @return
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> register(User user,String captcha){
		Map<String,Object> result = new HashMap<>();
		if(user != null && user.getUserId() != null && user.getEmail() != null
				&& user.getPwdHash() != null && user.getName() != null) {
			try {
				boolean success = userService.userRegister(user, captcha);
				if(success) {
					result.put("success", true);
					result.put("msg", "");
				}
			} catch (LaboratoryException e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}else {
			result.put("success", false);
			result.put("msg", "信息未填写完全!");
		}
		return result;
	}
	
	/**
	 * verify email and send verify mail
	 * @param email
	 * @return
	 */
	@RequestMapping(value="/verifyemail",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> verifyEmail(String email){
		Map<String,Object> result = new HashMap<>();
		if(email != null) {
			boolean emailType = Validator.isEmail(email);//后端校验邮箱是否为空
			if(emailType){
				try {
					boolean success = userService.verifyEmailAndPassCAPTCHA(email);
					if(success) {
						result.put("success", true);
						result.put("msg", "");
					}
				} catch (LaboratoryException e) {
					result.put("success", false);
					result.put("msg", e.getMessage());
				}
			}else {
				result.put("success", false);
				result.put("msg", "邮箱格式不正确!");
			}
		}else {
			result.put("success", false);
			result.put("msg", "邮箱不能为空!");
		}
		return result;
	}
	
	
	 private void addCookie(HttpServletResponse response,String id){
        Cookie cookie = new Cookie("loginUserId",id);//创建新cookie
        cookie.setMaxAge(60*60*24*30);// 设置存在时间为30天
        cookie.setPath("/");//设置作用域
        response.addCookie(cookie);//将cookie添加到response的cookie数组中返回给客户端
    }

}
