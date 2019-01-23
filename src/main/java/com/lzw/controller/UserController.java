package com.lzw.controller;

import com.lzw.model.User;
import com.lzw.service.UserService;
import com.lzw.util.CommonResult;
import com.lzw.util.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.List;

@RequestMapping("/user")
@RestController
@Api(tags = "UserController", description = "user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation("修改")
    public Object update(@RequestBody User param){
        userService.update(param);
        return new CommonResult().success(null);
    }

    @DeleteMapping
    @ApiOperation("删除")
    public Object delete(@RequestBody User param){
        userService.delete(param.getId());
        return new CommonResult().success(null);
    }

    @PostMapping("/read/list")
    @ApiOperation("修改")
    public Object list(@RequestBody User param){
        List<User> list = userService.getUserByEntity(param);
        return new CommonResult().success(list);
    }
}
