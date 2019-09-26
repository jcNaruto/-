package com.jc.laboratory.dto;

import java.util.List;

import com.jc.laboratory.entity.Activity;
import com.jc.laboratory.entity.ActivityDetail;

/**
 * 在activityDetailResult的基础上增加了分数和是否完成的属性
 * @author jc
 *
 */
public class ActivityResourceResult {

	private Integer isComplete;
	
	private Integer score;
	
	private ActivityDetailResult activityDetailResult;
	

	public ActivityDetailResult getActivityDetailResult() {
		return activityDetailResult;
	}

	public void setActivityDetailResult(ActivityDetailResult activityDetailResult) {
		this.activityDetailResult = activityDetailResult;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}


	
	
}
