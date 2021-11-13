package com.study;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

@Slf4j
public class ConnectionManager {

    private final String userName;
    private final String password;
    private final String url;
    private Connection connection = null;

    public ConnectionManager(String userName, String password, String url) {
        this.userName = userName;
        this.password = password;
        this.url = url;
    }

    @SneakyThrows
    private void createConnection() {
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);
        connection = DriverManager.getConnection(url, connectionProps);
        log.debug("Connection established");
    }

    @SneakyThrows
    public Statement createStatement(){
        if (connection ==null){
            createConnection();
        }
        return connection.createStatement();
    }
}
