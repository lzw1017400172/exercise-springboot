package com.lzw.controller;

import com.lzw.model.User;
import com.lzw.service.MyService;
import com.lzw.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.HashMap;

@RestController
@Api(tags = "IndexController", description = "home")
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
    @ApiOperation("首页")
    public Object index(){
        return "HELLO SPRING BOOT";
    }

    @GetMapping("/get_msg")
    @ApiOperation("获取消息")
    public Object getMsg(){
        return myService.getMsg();
    }

    @GetMapping("/get_uid")
    @ApiOperation("获取uid")
    public Object getUId(){
        return myService.getUserIds(new HashMap<>());
    }

    @GetMapping("/get_db_source")
    @ApiOperation("获取当前的数据源")
    public Object getDbSource(){
        return dataSource.toString();
    }

    @GetMapping("/login")
    @ApiOperation("登录")
    public Object login(HttpServletResponse response){
        User param = new User();
        param.setUserName("LZW");
        String token = jwtTokenUtil.generateToken(param);
        response.setHeader(this.tokenHeader,this.tokenHead+token);
        return "登录成功";
    }

}
