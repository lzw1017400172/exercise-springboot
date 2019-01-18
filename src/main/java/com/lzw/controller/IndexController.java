package com.lzw.controller;

import com.lzw.model.User;
import com.lzw.service.MyService;
import com.lzw.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.HashMap;

@RestController
public class IndexController {

    @Autowired
    private MyService myService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @GetMapping("/")
    public Object index(){
        return "HELLO SPRING BOOT";
    }

    @GetMapping("/get_msg")
    public Object getMsg(){
        return myService.getMsg();
    }

    @GetMapping("/get_uid")
    public Object getUId(){
        return myService.getUserIds(new HashMap<>());
    }

    @GetMapping("/get_db_source")
    public Object getDbSource(){
        return dataSource.toString();
    }

    @GetMapping("/login")
    public Object login(HttpServletResponse response){
        User param = new User();
        param.setUserName("LZW");
        String token = jwtTokenUtil.generateToken(param);
        response.setHeader(this.tokenHeader,this.tokenHead+token);
        return "登录成功";
    }

}
