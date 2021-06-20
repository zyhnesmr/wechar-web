package com.wechat.app.controller;

import com.github.binarywang.java.emoji.EmojiConverter;
import com.wechat.app.entity.GroupMsgContent;
import com.wechat.app.entity.Message;
import com.wechat.app.entity.User;
import com.wechat.app.service.GroupMsgContentService;
import com.wechat.app.utils.TuLingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class WsController {
  @Autowired
  SimpMessagingTemplate simpMessagingTemplate;

  /**
   * 单聊的消息的接受与转发
   * @param authentication
   * @param message
   */
  @MessageMapping("/ws/chat")
  public void handleMessage(Authentication authentication, Message message){
    //实际上，Authentication对象有未认证和已认证两种状态，在作为参数传入认证管理器（AuthenticationManager）的
    // authenticate方法时，是一个未认证的对象，它从客户端获取用户的身份信息（如用户名，密码），
    // 可以是从一个登录页面，也可以从Cookie中获取，并由系统自动构造成一个Authentication对象
    // Spring Security要做的就是将这个未认证的Authentication对象和UserDetails进行匹配，
    // 成功后将UserDetails中的用户权限信息拷贝到Authentication中组成一个完整的Authentication对象，共其它组件共享
    User user= ((User) authentication.getPrincipal());
    message.setFromNickname(user.getNickname());
    message.setFrom(user.getUsername());
    message.setCreateTime(new Date());
    simpMessagingTemplate.convertAndSendToUser(message.getTo(),"/queue/chat",message);
  }

  @Autowired
  GroupMsgContentService groupMsgContentService;
  EmojiConverter emojiConverter = EmojiConverter.getInstance();

  SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  /**
   * 群聊的消息接受与转发
   * @param authentication
   * @param groupMsgContent
   */
  @MessageMapping("/ws/groupChat")
  public void handleGroupMessage(Authentication authentication, GroupMsgContent groupMsgContent){
    User currentUser= (User) authentication.getPrincipal();
    //处理emoji内容,转换成unicode编码
    groupMsgContent.setContent(emojiConverter.toHtml(groupMsgContent.getContent()));
    //保证来源正确性，从Security中获取用户信息
    groupMsgContent.setFromId(currentUser.getId());
    groupMsgContent.setFromName(currentUser.getNickname());
    groupMsgContent.setFromProfile(currentUser.getUserProfile());
    groupMsgContent.setCreateTime(new Date());
    //保存该条群聊消息记录到数据库中
    groupMsgContentService.insert(groupMsgContent);
    //转发该条数据
    simpMessagingTemplate.convertAndSend("/topic/greetings",groupMsgContent);
  }

  /**
   * 接受前端发来的消息，获得图灵机器人回复并转发回给发送者
   * @param authentication
   * @param message
   * @throws IOException
   */
  @MessageMapping("/ws/robotChat")
  public void handleRobotChatMessage(Authentication authentication, Message message) throws IOException {
    User user = ((User) authentication.getPrincipal());
    //接收到的消息
    message.setFrom(user.getUsername());
    message.setCreateTime(new Date());
    message.setFromNickname(user.getNickname());
    message.setFromUserProfile(user.getUserProfile());
    //发送消息内容给机器人，获得回复
    String result = TuLingUtil.replyMessage(message.getContent());
    //构建返回消息JSON字符串
    Message resultMessage = new Message();
    resultMessage.setFrom("瓦力");
    resultMessage.setCreateTime(new Date());
    resultMessage.setFromNickname("瓦力");
    resultMessage.setContent(result);
    //回送机器人回复的消息给发送者
    simpMessagingTemplate.convertAndSendToUser(message.getFrom(),"/queue/robot",resultMessage);
  }
}
