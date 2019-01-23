package com.lzw.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.lzw.mapper.UserMapper;
import com.lzw.model.User;
import com.lzw.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getUserByEntity(User user) {
        Wrapper<User> wrapper = new EntityWrapper<>(user);
        return userMapper.selectList(wrapper);
    }

    @Override
    public User getAdminByUsername(String userName) {
        User user = new User();
        user.setUserName(userName);
        List<User> users = getUserByEntity(user);
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public User update(User param) {
        if(param.getId() == null){
            userMapper.insert(param);
        } else {
            userMapper.updateById(param);
        }
        return param;
    }

    @Override
    public void delete(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public User register(User param) {
        User umsAdmin = new User();
        BeanUtils.copyProperties(param, umsAdmin);
        umsAdmin.setStatus(1);
        //查询是否有相同用户名的用户
        Wrapper<User> wrapper = new EntityWrapper<>(umsAdmin);
        int count = userMapper.selectCount(wrapper);
        if (count > 0) {
            return null;
        }
        //将密码进行加密操作
        String password = passwordEncoder.encode(umsAdmin.getPassword());
        umsAdmin.setPassword(password);
        userMapper.insert(umsAdmin);
        return umsAdmin;
    }
}
