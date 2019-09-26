package com.jc.laboratory.dto;

import com.jc.laboratory.entity.Tool;

/**
 * 工具本身和被借还是被还，以及相关的username
 * @author jc
 *
 */
public class ToolResult extends Tool {
	private boolean borrowed;

	public boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
