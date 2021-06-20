package com.wechat.app.controller;

import com.wechat.app.entity.GroupMsgContent;
import com.wechat.app.entity.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;

/**
 * 邮箱服务控制器
 */
@RestController
@RequestMapping("/mail")
public class MailController {

  @Autowired
  JavaMailSender javaMailSender;
  /**
   * 发送反馈消息给系统管理员
   * @param msg
   * @return
   */
  @PostMapping("/feedback")
  public RespBean sendFeedbackToMail(@RequestBody GroupMsgContent msg){
    SimpleMailMessage message = new SimpleMailMessage();
    message.setSubject("来自用户的意见反馈");
    //读取信息
    StringBuilder formatMessage = new StringBuilder();
    formatMessage.append("用户编号："+msg.getFromId()+"\n");
    formatMessage.append("用户昵称："+msg.getFromName()+"\n");
    formatMessage.append("反馈内容："+msg.getContent());
    System.out.println(">>>>>>>>>>>>>>"+formatMessage+"<<<<<<<<<<<<<<<<<<");
    //设置邮件消息
    message.setText(formatMessage.toString());
    message.setFrom("");
    message.setTo("2424913305@qq.com");
    message.setSentDate(new Date());
    try {
      javaMailSender.send(message);
      return RespBean.ok("邮件发送成功！感谢你的反馈~");
    }catch (Exception e){
      return RespBean.error("系统异常，请稍后重试！");
    }
  }
}
