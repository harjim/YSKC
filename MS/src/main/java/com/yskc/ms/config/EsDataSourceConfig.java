package com.yskc.ms.config;

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
 * @DateTime: 2021/9/23 15:25
 * @Description:数据库配置
 * @author: hsx
 */
@Configuration
@MapperScan(basePackages = {"com.yskc.ms.dao.es"}, sqlSessionFactoryRef = "esSqlSessionFactory")
public class EsDataSourceConfig {
    private Logger logger = LoggerFactory.getLogger(EsDataSourceConfig.class);
    static final String MAPPER_LOCATION = "classpath:mapper/es/*.xml";
    @Value("${spring.datasource.es.url}")
    private String dbUrl;
    @Value("${spring.datasource.es.username}")
    private String username;
    @Value("${spring.datasource.es.password}")
    private String password;
    @Value("${spring.datasource.es.driver-class-name}")
    private String driverClassName;
    /**
     * 声明其为Bean实例
     * @return
     */
    @Bean(name = "esDataSource")
    @Primary
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        return datasource;
    }

    /**
     * masterTransactionManager
     * @return
     */
    @Bean(name = "esTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * plusSqlSessionFactory
     * @param dataSource
     * @param globalConfig
     * @return
     * @throws IOException
     */
    @Bean(name = "esSqlSessionFactory")
    public MybatisSqlSessionFactoryBean plusSqlSessionFactory(@Qualifier("esDataSource") DataSource dataSource,
                                                              @Qualifier("esPlusGlobalConfiguration") GlobalConfiguration globalConfig) throws IOException {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(EsDataSourceConfig.MAPPER_LOCATION));
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);
        Interceptor[] plugins = {paginationInterceptor()};
        sqlSessionFactoryBean.setPlugins(plugins);
        return sqlSessionFactoryBean;
    }

    /**
     * plusGlobalConfiguration
     * @return
     */
    @Bean(name = "esPlusGlobalConfiguration")
    public GlobalConfiguration plusGlobalConfiguration() {
        GlobalConfiguration configuration = new GlobalConfiguration();
        configuration.setIdType(IdType.AUTO.getKey());
        return  configuration;
    }

    /**
     * paginationInterceptor
     * @return
     */
    @Bean(name = "esPaginationInterceptor")
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }
}
