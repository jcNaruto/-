package com.jc.laboratory.service.impl;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.laboratory.dao.UserMapper;
import com.jc.laboratory.entity.User;
import com.jc.laboratory.entity.UserExample;
import com.jc.laboratory.entity.UserExample.Criteria;
import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.service.UserService;
import com.jc.laboratory.util.JedisClientPool;
import com.jc.laboratory.util.MD5Util;
import com.jc.laboratory.util.MailUtil;
import com.jc.laboratory.util.MathUtil;

/**
 * user function impl
 * @author jc
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisClientPool jedis;
	@Autowired
	private MailUtil mailUtil;

	
	/**
	 * 散列次数
	 * MD5不可逆的加密算法
	 */
	private final int hashIteration = 2;
	
	private final int initialCoin = 0;
	private final int initialStatus = 0;
	private final int initialRole = 0;
	
	private final Long expiredTime = (long) (10*60);//邮箱验证码有效时间
	private Long expiredTime2 = 45L;//二维码有效时间
	
	private final Integer registerType = 0;
	private final Integer updatePwdType = 1;
	
	
	private final String REGISTER_REDIS_KEY = "register";//注册Email的redis标识的前缀
	private final String UPDATEPWD_REDIS_KEY = "updatepwd";//更新密码的发送Email的redis标识的前缀
	private final String GET_CAPCTHA_MANY_KEY = "getcaptcha";//获取验证码的redis标识的前缀，防止用户获取验证码频繁
	private final String GET_CAPCTHA_MANY_VALUE = "getcaptchamany";//用户获取验证码频繁的redis value
	
	
	@Override
	/**
	 * user update
	 * @param user
	 * @return
	 * @throws LaboratoryException
	 */
	public User update(User user,String captcha) throws LaboratoryException {
		try {
			if(user.getPwdHash() != null && captcha != null) {
				String realCAPTCHA = jedis.get(UPDATEPWD_REDIS_KEY+user.getEmail());
				if(!captcha.equals(realCAPTCHA)) {
					throw new LaboratoryException("验证码错误");
				}else {
					user.setPwdHash(encrypt(user.getPwdHash(),user.getUserId()));
				}
			}else if(user.getPwdHash() != null && captcha == null) {
				throw new LaboratoryException("请输入验证码");
			}
			int effectRow = 0;
			if(user.getPwdHash() != null && captcha != null) {
				UserExample axample = new UserExample();
				Criteria criteria = axample.createCriteria();
				criteria.andEmailEqualTo(user.getEmail());
				criteria.andUserIdEqualTo(user.getUserId());
				effectRow = userMapper.updateByExampleSelective(user, axample);
				if(effectRow == 1) {
					return  userMapper.selectByExample(axample).get(0);
				}else {
					throw new LaboratoryException("用户数据更新失败");
				}
			}else {
				effectRow = userMapper.updateByPrimaryKeySelective(user);
			}
			if(effectRow == 1) {
				return userMapper.selectByPrimaryKey(user.getUserId());
			}else {
				throw new LaboratoryException("用户数据更新失败");
			}
		} catch (Exception e) {
			throw new LaboratoryException(e.getMessage());
		}
	}
	
	@Override
	/**
	 * user login
	 * @param id
	 * @param pwd
	 * @return
	 * @throws LaboratoryException
	 */
	public User login(String id, String pwd) throws LaboratoryException {
		String realPwd = encrypt(pwd,id);
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(id);
		criteria.andPwdHashEqualTo(realPwd);
		List<User> list;
		try {
			list = userMapper.selectByExample(example);
		} catch (Exception e1) {
			throw new LaboratoryException("连接数据库失败");
		}
		if(list == null || list.size() != 1) {
			throw new LaboratoryException("用户名或者密码错误");
		}else {
			return list.get(0);
		}
	}
	
	@Override
	/**
	 * update pwd before email verify
	 * @return
	 * @throws LaboratoryException
	 */
	public boolean updatePwdVefiry(String email) throws LaboratoryException {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> list;
		try {
			list = userMapper.selectByExample(example);
		} catch (Exception e1) {
			throw new LaboratoryException("检验该email是否已注册失败");
		}
		if(list==null || list.size()==0) {//数据库中没有相同的email的账户
			throw new LaboratoryException("该邮箱未被注册！");
		}
		
		proCaptchaAndSendEmail(email,null,updatePwdType);
		return true;
	}

	
	@Override
	/**
	 * user register
	 * @param user
	 * @param captcha
	 * @return
	 * @throws LaboratoryException
	 */
	public boolean userRegister(User user, String captcha) throws LaboratoryException {
		String realCaptcha = jedis.get(REGISTER_REDIS_KEY+user.getEmail());
		if(realCaptcha != null) {
			if(realCaptcha.equals(captcha)) {
				UserExample example = new UserExample();
				Criteria criteria = example.createCriteria();
				criteria.andUserIdEqualTo(user.getUserId());
				List<User> list;
				try {
					list = userMapper.selectByExample(example);
				} catch (Exception e) {
					throw new LaboratoryException("检验该学号是否被注册失败");
				}
				if(list!=null && list.size()!=0) {//数据库中存在相同的学号的账户
					throw new LaboratoryException("该学号已被注册！");
				}
				user.setCoin(initialCoin);
				user.setCreateTime(new Date());
				user.setIsAllowed(initialStatus);
				user.setRole(initialRole);
				user.setUpdateTime(user.getCreateTime());
				
				user.setAvatarHash(getAvatarHashByEmail(user.getEmail()));
				user.setPwdHash(encrypt(user.getPwdHash(),user.getUserId()));
				try {
					int effectRow = userMapper.insert(user);
					if(effectRow == 1) {
						return true;
					}else {
						throw new LaboratoryException("新增用户失败");
					}
				} catch (Exception e) {
					throw new LaboratoryException("新增用户失败");
				}
			}else {
				throw new LaboratoryException("验证码失效");
			}
		}else {
			throw new LaboratoryException("验证码无效");
		}
	}
	
	@Override
	/**
	 * judge email is repeat and pass CAPTCHA
	 * @return
	 */
	public boolean verifyEmailAndPassCAPTCHA(String email) throws LaboratoryException {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> list;
		try {
			list = userMapper.selectByExample(example);
		} catch (Exception e1) {
			throw new LaboratoryException("检验该email是否已注册失败");
		}
		if(list!=null && list.size()!=0) {//数据库中存在相同的email的账户
			throw new LaboratoryException("该邮箱已被注册！");
		}
		String verifyCode = MathUtil.gernerateVerifyCode();
		proCaptchaAndSendEmail(email,null,registerType);
		return true;
	}
	
	
	
	
	
	/**
	 * MD5加密
	 * @param src 原密码
	 * @param salt 盐 此项目中用用户的id来做
	 * @return
	 */
	private String encrypt(String src, String salt){
		Md5Hash md5 = new Md5Hash(src,salt, hashIteration);
		return md5.toString();
	}
	
	/**
	 * getAvatarHashByEmail 
	 * <img src="https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50" />
	 * 确保email小写
	 * @param email
	 * @return
	 */
	private String getAvatarHashByEmail(String email) {
		String lowerCase = email.toLowerCase();
		String md5Hex = MD5Util.md5Hex(lowerCase);
		return md5Hex;
	}

	
	/**
	 * type 1 register
	 * type 2 update pwd
	 * @param email
	 * @param type
	 * @throws LaboratoryException
	 */
	private void proCaptchaAndSendEmail(String email,String userId,Integer type) throws LaboratoryException{
		String prioGet = jedis.get(GET_CAPCTHA_MANY_KEY+email);
		if(prioGet!= null) {
			throw new LaboratoryException("获取验证码频繁");
		}
		jedis.save(GET_CAPCTHA_MANY_KEY+email, GET_CAPCTHA_MANY_VALUE, expiredTime2);
		String emailSubject;
		if(type.equals(registerType)) {
			emailSubject = "smartLab注册邮箱验证码";
		}else {
			emailSubject = "smartLab修改密码邮箱验证码";
		}
		String verifyCode = MathUtil.gernerateVerifyCode();
		try {
			if(type.equals(registerType)) {
				emailSubject = "smartLab注册邮箱验证码";
				jedis.save(REGISTER_REDIS_KEY+email, verifyCode, expiredTime);
			}else {
				emailSubject = "smartLab修改密码邮箱验证码";
				jedis.save(UPDATEPWD_REDIS_KEY+email, verifyCode, expiredTime);
			}
			
		} catch (Exception e1) {
			throw new LaboratoryException("redis存储验证码失败");
		}//将email-CAPTCHA存储到redis过期时间10min
		//TODO
		try {
			mailUtil.sendMail(email, emailSubject, verifyCode);
			//mailUtil.test();
		} catch (Exception e) {
			throw new LaboratoryException("验证邮箱发送失败！:"+e.getMessage());
		}
	}

	
}
