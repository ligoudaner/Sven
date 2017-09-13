package com.ourslook.production;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.ourslook.production.activiti.modeler.JsonpCallbackFilter;

/**
 * 
    * @ClassName: TomcatApplication
    * @Description: tomcat启动添加类
    * @author Sven
    * @date 2017年9月8日
    *
 */
@SpringBootApplication
@ComponentScan({"org.activiti.rest.diagram", "com.ourslook.production"})
@EnableAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
		org.activiti.spring.boot.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})
public class TomcatApplication extends SpringBootServletInitializer{

	@Bean
	public JsonpCallbackFilter filter(){
		return new JsonpCallbackFilter();
	}
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TomcatApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TomcatApplication.class, args);
    }
}
