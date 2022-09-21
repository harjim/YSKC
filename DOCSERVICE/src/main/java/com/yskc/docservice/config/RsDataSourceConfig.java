package com.yskc.docservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
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
import java.sql.SQLException;

/**
 * 数据库配置
 *
 * @author huronghua
 */
@Configuration
@MapperScan(basePackages = {"com.yskc.docservice.dao.rs"}, sqlSessionFactoryRef = "rsSqlSessionFactory")
public class RsDataSourceConfig {
    static final String MAPPER_LOCATION = "classpath:mapper/rs/*.xml";
    @Value("${spring.datasource.rs.url}")
    private String dbUrl;
    @Value("${spring.datasource.rs.username}")
    private String username;
    @Value("${spring.datasource.rs.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    /**
     * 声明其为Bean实例
     *
     * @return
     */
    @Bean(name = "rsDataSource")
    @Primary
    public DataSource dataSource() throws SQLException {
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
    @Primary
    @Bean(name = "rsTransactionManager")
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("rsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * plusSqlSessionFactory
     *
     * @param dataSource
     * @param globalConfig
     * @return
     * @throws IOException
     */
    @Primary
    @Bean(name = "rsSqlSessionFactory")
    public MybatisSqlSessionFactoryBean plusSqlSessionFactory(@Qualifier("rsDataSource") DataSource dataSource,
                                                              @Qualifier("plusGlobalConfiguration") GlobalConfig globalConfig) throws IOException {
        return plusSqlSessionFactory(dataSource, globalConfig, MAPPER_LOCATION);
    }

    /**
     * plusGlobalConfiguration
     *
     * @return
     */
    @Bean(name = "plusGlobalConfiguration")
    public GlobalConfig plusGlobalConfiguration() {
        GlobalConfig globalConfig = new GlobalConfig();
        GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
        dbConfig.setIdType(IdType.AUTO);
        globalConfig.setDbConfig(dbConfig);
        // 启动不打印logo
        globalConfig.setBanner(false);
        return globalConfig;
    }


    /**
     * 创建sqlSessionFactory
     *
     * @param dataSource
     * @param globalConfig
     * @param mapperLocation
     * @return
     * @throws IOException
     */
    public static MybatisSqlSessionFactoryBean plusSqlSessionFactory(DataSource dataSource,
                                                                     GlobalConfig globalConfig, String mapperLocation) throws IOException {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocation));
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);
        MybatisConfiguration configuration = new MybatisConfiguration();
        // 关闭驼峰转换
        configuration.setMapUnderscoreToCamelCase(false);
        sqlSessionFactoryBean.setConfiguration(configuration);
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //mysql分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        sqlSessionFactoryBean.setPlugins(interceptor);
        return sqlSessionFactoryBean;
    }


}
