/**
 * File: ApiConfig.java
 * ApiConfig.java: Configures the initial DataSource
 * @author Gregory Ojiem
 */
package com.example.backend.Config;

import org.ini4j.Ini;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Configuration
public class ApiConfig {

    /**
     * Gets credential from an ini file, then initializes the datasource
     * @return The DataSource we initialize
     */
    @Bean
    public DataSource getDataSource() {
        String username;
        String password;

        try{
            String path = "backend/src/main/java/com/example/backend/Config/dbInfo.ini";
            Ini ini = new Ini(new File(path));
            username = ini.get("header", "username");
            password = ini.get("header", "password");
        } catch (IOException e){
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Username:");
            username = input.next();
            System.out.println("Enter Password:");
            password = input.next();
            input.close();
        }

        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
        dataSourceBuilder.url("jdbc:postgresql://totoro-database.cmiz8kdduxur.us-east-1.rds.amazonaws.com:5432/");
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }
}
