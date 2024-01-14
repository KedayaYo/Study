package com.tth.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tth.service.model.master.SysUser;
import com.tth.service.service.SysUserService;
import com.tth.service.mapper.master.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author entic
* @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
* @createDate 2024-01-14 19:15:55
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

    @Override
    public Integer insertBatch(List<SysUser> userList) {
        return baseMapper.insertBatch(userList);
    }

    @Override
    public Integer updateBatch(List<SysUser> userList) {
        return baseMapper.updateBatch(userList);
    }
}




