package com.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by simon on 12/10/19.
 */
public class DataSourceConfig {
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://172.16.238.11:3306/flowable?characterEncoding=UTF-8");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("123456");
        return dataSourceBuilder.build();
    }
}


/**
 *
 * spring.datasource.url=jdbc:mysql://172.16.238.11:3306/flowable?characterEncoding=UTF-8
 spring.datasource.username=root
 spring.datasource.password=123456
 spring.jpa.hibernate.ddl-auto=update


 * */