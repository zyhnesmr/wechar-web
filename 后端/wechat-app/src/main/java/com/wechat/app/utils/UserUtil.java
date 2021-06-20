package com.wechat.app.utils;

import com.wechat.app.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * 用户工具类
 */
public class UserUtil {
  /**
   * 获取当前登录用户实体
   * @return
   */
  public static User getCurrentUser(){
    return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
  }
}
