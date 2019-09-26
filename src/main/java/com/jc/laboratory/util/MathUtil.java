package com.jc.laboratory.util;

import java.util.UUID;

public class MathUtil {
	
	
	/**
	 * return five number 
	 * @return
	 */
	public static String gernerateVerifyCode() {
		return String.valueOf((int)((Math.random()*9+1)*10000));
	}
	
	/**
	 * generate UUID
	 * @return
	 */
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }


}
