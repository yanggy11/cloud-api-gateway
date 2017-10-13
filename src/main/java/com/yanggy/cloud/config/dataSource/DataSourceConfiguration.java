package com.yanggy.cloud.config.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

/**
 * Created by yangguiyun on 2017/9/21.
 */

@Configuration
@MapperScan(DataSourceConfiguration.MAPPER_PACKAGE_PATH)
@EnableConfigurationProperties(DataSourceProperties.class)
@EnableTransactionManagement
public class DataSourceConfiguration {
    private final static String MAPPER_LOCATIONS = "sql-mapper/*.xml";
    private final static String ENTITY_PACKAGE_PATH = "com.yanggy.cloud.entity";
    protected final static String MAPPER_PACKAGE_PATH = "com.yanggy.cloud.mapper";
    @Autowired
    private  DataSourceProperties dataSourceProperties;
    private DruidDataSource datasource = null;

    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        datasource = new DruidDataSource();
        datasource.setUrl(dataSourceProperties.getUrl());
        datasource.setDbType(dataSourceProperties.getType());
        datasource.setDriverClassName(dataSourceProperties.getDriver());
        datasource.setUsername(dataSourceProperties.getUsername());
        datasource.setPassword(dataSourceProperties.getPassword());
        return datasource;
    }

    @PreDestroy
    public void close() {
        if(datasource != null){
            datasource.close();
        }
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_LOCATIONS));
        sqlSessionFactoryBean.setTypeAliasesPackage(ENTITY_PACKAGE_PATH);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());

        return jdbcTemplate;
    }
}
