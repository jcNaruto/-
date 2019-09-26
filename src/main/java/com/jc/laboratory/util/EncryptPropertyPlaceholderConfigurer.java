package com.jc.laboratory.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * decrypt
 * @author jc
 *
 */
public class EncryptPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	//need decrypt column
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password","mail.username",
			"mail.password","redis.password"};

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryptProp(propertyName)) {
			String decryptValue = DESUtils.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}
	}

	private boolean isEncryptProp(String propertyName) {
		for (String encryptpropertyName : encryptPropNames) {
			if (encryptpropertyName.equals(propertyName)) {
				return true;
			}
		}
		return false;
	}
}
