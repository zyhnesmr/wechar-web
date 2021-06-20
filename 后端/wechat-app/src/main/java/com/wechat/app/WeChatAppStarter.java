package com.wechat.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wechat.app.dao")
public class WeChatAppStarter {
    public static void main(String[] args) {
        SpringApplication.run(WeChatAppStarter.class,args);
    }
}
