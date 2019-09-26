package com.jc.laboratory.service;

import com.jc.laboratory.entity.User;
import com.jc.laboratory.exception.LaboratoryException;

/**
 * user function interface
 * @author jc
 *
 */
public interface UserService {
	
	/**
	 * user update
	 * @param user
	 * @return
	 * @throws LaboratoryException
	 */
	public User update(User user,String captcha) throws LaboratoryException;
	
	/**
	 * user login
	 * @param id
	 * @param pwd
	 * @return
	 * @throws LaboratoryException
	 */
	public User login(String id,String pwd) throws LaboratoryException;
	
	/**
	 * update pwd before email verify
	 * @return
	 * @throws LaboratoryException
	 */
	public boolean updatePwdVefiry(String email) throws LaboratoryException;
	
	/**
	 * judge email is repeat and pass CAPTCHA
	 * @return
	 */
	public boolean verifyEmailAndPassCAPTCHA(String email) throws LaboratoryException;
	
	/**
	 * user register
	 * @param user
	 * @param captcha
	 * @return
	 * @throws LaboratoryException
	 */
	public boolean userRegister(User user,String captcha) throws LaboratoryException;
}
