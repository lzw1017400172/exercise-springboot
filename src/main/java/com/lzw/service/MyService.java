package com.lzw.service;

import com.lzw.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyService {

    @Autowired
    private UserMapper userMapper;

    public String getMsg(){
        return "my name is lzw!!!";
    }

    public List<Long> getUserIds(Map<String,Object> param){
        return userMapper.selectId(param);
    }
}
