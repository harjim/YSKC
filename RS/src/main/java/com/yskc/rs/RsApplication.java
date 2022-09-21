package com.yskc.rs;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.yskc.common.annotation.EnableOwnerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableSwaggerBootstrapUI
@EnableOwnerException
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, KafkaAutoConfiguration.class})
public class RsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RsApplication.class, args);
    }
}
