package com.yanggy.cloud;

import com.yanggy.cloud.filter.ZuulRouteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Created by yangguiyun on 2017/9/18.
 */

@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class ZuulApplication {
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new org.springframework.web.filter.CorsFilter(source));
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public ZuulRouteFilter getZuulRouteFilter() {
        return new ZuulRouteFilter();
    }
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
