package com.tth.service.controller;

import com.alicp.jetcache.Cache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.tth.service.config.JetcacheConfig;
import com.tth.service.model.master.SysUser;
import com.tth.service.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    JetcacheConfig jetcacheConfig;
    @Autowired
    private Cache<String, Object> jetCache;
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("get")
    public SysUser get(@RequestParam("userId") Long userId) {
        String key = "USER_" + userId;
        if (jetCache.get(key) != null) {
            if (jetCache.get(key) instanceof SysUser) {
                return (SysUser) jetCache.get(key);
            } else {
                return null;
            }
        }
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getDelFlag, 0)
                .eq(SysUser::getUserId, userId);
        SysUser user = sysUserService.getOne(wrapper);
        // SysUser user = sysUserService.getById(userId);
        jetCache.put(key, user, 10, TimeUnit.MINUTES);
        System.out.println("第一次获取数据，未走缓存：" + key + "：" + user);
        return user;
    }

    @GetMapping("list")
    public List<SysUser> list() {
        if (jetCache.get("USER_LIST") != null) {
            return (List<SysUser>) jetCache.get("USER_LIST");
        }
        List<SysUser> userList = sysUserService.list();
        jetCache.put("USER_LIST", userList, 10, TimeUnit.MINUTES);
        System.out.println("第一次获取数据，未走缓存：" + userList);
        return userList;
    }

    @PostMapping("updateSysUser")
    public Boolean updateSysUser(@RequestBody SysUser user) {
        // TODO 更新数据库
        // List<SysUser> userList = (List<SysUser>) jetCache.get("USER_LIST");
        Long userId = user.getUserId();
        String key = "USER_" + userId;
        if (jetCache.get(key) != null) {
            user = (SysUser) jetCache.get(key);
        } else {
            user = get(userId);
        }
        user.setUserName("Kedaya");
        List<SysUser> userList = new ArrayList<>();
        userList.add(user);
        Integer rows = sysUserService.updateBatch(userList);
        System.out.println("更新SysUser数据：" + rows);
        jetCache.put("USER_" + userId, user, 10, TimeUnit.MINUTES);
        return true;
    }

    @PostMapping("deleteSysUser")
    public Boolean deleteSysUser(Long userId) {
        // TODO 从数据库删除
        LambdaUpdateWrapper<SysUser> wrapper = new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getDelFlag, 0)
                .eq(SysUser::getUserId, userId)
                .set(SysUser::getDelFlag, 1);
        boolean isUpdate = sysUserService.update(wrapper);
        System.out.println("删除SysUser数据：" + (isUpdate ? "成功" : "失败"));
        jetCache.remove("USER_" + userId);
        return true;
    }

}
