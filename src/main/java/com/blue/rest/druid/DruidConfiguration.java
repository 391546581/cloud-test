package com.blue.rest.druid;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * @Auther: wangcs
 * @Date: 2019/7/2 11:02
 * @Description:
 */

@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DruidConfiguration {

    @Bean(destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties("spring.datasource")
    public DruidDataSource dataSource(DataSourceProperties properties) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(properties.determineDriverClassName());
        dataSource.setUrl(properties.determineUrl());
        dataSource.setUsername(properties.determineUsername());
        dataSource.setPassword(properties.determinePassword());
        DatabaseDriver databaseDriver = DatabaseDriver
                .fromJdbcUrl(properties.determineUrl());
        String validationQuery = databaseDriver.getValidationQuery();
        if (validationQuery != null) {
            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery(validationQuery);
        }
        try {
            //开启Druid的监控统计功能，mergeStat代替stat表示sql合并,wall表示防御SQL注入攻击
            dataSource.setFilters("mergeStat,wall,log4j");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataSource;
    }
}
