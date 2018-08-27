package com.yanggy.cloud.config.security;

import com.yanggy.cloud.config.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Created by yangguiyun on 2017/9/26.
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 设置认证方式以及密码加密算法
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(this.userDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(passwordEncoder());
    }
    // 装载BCrypt密码编码器
    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                //设置jwt过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(
                        "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.ttf", "/**/*.woff2", "/**/*.css", "/**/*.js", "/api/notifications/**",
                        "/**/*.png", "/api/journal/**","/info","/trace","/health","/beans","/env","/metrics","/refresh","/auditevents","/jolokia/**","/heapdump","/threads",
                         "/api/applications/**","/features ","/archaius","/auditevents","/mappings","/resume","/configprops","/restart","/routes","/loggers","/api/notifications/**"
                ).permitAll()
                .antMatchers("/auth/**").permitAll().antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }
}
