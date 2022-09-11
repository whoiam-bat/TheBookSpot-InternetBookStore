package com.my.store;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConfig {
    private static JdbcConfig config;
    @Resource(name="store")
    private DataSource dataSource;
    private final Connection connection;

    private JdbcConfig() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Couldn't get connection");
            throw new RuntimeException(e);
        }
    }

    public static JdbcConfig getInstance() {
        if (config == null) {
            config = new JdbcConfig();
        }
        return config;
    }

    public Connection getConnection() {
        return connection;
    }
}
