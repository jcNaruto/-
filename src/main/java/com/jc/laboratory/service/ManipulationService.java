package com.jc.laboratory.service;

import java.util.List;

import com.jc.laboratory.entity.Manipulation;
import com.jc.laboratory.exception.LaboratoryException;

public interface ManipulationService {
	
	/**
	 * 查看特定用户的操作历史
	 * @param userId
	 * @return
	 * @throws LaboratoryException
	 */
	public List<Manipulation> selectAll(String userId) throws LaboratoryException;

}
