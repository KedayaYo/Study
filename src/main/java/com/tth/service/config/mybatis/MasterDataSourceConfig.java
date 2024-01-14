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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@EnableTransactionManagement(proxyTargetClass = true)
@Configuration
// 配置mybatis的接口类放的地方
@MapperScan(basePackages = "com.tth.service.mapper.master", sqlSessionTemplateRef = "masterSqlSessionTemplate")
public class MasterDataSourceConfig {
    @Autowired
    private MybatisPlusInterceptor mybatisPlusInterceptor;
    @Autowired
    private CustomizedSqlInjector customizedSqlInjector;

    // 将这个对象放入Spring容器中
    @Bean(name = "masterDataSource")
    // 读取application.properties中的配置参数映射成为一个对象,prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.master")
    // 表示这个数据源是默认数据源
    @Primary
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * @param dataSource
     * @return
     * @throws Exception
     * @Qualifier表示查找Spring容器中名字为masterDataSource的对象
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource dataSource)
            throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/master/*.xml"));
        // 设置全局配置 自定义sql注入
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setSqlInjector(customizedSqlInjector);
        bean.setGlobalConfig(globalConfig);
        // 添加mybatisPlus插件
        bean.setPlugins(new Interceptor[]{mybatisPlusInterceptor});
        return bean.getObject();
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(
            @Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}