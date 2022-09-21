package com.xxl.job.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, KafkaAutoConfiguration.class})
public class XxlJobExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxlJobExecutorApplication.class, args);
    }

}