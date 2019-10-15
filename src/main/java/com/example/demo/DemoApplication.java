package com.example.demo;

import com.example.demo.units.ConnectionUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        SpringApplication.run(DemoApplication.class, args);

        System.out.println("Get connection ... ");

        // get Connection connects to database
        Connection conn;
        try {
            conn = ConnectionUtils.getMyConnection();
            System.out.println("Get connection " + conn);
            System.out.println("Done!");
        } catch (SQLException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
