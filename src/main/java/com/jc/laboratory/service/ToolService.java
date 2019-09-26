package com.jc.laboratory.service;

import java.util.List;

import com.jc.laboratory.dto.ToolResult;
import com.jc.laboratory.exception.LaboratoryException;

public interface ToolService {
	
	/**
	 * 查看当前所有物品以及状态
	 * @return
	 * @throws LaboratoryException
	 */
	public List<ToolResult> selectAll() throws LaboratoryException;
	
	/**
	 * 借物品，返回字符串，用该字符串来生成二维码
	 * @param boxId
	 * @param isUsed
	 * @param userId
	 * @return
	 * @throws LaboratoryException
	 */
	public String borrowTool(Integer boxId,Integer isUsed,String userId) throws LaboratoryException;
	
	/**
	 * 借还物品的硬件调用接口
	 * @param key
	 * @return
	 * @throws LaboratoryException
	 */
	public String toolFeedBack(String key) throws LaboratoryException;

	/**
	 * 还物品，返回字符串，用该字符串来生成二维码
	 * @param boxId
	 * @param isUsed
	 * @param userId
	 * @return
	 * @throws LaboratoryException
	 */
	public String backTool(Integer boxId, Integer isUsed, String userId) throws LaboratoryException;

}
