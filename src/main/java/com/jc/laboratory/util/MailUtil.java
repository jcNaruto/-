package com.jc.laboratory.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * 邮件工具类
 * @author jc
 *
 */
@EnableAsync
public class MailUtil {

	/*异步发送该属性不能通过xml文件中的property注入*/
	
	private Logger logger = LoggerFactory.getLogger(MailUtil.class);
	private JavaMailSender javaMailSender;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	//发件人
	private String from;
	

	@Async
	public void sendMail(String to, String subject, String text) throws Exception{
		logger.info("Async send mail strat ===========");
		//1. 创建邮件信息
		MimeMessage message = javaMailSender.createMimeMessage();
		//message.addRecipients(MimeMessage.RecipientType.CC, from);//设置抄送
		//2. 使用spring邮件工具类
		MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
		//3.收件人
		helper.setTo(to);
		//4.发件人
		helper.setFrom(from);
		//5.设置邮件的标题
		helper.setSubject(subject);
		//6.邮件的内容
		
		String html2 = "<div style='border:2px solid #D3D3D3;'><div class='header'>"+
        "<img width='100%' height='30%' src='cid:demoimg' alt='dlut'/>"+
    "</div><hr color=\"#009ACD\">"+
				
				"<div class='content'>"+
       "你好，同学:<br>"+
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
        "<font size='5' style='display:block;text-align: center'>验证您是该电子邮箱地址地所有者：</font>"+
        "<span id='youremail' style='font-size:30px;display:block;color: #009ACD;text-align: center' >12121@qq.com</span>"+
        "<br><br>"+
        "<font size='4' style='display:block;text-align: center'>有人在验证电子邮件地址时输入了该电子邮件地址"+
        "您可以使用此验证码来验证您是该电子邮件地址的所有者。</font>"+
        "<div id='verifycode'>"+
        "    <span id='verifycode' style='font-size:40px;display:block;color: #009ACD;text-align: center' >12345</span>"+
        "</div>"+
         "<br><br>"+
        "<font size='4' style='display:block;text-align: center'>如果这不是您本人所为，则可能是有人误输了您的电子邮件地址。请勿将此验证码泄露给他人，并且您目前无需执行任何其它操作。</font>"+
        "<div style='color: gray;'>"+
           " smartLab团队敬上(<span id='time'>10分钟后失效</span>)"+
     "   </div>"+
    "</div></div>";
		String replace1 = html2.replaceAll("12345", text);
		String replaceAll = replace1.replaceAll("12121@qq.com", to);
		ClassPathResource resource = new ClassPathResource("image/dlut.jpg"); // 加载项目路径下资源
		 // 将资源绑定到demoimg上
		/*helper.setText(html);*/
		helper.setText(replaceAll, true); // 邮件内容，参数true表示是html代码
		helper.addInline("demoimg", resource);
		//7.发送邮件
		javaMailSender.send(message);
		//System.out.println("===========================邮件已发送=====================");
		logger.info("Async send mail end ===========");
	}
	

	public void setFrom(String from) {
		this.from = from;
	}
}
