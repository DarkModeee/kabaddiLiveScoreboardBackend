package com.kabaddiLiveScoreBoard.KabaddiLiveScoreBoard.Service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;



@Service
public class DatabaseConnectionTestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void testDatabaseConnection() {
        try {
            // Run a simple query to check the connection
            String sql = "SELECT 1";
            Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
            if (result != null && result == 1) {
                System.out.println("Successfully connected to the database!");
            }
        } catch (Exception e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
    }
}
