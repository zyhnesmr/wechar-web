package com.wechat.app.service;

import com.wechat.app.entity.Dynamics;

import java.util.List;

public interface DynamicsService {
    List<Dynamics> queryAllDynamics();

    List<Dynamics> queryByUsername(String username);

    void insertDynamics(Dynamics dynamics);
}
