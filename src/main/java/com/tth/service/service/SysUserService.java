package com.tth.service.service;

import com.tth.service.model.master.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author entic
* @description 针对表【sys_user(用户信息表)】的数据库操作Service
* @createDate 2024-01-14 19:15:55
*/
public interface SysUserService extends IService<SysUser> {

    Integer insertBatch(List<SysUser> userList);
    Integer updateBatch(List<SysUser> userList);
}
