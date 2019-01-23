package com.lzw.service;

import com.lzw.model.User;

import java.util.List;

public interface UserService {

    List<User> getUserByEntity(User user);
    /**
     * 根据用户名获取后台管理员
     */
    User getAdminByUsername(String userName);

    User update(User param);

    void delete(Long id);

    /**
     * 注册
     * @param param
     * @return
     */
    User register(User param);

}
