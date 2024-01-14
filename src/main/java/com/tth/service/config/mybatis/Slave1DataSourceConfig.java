package com.tth.service.config.mybatis;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.tth.service.config.mybatisplus.CustomizedSqlInjector;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "com.tth.service.mapper.slave1", sqlSessionTemplateRef = "slave1SqlSessionTemplate")
public class Slave1DataSourceConfig {
    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;
    @Autowired
    private CustomizedSqlInjector customizedSqlInjector;

    // 将这个对象放入Spring容器中
    @Bean(name = "slave1DataSource")
    // 读取application.properties中的配置参数映射成为一个对象,prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slave1DataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * @param dataSource
     * @return
     * @throws Exception
     * @Qualifier表示查找Spring容器中名字为slave1DataSource的对象
     */
    @Bean(name = "slave1SqlSessionFactory")
    public SqlSessionFactory slave1SqlSessionFactory(@Qualifier("slave1DataSource") DataSource dataSource)
            throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/slave1/*.xml"));
        // 设置全局配置 自定义sql注入
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setSqlInjector(customizedSqlInjector);
        bean.setGlobalConfig(globalConfig);
        // 添加mybatisPlus插件
        bean.setPlugins(new Interceptor[]{mybatisPlusInterceptor});
        return bean.getObject();
    }

    @Bean(name = "slave1TransactionManager")
    public DataSourceTransactionManager slave1TransactionManager(@Qualifier("slave1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "slave1SqlSessionTemplate")
    public SqlSessionTemplate slave1SqlSessionTemplate(
            @Qualifier("slave1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}