package com.lzw.controller;

import com.lzw.model.User;
import com.lzw.service.UserService;
import com.lzw.util.CacheUtil;
import com.lzw.util.CommonResult;
import com.lzw.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "IndexController", description = "home")
public class IndexController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @GetMapping("/")
    @ApiOperation("首页")
    public Object index(){
        return "HELLO SPRING BOOT";
    }

    @GetMapping("/get_db_source")
    @ApiOperation("获取当前的数据源")
    public Object getDbSource(){
        return dataSource.toString();
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Object register(@RequestBody User param, BindingResult result) {
        param = userService.register(param);
        if (param == null) {
            new CommonResult().failed();
        }
        return new CommonResult().success(param);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public Object login(@RequestBody User param){
        if(StringUtils.isBlank(param.getUserName())){
            return new CommonResult().validateFailed("userName");
        }
        if(StringUtils.isBlank(param.getPassword())){
            return new CommonResult().validateFailed("password");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(param.getUserName());
        if(passwordEncoder.matches(param.getPassword(),userDetails.getPassword())){
            String token = jwtTokenUtil.generateToken(userDetails);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            return new CommonResult().success(tokenMap);
        } else {
            return new CommonResult().validateFailed("账号或者密码错误。");
        }
    }

}
