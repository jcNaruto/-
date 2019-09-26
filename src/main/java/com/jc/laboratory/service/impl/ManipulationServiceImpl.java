package com.jc.laboratory.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.laboratory.dao.ManipulationMapper;
import com.jc.laboratory.entity.Manipulation;
import com.jc.laboratory.entity.ManipulationExample;
import com.jc.laboratory.entity.ManipulationExample.Criteria;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.ManipulationService;

/**
 * manipulation function impl
 * @author jc
 *
 */
@Service
public class ManipulationServiceImpl implements ManipulationService {

	@Autowired
	private ManipulationMapper manipulationMapper;

	@Override
	/**
	 * get all manipulate
	 */
	public List<Manipulation> selectAll(String userId) throws LaboratoryException {
		ManipulationExample example = new ManipulationExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		try {
			List<Manipulation> list = manipulationMapper.selectByExample(example);
			return list;
		} catch (Exception e) {
			throw new LaboratoryException("查询用户操作失败");
		}
	}
}
