package com.jc.laboratory.service;

import java.util.List;

import com.jc.laboratory.dto.ActivityDetailResult;
import com.jc.laboratory.dto.ActivityResourceResult;
import com.jc.laboratory.entity.Activity;
import com.jc.laboratory.entity.UserActivity;
import com.jc.laboratory.exception.LaboratoryException;

/**
 * activity function service interface
 * @author jc
 *
 */
public interface ActivityService {
	
	/**
	 * check my activity
	 * @param userId
	 * @return
	 */
	List<ActivityResourceResult> myActivity(String userId);
	
	
	/**
	 * user choose activity
	 * @param activityId
	 * @param userId
	 * @return
	 */
	boolean chooseActivity(UserActivity userActivity) throws LaboratoryException;
	
	/**
	 * query all activity 
	 * all type use this method
	 * @param activity
	 * @return
	 */
	List<ActivityDetailResult> queryAllActivity(Activity activity);
	
	
	/**
	 * insert activity include all type
	 * @param activity
	 * @return
	 */
	boolean insertActivity(Activity activity);

}
