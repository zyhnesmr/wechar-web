package com.wechat.app.entity;


import java.util.Date;

public class Dynamics {
    private String user_id;
    private String nickname;
    private String content;
    private String create_time;
    private String user_profile;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    @Override
    public String toString() {
        return "Dynamics{" +
                "user_id='" + user_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", create_time=" + create_time +
                ", user_profile='" + user_profile + '\'' +
                '}';
    }
}
