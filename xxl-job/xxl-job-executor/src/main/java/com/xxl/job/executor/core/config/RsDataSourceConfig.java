package com.xxl.job.executor.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.core.config
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-13 18:31
 * @Description: rs数据源
 */
@Configuration
@MapperScan(basePackages = {"com.xxl.job.executor.dao.rs"}, sqlSessionFactoryRef = "rsSqlSessionFactory")
public class RsDataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(MsDataSourceConfig.class);
    static final String MAPPER_LOCATION = "classpath:mapper/rs/*.xml";
    @Value("${spring.datasource.rs.url}")
    private String dbUrl;
    @Value("${spring.datasource.rs.username}")
    private String username;
    @Value("${spring.datasource.rs.password}")
    private String password;
    @Value("${spring.datasource.rs.driver-class-name}")
    private String driverClassName;

    /**
     * 声明其为Bean实例
     *
     * @return
     */
    @Bean(name = "rsDataSource")
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        return datasource;
    }

    /**
     * masterTransactionManager
     *
     * @return
     */
    @Bean(name = "rsTransactionManager")
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * plusSqlSessionFactory
     *
     * @param dataSource
     * @param globalConfig
     * @return
     * @throws IOException
     */
    @Bean(name = "rsSqlSessionFactory")
    public MybatisSqlSessionFactoryBean plusSqlSessionFactory(@Qualifier("rsDataSource") DataSource dataSource,
                                                              @Qualifier("rsPlusGlobalConfiguration") GlobalConfiguration globalConfig) throws IOException {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);
        Interceptor[] plugins = {paginationInterceptor()};
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean;
    }

    /**
     * plusGlobalConfiguration
     *
     * @return
     */
    @Bean(name = "rsPlusGlobalConfiguration")
    public GlobalConfiguration plusGlobalConfiguration() {
        GlobalConfiguration configuration = new GlobalConfiguration();
        configuration.setIdType(IdType.AUTO.getKey());
        return configuration;
    }

    /**
     * paginationInterceptor
     *
     * @return
     */
    @Bean(name = "rsPaginationInterceptor")
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}
