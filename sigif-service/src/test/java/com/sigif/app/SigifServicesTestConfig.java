package com.sigif.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class SigifServicesTestConfig extends SigifServicesConfig {

    // Surcharge de la datasource pour les TU
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        String password = environment.getProperty("jdbc.password");
        if (password != null) {
            dataSource.setPassword(password);
        }

        return dataSource;
    }

    @Bean
    public Connection referenceConnection() throws IllegalStateException, SQLException {
        Connection jdbcConnection = null;
        String password = environment.getProperty("jdbc.reference.password");
        if (password == null) {
            password = "";
        }
        jdbcConnection = DriverManager.getConnection(environment.getRequiredProperty("jdbc.reference.url"),
                environment.getRequiredProperty("jdbc.reference.username"), password);
        return jdbcConnection;
    }

}
