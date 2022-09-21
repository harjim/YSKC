package com.yskc.ms;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.yskc.common.annotation.EnableOwnerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动
 * @author huronghua
 */
@EnableSwagger2
@EnableSwaggerBootstrapUI
@EnableOwnerException
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, KafkaAutoConfiguration.class})
public class MsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsApplication.class, args);
    }

}
