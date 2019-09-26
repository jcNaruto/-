package com.jc.laboratory.exception;
/**
 * 
 * @author jc
 *
 */
public class LaboratoryException extends Exception {
	// 异常信息
	private String laboratoryMessage;

	public LaboratoryException() {
		super();
	}

	public LaboratoryException(String laboratoryMessage) {
		super();
		this.laboratoryMessage = laboratoryMessage;
	}

	public String getLaboratoryMessage() {
		return laboratoryMessage;
	}

	public void setLaboratoryMessage(String laboratoryMessage) {
		this.laboratoryMessage = laboratoryMessage;
	}

}