package com.jc.laboratory.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jc.laboratory.exception.LaboratoryException;

public class CookieUtil {
	
	 
	 //修改cookie，可以根据某个cookie的name修改它 除了value和时间外的所有属性都必须一致
   public static String updateAndGetUserCookieValue(HttpServletRequest request,HttpServletResponse response) throws LaboratoryException{
       Cookie[] cookies = request.getCookies();
       if (null==cookies) {
       	throw new LaboratoryException("您未登录");
       } else {
           for(Cookie cookie : cookies){
               //迭代时如果发现与指定cookieName相同的cookie，就修改相关数据
               if("loginUserId".equals(cookie.getName())){
               	cookie.setPath("/");
               	String value = cookie.getValue();
               	cookie.setValue(value);
                   cookie.setMaxAge(60*60*24*30);// 修改存活时间
                   response.addCookie(cookie);
                   return value;
               }
           }
       }
		return null;
   }

}
