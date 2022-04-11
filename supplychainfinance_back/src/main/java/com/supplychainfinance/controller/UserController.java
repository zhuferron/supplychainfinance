package com.supplychainfinance.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.supplychainfinance.common.lang.Result;
import com.supplychainfinance.pojo.LoginUser;
import com.supplychainfinance.service.LoginUserService;
import com.supplychainfinance.common.lang.RandomID;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    //Restful
    @Autowired
    LoginUserService loginUserService;


    //用requestBody
    @ApiOperation("用户登录请求")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");

        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        wrapper.eq("password",password);
        LoginUser user = loginUserService.getOne(wrapper);

        if(user != null){
            return JSON.toJSONString(Result.succ("登录成功",user));
        }else{
            return JSON.toJSONString(Result.fail("登录失败"));
        }
    }

    @ApiOperation("用户注册请求")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(@RequestBody LoginUser loginUser){

        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",loginUser.getUsername());
        LoginUser user = loginUserService.getOne(wrapper);
        if(user != null ){
            return JSON.toJSONString(Result.fail("用户已注册"));
        }else{
            LoginUser new_user = new LoginUser();
            new_user.setId(RandomID.getUUID());
            new_user.setUsername(loginUser.getUsername());
            new_user.setPassword(loginUser.getPassword());
            new_user.setEmail(loginUser.getEmail());
            new_user.setRole(loginUser.getRole());

            //new_user.setId(RandomID.getUUID());
            //new_user.setUsername(map.get("username"));
            //new_user.setPassword(map.get("password"));
            //new_user.setEmail(map.get("email"));
            // new_user.setRole(Integer.parseInt(map.get("role")));

            boolean judge = loginUserService.save(new_user);
            if(judge){
                return JSON.toJSONString(Result.succ("用户注册成功"));
            }else{
                return JSON.toJSONString(Result.fail("用户注册失败"));
            }
        }
    }

    @ApiOperation("用户查询请求")
    @RequestMapping(value = "/getInfo/{id}",method = RequestMethod.GET)
    public String getInfo(@PathVariable(name = "id") String id){

        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        LoginUser user = loginUserService.getOne(wrapper);
        if(user != null){
            return JSON.toJSONString(Result.succ("查询成功",user));
        }else{
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("用户修改请求")
    @RequestMapping(value = "/updateInfo",method = RequestMethod.PUT)
    public String updateInfo(@RequestBody LoginUser loginUser){

        QueryWrapper<LoginUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email",loginUser.getEmail());
        wrapper.eq("username",loginUser.getUsername());
        wrapper.eq("password",loginUser.getPassword());
        wrapper.eq("info",loginUser.getInfo());

        LoginUser user = loginUserService.getOne(wrapper);

        if(user != null){
            return JSON.toJSONString(Result.fail("用户信息未修改"));
        }else{
            LoginUser update_user = loginUserService.getById(loginUser.getId());
            update_user.setUsername(loginUser.getUsername());
            update_user.setPassword(loginUser.getPassword());
            update_user.setEmail(loginUser.getEmail());
            update_user.setInfo(loginUser.getInfo());
            boolean judge = loginUserService.updateById(update_user);
            if(judge){
                return JSON.toJSONString(Result.succ("用户信息修改成功"));
            }
            else{
                return JSON.toJSONString(Result.fail("用户信息修改失败"));
            }
        }
    }

    @ApiOperation("上传资料")
    @RequestMapping(value = "/uploadVerifyInfo", method = RequestMethod.POST)
    public String uploadVerifyInfo(@RequestBody Map<String,Object> map){
        String id = (String) map.get("id");
        String info = (String) map.get("info");

        QueryWrapper<LoginUser> wrapper = new QueryWrapper<LoginUser>();
        wrapper.eq("id",id);
        LoginUser loginUser = loginUserService.getOne(wrapper);
        loginUser.setVerify("认证中");
        loginUser.setInfo(info);
        boolean judge = loginUserService.saveOrUpdate(loginUser);
        if(judge){
            return JSON.toJSONString(Result.succ("认证资料上传成功"));
        }else {
            return  JSON.toJSONString(Result.fail("认证资料上传失败"));
        }
    }


    @ApiOperation("查看特定机构的审核资料")
    @RequestMapping(value = "/verifyInfo/{id}",method = RequestMethod.GET)
    public String verifyInfo(@PathVariable(name = "id") String id){
        LoginUser loginUser  = loginUserService.getById(id);
        if(loginUser != null){
            return  JSON.toJSONString(Result.succ("查询成功",loginUser));
        }else {
            return  JSON.toJSONString(Result.fail("查询失败"));
        }

    }

    @ApiOperation("查看所有机构的审核资料")
    @RequestMapping(value = "/verifyInfoAll" ,method = RequestMethod.GET)
    public String verifyInfoAll(){

        QueryWrapper<LoginUser> wrapper = new QueryWrapper<LoginUser>();
        List<LoginUser> loginUsersAll = loginUserService.list(new QueryWrapper<LoginUser>());
        if(loginUsersAll != null){
            return  JSON.toJSONString(Result.succ("查询成功",loginUsersAll));
        }else {
            return JSON.toJSONString(Result.fail("查询失败"));
        }
    }

    @ApiOperation("审核通过")
    @RequestMapping(value = "/passVerify", method = RequestMethod.POST)
    public String passVerify(@RequestBody Map<String,Object> map){
        String id = (String) map.get("id");

        QueryWrapper<LoginUser> wrapper = new QueryWrapper<LoginUser>();
        wrapper.eq("id", id);
        LoginUser loginUser = loginUserService.getOne(wrapper);
        loginUser.setVerify("已通过");
        boolean judge = loginUserService.saveOrUpdate(loginUser);
        if(judge) {
            return JSON.toJSONString(Result.succ("操作成功"));
        }else {
            return JSON.toJSONString(Result.fail("操作失败"));
        }
    }

    @ApiOperation("审核不通过")
    @RequestMapping(value = "/unPassVerify", method = RequestMethod.POST)
    public String unPassVerify(@RequestBody Map<String,Object> map){
        String id = (String) map.get("id");

        QueryWrapper<LoginUser> wrapper = new QueryWrapper<LoginUser>();
        wrapper.eq("id", id);
        LoginUser loginUser = loginUserService.getOne(wrapper);
        loginUser.setVerify("未通过");
        boolean judge = loginUserService.saveOrUpdate(loginUser);
        if(judge) {
            return JSON.toJSONString(Result.succ("操作成功"));
        }else {
            return JSON.toJSONString(Result.fail("操作失败"));
        }
    }


}
