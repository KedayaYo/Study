package com.tth.service.mapper.master;

import com.tth.service.config.mybatisplus.RootMapper;
import com.tth.service.model.master.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
* @author entic
* @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
* @createDate 2024-01-14 19:15:55
* @Entity com.tth.service.model.master.SysUser
*/
@Repository
public interface SysUserMapper extends RootMapper<SysUser> {
    @Override
    int insertBatch(Collection<SysUser> batchList);

    @Override
    int updateBatch(Collection<SysUser> batchList);
}




