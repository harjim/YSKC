package com.yskc.docservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

/**
 * 数据库配置
 * @author huronghua
 */
@Configuration
@MapperScan(basePackages = {"com.yskc.docservice.dao.ms"}, sqlSessionFactoryRef = "msSqlSessionFactory")
public class MsDataSourceConfig {
    static final String MAPPER_LOCATION = "classpath:mapper/ms/**.xml";
    @Value("${spring.datasource.ms.url}")
    private String dbUrl;
    @Value("${spring.datasource.ms.username}")
    private String username;
    @Value("${spring.datasource.ms.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    /**
     * 声明其为Bean实例
     * @return
     */
    @Bean(name = "msDataSource")
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
     * @return
     */
    @Bean(name = "msTransactionManager")
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("msDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * plusSqlSessionFactory
     * @param dataSource
     * @param globalConfig
     * @return
     * @throws IOException
     */
    @Bean(name = "msSqlSessionFactory")
    public MybatisSqlSessionFactoryBean plusSqlSessionFactory(@Qualifier("msDataSource") DataSource dataSource,
                                                              @Qualifier("plusGlobalConfiguration") GlobalConfig globalConfig) throws IOException {
        return RsDataSourceConfig.plusSqlSessionFactory(dataSource,globalConfig,MAPPER_LOCATION);
    }
}
