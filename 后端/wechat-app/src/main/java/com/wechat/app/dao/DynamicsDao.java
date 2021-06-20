package com.wechat.app.dao;

import com.wechat.app.entity.Dynamics;

import java.util.List;

public interface  DynamicsDao {

    List<Dynamics> queryAllDynamics();

    List<Dynamics> queryByUsername(String username);

    void insertDynamics(Dynamics dynamics);
}
