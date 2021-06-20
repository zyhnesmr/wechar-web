package com.wechat.app.service.impl;

import com.wechat.app.dao.DynamicsDao;
import com.wechat.app.entity.Dynamics;
import com.wechat.app.service.DynamicsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
@Service("dynamicsService")
public class DynamicsServiceImpl implements DynamicsService {
    @Resource
    private DynamicsDao dynamicsDao;
    @Override
    public List<Dynamics> queryAllDynamics() {
        List<Dynamics> dynamics = dynamicsDao.queryAllDynamics();
        if (dynamics!=null){
            return dynamics;
        }
        return Collections.emptyList();
    }

    @Override
    public List<Dynamics> queryByUsername(String username) {
        List<Dynamics> dynamics = dynamicsDao.queryByUsername(username);
        if (dynamics!=null){
            return dynamics;
        }
        return Collections.emptyList();
    }

    @Override
    public void insertDynamics(Dynamics dynamics) {
        dynamicsDao.insertDynamics(dynamics);
    }
}
