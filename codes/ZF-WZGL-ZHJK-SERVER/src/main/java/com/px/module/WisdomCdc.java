package com.px.module;

import com.px.module.common.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableWebMvc
@EnableSwagger2
//@ComponentScan
//@EnableAutoConfiguration  
@EnableConfigurationProperties({ApplicationProperties.class})
@EnableScheduling
public class WisdomCdc extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WisdomCdc.class);
    }
    public static void main(String[] args) {
        SpringApplication.run (WisdomCdc.class, args);
    }

}
