package com.jc.laboratory.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.laboratory.dao.ManipulationMapper;
import com.jc.laboratory.dao.UserMapper;
import com.jc.laboratory.entity.Manipulation;
import com.jc.laboratory.entity.User;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.DoorService;
import com.jc.laboratory.util.JedisClientPool;
import com.jc.laboratory.util.MathUtil;

@Service
public class DoorServiceImpl implements DoorService {
	
	@Autowired
	private ManipulationMapper manipulationMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisClientPool jedis;
	
	private static final String OPEN_DOOR_NORMALLY = "200";

	@Override
	/**
	 * return uuid 
	 * uuid - status
	 * 返回具备信息的用于生成二维码的uuid
	 */
	public String openDoor(String userId) throws LaboratoryException {
		User user = null;
		String key = MathUtil.getUUID();
		key+="-"+userId;
		String value = "200";
		try {
			user = userMapper.selectByPrimaryKey(userId);
		} catch (Exception e) {
			throw new LaboratoryException("操作数据库异常");
		}
		if(user == null || user.getIsAllowed() == 0) {
			value = "404";
			throw new LaboratoryException("该用户未得到授权访问door");
		}
		try {
			jedis.save(key, value, (long) 60);
		} catch (Exception e) {
			throw new LaboratoryException("操作redis数据库异常");
		}
		return key;
	}

	@Override
	/**
	 * hard ware request
	 * 硬件扫码之后请求的接口
	 */
	public String feedBack(String key) throws LaboratoryException {
		String[] split = key.split("-");
		String status = "404";
		try {
			status = jedis.get(key);
		} catch (Exception e) {
			throw new LaboratoryException("操作redis数据库异常");
		}
		if(status != null && OPEN_DOOR_NORMALLY.equals(status)) {
			Manipulation manipulation = new Manipulation();
			manipulation.setFunctionType(1);//开门
			manipulation.setTime(new Date());
			manipulation.setUserId(split[1]);
			manipulation.setBoxId(0);
			manipulationMapper.insert(manipulation);
		}
		return status;
	}
	
	

}
