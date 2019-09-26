package com.jc.laboratory.service;

import com.jc.laboratory.exception.LaboratoryException;

/**
 * door function interface
 * @author jc
 * 
 *
 */
public interface DoorService {

	/**
	 * 开门的请求接口，返回具有信息的string，用于生成具备信息的二维码
	 * @param userId
	 * @return
	 * @throws LaboratoryException
	 */
	public String openDoor(String userId) throws LaboratoryException;
	
	/**
	 * 开门的反馈接口，当硬件扫描二维码之后会获取到对应的key,发送至该接口，判断二维码是否有效
	 * ，并返回一定的值，硬件根据此值来判断是否开门
	 * @param key
	 * @return
	 * @throws LaboratoryException
	 */
	public String feedBack(String key) throws LaboratoryException;
}
