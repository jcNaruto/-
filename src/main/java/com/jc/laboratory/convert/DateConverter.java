package com.jc.laboratory.convert;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import com.jc.laboratory.exception.LaboratoryException;
import com.jc.laboratory.web.handler.GlobalExceptionHandler;

/**
 * 自定义的转换器，将前端传来字符串转换为date
 * @author jc
 * Converter<S, T>
 *S:source,需要转换的源的类型	
 *T:target,需要转换的目标类型
 *
 */
public class DateConverter implements Converter<String, Date> {
	private Logger logger = LoggerFactory.getLogger(DateConverter.class);
	
	@Override
	public Date convert(String source) {
		try {
			// 把字符串转换为日期类型，前端支持MM/dd/yyyy类型的格式
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
			Date date = simpleDateFormat.parse(source);
			return date;
		} catch (Exception e) {
			logger.error("日期转换失败");
		}
		return null;
	}
}
