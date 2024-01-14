package com.tth.service.mapper.slave1;

import com.tth.service.config.mybatisplus.RootMapper;
import com.tth.service.model.slave1.XxlJobUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
* @author entic
* @description 针对表【xxl_job_user】的数据库操作Mapper
* @createDate 2024-01-14 20:05:34
* @Entity com.tth.service.model.slave1.XxlJobUser
*/
@Repository
public interface XxlJobUserMapper extends RootMapper<XxlJobUser> {
    @Override
    int insertBatch(Collection<XxlJobUser> batchList);

    @Override
    int updateBatch(Collection<XxlJobUser> batchList);
}




