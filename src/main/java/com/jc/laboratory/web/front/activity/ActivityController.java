package com.jc.laboratory.web.front.activity;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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

import com.jc.laboratory.dto.ActivityDetailResult;
import com.jc.laboratory.dto.ActivityResourceResult;
import com.jc.laboratory.entity.Activity;
import com.jc.laboratory.entity.UserActivity;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.ActivityService;
import com.jc.laboratory.util.CookieUtil;

/**
 * activity user function controller 
 * front
 * @author jc
 *
 */
@Controller
@RequestMapping("/front/activity")
public class ActivityController {
	
	@Autowired
	private ActivityService activityService;
	
	private final Integer LESSION_TYPE_OK = 1;//当前课程或活动开放状态
	
	
	
	/**
	 * query user activity
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/myactivity",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> myActivity(HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		String userId = null;
		try {
			//该方法要求用户登陆之后才能使用
			userId = CookieUtil.updateAndGetUserCookieValue(request, response);
			if(userId != null) {
				List<ActivityResourceResult> list = activityService.myActivity(userId);
				if(list != null) {
					result.put("success", true);
					result.put("lessions", list);
					result.put("currWeek",getCurrWeek());
					result.put("msg", "");
				}else {
					result.put("success", false);
					result.put("msg", "我的课程查询失败");
				}
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
	 * user activity choose
	 * @param userActivity activityID
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/choose",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> chooseActivity(UserActivity userActivity,HttpServletResponse response,HttpServletRequest request){
		Map<String,Object> result = new HashMap<>();
		String userId = null;
		try {
			//该方法要求用户登陆之后才能使用
			userId = CookieUtil.updateAndGetUserCookieValue(request, response);
		} catch (LaboratoryException e1) {
			result.put("success", false);
			result.put("msg", e1.getMessage());
		}
		
		if(userActivity.getActivityId() != null) {
			try {
				if(userId != null) {
					userActivity.setUserId(userId);
				}else {
					result.put("success", false);
					result.put("msg", "您未登录");
					return result;
				}
				//选课
				boolean success = activityService.chooseActivity(userActivity);
				if(success) {
					result.put("success", true);
					result.put("msg", "");
				}else {
					result.put("success", false);
					result.put("msg", "选课失败");
				}
			} catch (LaboratoryException e) {
				result.put("success", false);
				result.put("msg", e.getMessage());
			}
		}else {
			result.put("success", false);
			result.put("msg", "请选择您要选择的课程");
		}
		return result;
	}
	
	
	/**
	 * query activity that type is ok
	 * @param activity
	 * @return
	 */
	@RequestMapping(value ="/selectall",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryAllActivity(Activity activity){
		Map<String,Object> result = new HashMap<>();
		//前台用户只能查看当前开放的课程
		if(activity.getStatus() == null) {
			activity.setStatus(LESSION_TYPE_OK);
		}
		//activity中只有一个属性被赋值，就是state
		List<ActivityDetailResult> list = activityService.queryAllActivity(activity);
		if(list != null && list.size()!=0) {
			result.put("success", true);
			result.put("lessions",list);
			result.put("msg", "");
		}else {
			result.put("success", false);
			result.put("msg","开课课程为空");
		}
		return result;
	}
	
	/**
	 * 得到当前周数的算法
	 * @return
	 */
	private Integer getCurrWeek() {
		Calendar calendar = Calendar.getInstance();  
		int i = calendar.get(Calendar.YEAR);
		int j = calendar.get(Calendar.MONTH);
		if(j == 2) {
			//return null;
		}else if(j == 8) {
			//return null;
		}else if(j == 1) {
			i-=1;
		}
		LocalDate startDate = null;
		if(j==9 || j==10 || j==11 || j==12 || j==1) {
			startDate = LocalDate.of(i, Month.SEPTEMBER,3);
		}else if(j==3 || j==4 || j==5 || j==6 || j==7){
			startDate = LocalDate.of(i, Month.MARCH,1);
		}
 
        long until = until(startDate);
        until=-1*until;
        int week = (int) (until/7);
        if(until%7 >0) {
        	week+=1;
        }
        return week;
	}

	/**
     * 计算当前日期与{@code endDate}的间隔天数
     *
     * @param endDate
     * @return 间隔天数
     */
	private long until(LocalDate endDate){
	      return LocalDate.now().until(endDate, ChronoUnit.DAYS);
    }

}
