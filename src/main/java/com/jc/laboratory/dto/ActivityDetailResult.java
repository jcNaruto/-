package com.jc.laboratory.dto;

import java.util.List;

import com.jc.laboratory.entity.Activity;
import com.jc.laboratory.entity.ActivityDetail;

/**
 * 手动封装的数据传输类，将符合前端业务需求的数据组合为一个实体便于操作
 * 课程，课程详细集合，应为有的课程的开课周数是不连续的例如1至7周上，10·15周上，所以要用多个ActivityDetail
 * 存储
 * @author jc
 *
 */
public class ActivityDetailResult extends Activity {
	
	private List<ActivityDetail> activityDetails;

	public List<ActivityDetail> getActivityDetails() {
		return activityDetails;
	}

	public void setActivityDetails(List<ActivityDetail> activityDetails) {
		this.activityDetails = activityDetails;
	}
	
	
}
