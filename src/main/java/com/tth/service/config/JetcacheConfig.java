package com.tth.service.config;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheManager;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.template.QuickConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Configuration
public class JetcacheConfig {

    @Autowired
    private CacheManager cacheManager;
    private Cache<Long, Object> userCache;

    @PostConstruct
    public void init(){
        QuickConfig qc = QuickConfig.newBuilder("Jetcache-skysys:")
                .expire(Duration.ofSeconds(3600))
                .cacheType(CacheType.BOTH)
                // 本地缓存更新后，将在所有的节点中删除缓存，以保持强一致性
                .syncLocal(false)
                .build();
        userCache = cacheManager.getOrCreateCache(qc);
    }

    @Bean
    public Cache<Long, Object> getUserCache(){
        return userCache;
    }
}
