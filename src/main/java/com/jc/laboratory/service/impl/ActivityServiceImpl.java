package com.jc.laboratory.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.laboratory.dao.ActivityDetailMapper;
import com.jc.laboratory.dao.ActivityMapper;
import com.jc.laboratory.dao.UserActivityMapper;
import com.jc.laboratory.dto.ActivityDetailResult;
import com.jc.laboratory.dto.ActivityResourceResult;
import com.jc.laboratory.entity.Activity;
import com.jc.laboratory.entity.ActivityDetail;
import com.jc.laboratory.entity.ActivityDetailExample;
import com.jc.laboratory.entity.ActivityExample;
import com.jc.laboratory.entity.ActivityExample.Criteria;
import com.jc.laboratory.entity.UserActivity;
import com.jc.laboratory.entity.UserActivityExample;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.ActivityService;

/**
 * Activity function Service Impl
 * @author jc
 *
 */
@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private UserActivityMapper userActivityMapper;
	@Autowired
	private ActivityDetailMapper activityDetailMapper;
	
	private static final Integer LESSION_NOT_COMPLETE = 0;
	
	
	@Override
	/**
	 * check my activity
	 * @param userId
	 * @return
	 */
	public List<ActivityResourceResult> myActivity(String userId) {
		List<ActivityResourceResult> resultList = new ArrayList<>();
		UserActivityExample example = new UserActivityExample();
		com.jc.laboratory.entity.UserActivityExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<UserActivity> list = userActivityMapper.selectByExample(example);
		//判断学生是否选课
		if(list.size() == 0) {
			return resultList;
		}
		
		for(UserActivity ua : list) {
			ActivityResourceResult arr = new ActivityResourceResult();
			//查询分数和完成情况
			Activity activity = activityMapper.selectByPrimaryKey(ua.getActivityId());
			arr.setIsComplete(ua.getIsComplete());
			arr.setScore(ua.getScore());
			//设置该课程的具体信息
			ActivityDetailResult activityDetailResult = new ActivityDetailResult();
			ActivityDetailExample example2 = new ActivityDetailExample();
			com.jc.laboratory.entity.ActivityDetailExample.Criteria createCriteria = example2.createCriteria();
			createCriteria.andActivityIdEqualTo(activity.getActivityId());
			activityDetailResult.setActivityDetails(activityDetailMapper.selectByExample(example2));
			//将具体的课程信息封装到返回dto中
			copy(activity,activityDetailResult);
			arr.setActivityDetailResult(activityDetailResult);
			resultList.add(arr);
		}
		
		return resultList;
	}

	
	
	@Override
	/**
	 * user choose activity
	 * can not choose repeat
	 * @param activityId
	 * @param userId
	 * @return
	 */
	public boolean chooseActivity(UserActivity userActivity) throws LaboratoryException {
		UserActivity isExist = userActivityMapper.selectByPrimaryKey(userActivity);
		if(isExist != null) {
			throw new LaboratoryException("你以选过该课程!!");
		}else {
			//选课之初就设置该课程未完成
			userActivity.setIsComplete(LESSION_NOT_COMPLETE);
			int effectRow = userActivityMapper.insert(userActivity);
			if(effectRow == 1) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	@Override
	/**
	 * insert activity include all type
	 * @param activity
	 * @return
	 */
	public boolean insertActivity(Activity activity) {
		activity.setCreateTime(new Date());
		activity.setRestNumber(activity.getMaxNumber());
		activity.setUpdateTime(new Date());
		activity.setType(1);
		int effectRow = activityMapper.insert(activity);
		if(effectRow == 1) {
			return true;
		}
		return false;
	}

	@Override
	/**
	 * query all activity 
	 * can query by criteria type by provide a activity that include attr Type
	 */
	public List<ActivityDetailResult> queryAllActivity(Activity activity) {
		ActivityExample example = new ActivityExample();
		if(activity != null && activity.getStatus() != null) {
			Criteria criteria = example.createCriteria();
			criteria.andStatusEqualTo(activity.getStatus());
		}
		List<Activity> list = activityMapper.selectByExample(example);
		
		List<ActivityDetailResult> activityDetailResults = new ArrayList<>();
		for(Activity activityNoDetail : list) {
			ActivityDetailResult result = new ActivityDetailResult();
			
			ActivityDetailExample example2 = new ActivityDetailExample();
			com.jc.laboratory.entity.ActivityDetailExample.Criteria createCriteria = example2.createCriteria();
			createCriteria.andActivityIdEqualTo(activityNoDetail.getActivityId());
			List<ActivityDetail> activityDetails = activityDetailMapper.selectByExample(example2);
			result.setActivityDetails(activityDetails);
			
			copy(activityNoDetail,result);
			activityDetailResults.add(result);
		}
		return activityDetailResults;
	}
	
	/**
	 * 将数据activity的数据封装到dto中的私有方法
	 * @param activity
	 * @param activityDetailResult
	 */
	private void copy(Activity activity,ActivityDetailResult activityDetailResult) {
		activityDetailResult.setActivityId(activity.getActivityId());
		activityDetailResult.setCreateTime(activity.getCreateTime());
		activityDetailResult.setMaxNumber(activity.getMaxNumber());
		activityDetailResult.setName(activity.getName());
		activityDetailResult.setRestNumber(activity.getRestNumber());
		activityDetailResult.setStatus(activity.getStatus());
		activityDetailResult.setTeacher(activity.getTeacher());
		activityDetailResult.setType(activity.getType());
		activityDetailResult.setUpdateTime(activity.getUpdateTime());		
	}
	
}
