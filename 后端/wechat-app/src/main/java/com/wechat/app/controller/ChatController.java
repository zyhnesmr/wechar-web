package com.wechat.app.controller;

import com.wechat.app.entity.Dynamics;
import com.wechat.app.entity.RespCode;
import com.wechat.app.entity.User;
import com.wechat.app.service.DynamicsService;
import com.wechat.app.service.UserService;
import com.wechat.app.utils.UserUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/chat")
public class ChatController {
  @Autowired
  UserService userService;

  @Autowired
  DynamicsService dynamicsService;

  @GetMapping("/users")
  public List<User> getUsersWithoutCurrentUser(){
    return userService.getUserFriends();
  }

  @GetMapping("/update")
  public String updateNicknameAndUserProfile(@RequestParam("nickname")String nickname,@RequestParam("user_profile")String user_profile){
    User currentUser = UserUtil.getCurrentUser();
    userService.updateNicknameAndUserProfile(nickname,user_profile,currentUser.getUsername());
    return "更新成功！";
  }

  @GetMapping("/recommand")
  public List<User> getRecommandedUser(){
    return userService.getNewFriends(UserUtil.getCurrentUser().getUsername());
  }

  @GetMapping("/dynamics")
  public List<Dynamics> queryAllDynamics(){
    return dynamicsService.queryAllDynamics();
  }

  @GetMapping("/selfdynamics")
  public List<Dynamics> querySelfDynamics(){
    User currentUser = UserUtil.getCurrentUser();
    return dynamicsService.queryByUsername(currentUser.getUsername());
  }
  @GetMapping("/putdynamics")
  public void putDynamic(@RequestParam("content") String content,@RequestParam("create_time")String create_time) throws ParseException {
    Dynamics dynamics = new Dynamics();
    dynamics.setContent(content);
    dynamics.setCreate_time(create_time);
    dynamics.setNickname(UserUtil.getCurrentUser().getNickname());
    dynamics.setUser_id(UserUtil.getCurrentUser().getUsername());
    dynamics.setUser_profile(UserUtil.getCurrentUser().getUserProfile());
    dynamicsService.insertDynamics(dynamics);
  }

  @GetMapping("/searchuser")
  public RespCode searchUser(@RequestParam("username")String username){
    List<User> users = userService.queryByUsername(username);
    RespCode respCode = new RespCode();
    if (users.size()>0){
      userService.insertTemp(UserUtil.getCurrentUser().getUsername(),username);
      respCode.setCode(0);
    }else {
      respCode.setCode(-1);
    }
    return respCode;
  }

//  @GetMapping("/adduser")
//  public String addUser(@RequestParam("username")String username){
//    userService.insertTemp(UserUtil.getCurrentUser().getUsername(),username);
//    return "已发出申请";
//  }

  @GetMapping("/getrequest")
  public List<User> getAllRequest(){
    return userService.getAllRequest(UserUtil.getCurrentUser().getUsername());
  }

  @GetMapping("/refuse")
  public void refuse(@RequestParam("from_id")String from_id){
    userService.deleteAddUser(from_id,UserUtil.getCurrentUser().getUsername());
  }

  @GetMapping("/accept")
  public void accept(@RequestParam("from_id")String from_id){

    userService.deleteAddUser(from_id,UserUtil.getCurrentUser().getUsername());
    userService.addRelation(from_id,UserUtil.getCurrentUser().getUsername());
  }



}
