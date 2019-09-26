package com.jc.laboratory.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.laboratory.dao.ManipulationMapper;
import com.jc.laboratory.dao.ToolMapper;
import com.jc.laboratory.dao.UserMapper;
import com.jc.laboratory.dto.ToolResult;
import com.jc.laboratory.entity.Manipulation;
import com.jc.laboratory.entity.Tool;
import com.jc.laboratory.entity.User;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.ToolService;
import com.jc.laboratory.util.JedisClientPool;
import com.jc.laboratory.util.MathUtil;

/**
 * tool function impl
 * @author jc
 *
 */
@Service
public class ToolServiceImpl implements ToolService {

	@Autowired
	private ToolMapper toolMapper;
	@Autowired
	private JedisClientPool jedis;
	@Autowired
	private ManipulationMapper manipulationMapper;
	@Autowired
	private UserMapper userMapper;
	
	private static final String BORROW = "0";
	private static final String BACK = "1";

	@Override
	public List<ToolResult> selectAll() throws LaboratoryException {
		List<ToolResult> resultList = new ArrayList<>();
		try {
			List<Tool> list = toolMapper.selectByExample(null);
			for(Tool tool : list) {
				ToolResult result = new ToolResult();
				if(tool.getIsBorrowed() == 1) {
					User user = userMapper.selectByPrimaryKey(tool.getUserId());
					result.setUserName(user.getName());
				}
				copy(result,tool);
				resultList.add(result);	
			}
			return resultList;
		} catch (Exception e) {
			throw new LaboratoryException("查询所有tool失败!");
		}
	}

	@Override
	public String borrowTool(Integer boxId, Integer isUsed, String userId) throws LaboratoryException {
		String key = MathUtil.getUUID();
		String realKey = key+"-"+userId+"-"+isUsed;
		try {
			jedis.save(realKey, String.valueOf(boxId), (long) 60);
		} catch (Exception e) {
			throw new LaboratoryException("操作redis异常");
		}
		return realKey;
	}

	@Override
	public String toolFeedBack(String key) throws LaboratoryException {
		String[] split = key.split("-");
		String value = null;
		try {
			value = jedis.get(key);
		} catch (Exception e) {
			throw new LaboratoryException("Redis数据库操作异常");
		}
		if(value != null) {
			Manipulation manipulation = new Manipulation();
			Tool tool = new Tool();
			tool.setBoxId(Integer.valueOf(value));
			manipulation.setBoxId(Integer.valueOf(value));
			if(BACK.equals(split[2])) {
				manipulation.setFunctionType(3);
				tool.setIsBorrowed(0);
				tool.setUserId(split[1]);
				toolMapper.updateByPrimaryKeySelective(tool);
			}else if(BORROW.equals(split[2])) {
				manipulation.setFunctionType(2);
				tool.setIsBorrowed(1);
				tool.setUserId(split[1]);
				toolMapper.updateByPrimaryKeySelective(tool);
			}
			manipulation.setTime(new Date());
			manipulation.setUserId(split[1]);
			try {
				manipulationMapper.insert(manipulation);
			} catch (Exception e) {
				throw new LaboratoryException("操作记录插入异常");
			}
		}
		return value;
	}

	@Override
	public String backTool(Integer boxId, Integer isUsed, String userId) throws LaboratoryException {
		String key = MathUtil.getUUID();
		String realKey = key+"-"+userId+"-"+isUsed;
		try {
			jedis.save(realKey, String.valueOf(boxId), (long) 60);
		} catch (Exception e) {
			throw new LaboratoryException("操作redis异常");
		}
		return realKey;
	}
	
	
	private void copy(ToolResult result,Tool tool) {
		result.setBoxId(tool.getBoxId());
		result.setCreateTime(tool.getCreateTime());
		if(tool.getIsBorrowed() == 1) {
			result.setBorrowed(true);
		}else if(tool.getIsBorrowed() == 0) {
			result.setBorrowed(false);
		}
		result.setIsBorrowed(tool.getIsBorrowed());
		result.setName(tool.getName());
		result.setUpdateTime(tool.getUpdateTime());
		result.setUserId(tool.getUserId());
	}
	
}
