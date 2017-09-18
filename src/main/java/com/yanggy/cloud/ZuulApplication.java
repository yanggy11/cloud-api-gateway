package com.yanggy.cloud;

import com.yanggy.cloud.filter.CorsFilter;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Created by yangguiyun on 2017/9/18.
 */

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ZuulApplication {
    @Bean
    public FilterRegistrationBean corsFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CorsFilter());
        registration.addUrlPatterns("/*");
        registration.setName("corsFilter");
        registration.setOrder(1);
        return registration;
    }
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
