package com.ujs.boat.Controller;

import com.ujs.boat.Enity.User;
import com.ujs.boat.Service.UserService;
import com.ujs.boat.common.GlobalResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Api(tags = "用户操作")
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册", notes = "用户注册")
    public GlobalResult register(@RequestBody User user) {
        User ret_user=new User();
        ret_user=userService.selectById(user.getId());
        if (ret_user==null){
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time=dateFormat1.format(new Date());
            user.setCreateTime(time);
            userService.insert(user);
            GlobalResult result = GlobalResult.build(200, "用户创建成功", null);
            return result;
        }
        else{
            GlobalResult result = GlobalResult.build(500, "用户已存在", null);
            return result;
        }
    }

    @PostMapping("/user/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public GlobalResult login(String id,String password) {
        User ret_user=new User();
        ret_user=userService.selectById(id);
        if (ret_user==null){
            GlobalResult result = GlobalResult.build(500, "用户不存在", null);
            return result;
        }
        else{
            if (ret_user.getPassword().equals(password)) {
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=dateFormat1.format(new Date());
                userService.update_status("上线",id);
                userService.updateTime(id,time);
                GlobalResult result = GlobalResult.build(200, "用户登录成功", null);
                return result;
            }
            else{
                GlobalResult result = GlobalResult.build(500, "用户密码错误", null);
                return result;
            }
        }
    }
}
