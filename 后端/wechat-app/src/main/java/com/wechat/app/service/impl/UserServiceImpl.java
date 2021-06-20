package com.wechat.app.service.impl;

import com.wechat.app.dao.UserDao;
import com.wechat.app.entity.RespPageBean;
import com.wechat.app.entity.User;
import com.wechat.app.service.UserService;
import com.wechat.app.utils.UserUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-06-16 11:37:09
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    @Resource
    private UserDao userDao;

    /**
     * 根据用户名进行登录
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);
        if (user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    /**
     * 获取除了当前用户的所有user表的数据
     * @return
     */
    @Override
    public List<User> getUserFriends() {
        return userDao.getUserFriends(UserUtil.getCurrentUser().getUsername());
    }
    /**
     * 设置用户当前状态为在线
     * @param id 用户id
     */
    @Override
    public void setUserStateToOn(Integer id) {
        userDao.setUserStateToOn(id);
    }
    /**
     * 设置用户当前状态为离线
     * @param id 用户id
     */
    @Override
    public void setUserStateToLeave(Integer id) {
        userDao.setUserStateToLeave(id);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Integer id) {
        return this.userDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<User> queryAllByLimit(int offset, int limit) {
        return this.userDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public Integer insert(User user) {
        //对密码进行加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodePass = encoder.encode(user.getPassword());
        user.setPassword(encodePass);
        user.setUserStateId(2);
        user.setEnabled(true);
        user.setLocked(false);
        return  this.userDao.insert(user);
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public Integer update(User user) {
        return this.userDao.update(user);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.userDao.deleteById(id) > 0;
    }

    @Override
    public Integer checkUsername(String username) {
        return userDao.checkUsername(username);
    }

    @Override
    public Integer checkNickname(String nickname) {
        return userDao.checkNickname(nickname);
    }

    @Override
    public RespPageBean getAllUserByPage(Integer page, Integer size, String keyword, Integer isLocked) {
        if (page!=null&&size!=null){
            page=(page-1)*size;//起始下标
        }
        //获取用户数据
        List<User> userList=userDao.getAllUserByPage(page,size,keyword,isLocked);
        //获取用户数据的总数
        Long total=userDao.getTotal(keyword,isLocked);
        RespPageBean respPageBean = new RespPageBean();
        respPageBean.setData(userList);
        respPageBean.setTotal(total);
        return respPageBean;
    }

    @Override
    public Integer changeLockedStatus(Integer id, Boolean isLocked) {
        return userDao.changeLockedStatus(id,isLocked);
    }

  @Override
  public Integer deleteByIds(Integer[] ids) {
    return userDao.deleteByIds(ids);
  }

    @Override
    public void updateNicknameAndUserProfile(String nickname, String user_profile, String username) {
        userDao.updateNicknameAndUserProfile(nickname,user_profile,username);
    }

    @Override
    public List<User> queryByUsername(String username) {
        List<User> users = userDao.queryByUsername(username);
        if (users!=null){
            return users;
        }
        return Collections.emptyList();
    }

    @Override
    public void insertTemp(String from_id, String to_id) {
        List<String> list = userDao.queryFromTo(from_id, to_id);
        if (list.size()==0){
            userDao.insertTemp(from_id,to_id);
        }else return;
    }

    @Override
    public List<User> getAllRequest(String to_id) {
        List<User> allRequest = userDao.getAllRequest(to_id);
        if (allRequest!=null)return allRequest;
        else return Collections.emptyList();
    }

    @Override
    public void deleteAddUser(String from_id, String to_id) {
        userDao.deleteAddUser(from_id,to_id);
    }

    @Override
    public void addRelation(String user_id, String friend_id) {
        userDao.addRelation(user_id,friend_id);
    }

    @Override
    public List<User> getNewFriends(String username) {
        return userDao.getNewFriends(username);
    }


}
