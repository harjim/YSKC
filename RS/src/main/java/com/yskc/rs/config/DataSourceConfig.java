package com.yskc.rs.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.annotation.MapperScan;
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
 * 数据库配置
 *
 * @author huronghua
 */
@Configuration
@MapperScan(basePackages = {"com.yskc.rs.dao"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class DataSourceConfig {
    static final String MAPPER_LOCATION = "classpath:mapper/**/*.xml";
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * 声明其为Bean实例
     *
     * @return
     */
    @Bean(name = "dataSource")
    @Primary
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
    @Bean(name = "transactionManager")
    @Primary
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
    @Bean(name = "sqlSessionFactory")
    @Primary
    public MybatisSqlSessionFactoryBean plusSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource,
                                                              @Qualifier("plusGlobalConfiguration") GlobalConfiguration globalConfig) throws IOException {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(DataSourceConfig.MAPPER_LOCATION));
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
    @Bean(name = "plusGlobalConfiguration")
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
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}
