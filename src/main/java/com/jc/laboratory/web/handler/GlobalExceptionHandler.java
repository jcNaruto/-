package com.jc.laboratory.web.handler;



import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.alibaba.fastjson.JSON;

/**
 * 全局异常处理器，进行日志记录等的操作
 * @author jc
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {
    	// 日志记录堆栈错误信息
        logger.error(ExceptionUtils.getFullStackTrace(e));  
        String msg = e.getMessage();
        if (msg == null || msg.equals("")) {
            msg = "服务器出错";
        }
        
        Map<String,Object> jsonObject = new HashMap<>();
        jsonObject.put("success", false);
        jsonObject.put("msg", msg);
        String jsonString = JSON.toJSONString(jsonObject);
        logger.error("===========error==========="+jsonString);
    }
}

