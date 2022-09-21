package com.yskc.docservice;

import com.yskc.common.annotation.EnableOwnerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author zdf
 */
@EnableOwnerException
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,FreeMarkerAutoConfiguration.class})
public class DocServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocServiceApplication.class, args);
    }

}
