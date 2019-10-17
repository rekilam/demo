package com.example.demo;

import com.example.demo.units.ConnectionUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoApplication {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        SpringApplication.run(DemoApplication.class, args);

    }

}
