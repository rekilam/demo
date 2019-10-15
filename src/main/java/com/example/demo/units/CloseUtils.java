/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.units;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FB-PC006
 */
public class CloseUtils {

    public static void close(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(CloseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void close(PreparedStatement ps, Connection con) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                Logger.getLogger(CloseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        close(con);
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CloseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        close(ps, con);

    }
}
