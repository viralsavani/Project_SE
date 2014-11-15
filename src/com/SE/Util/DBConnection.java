/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SE.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Anand
 */
public class DBConnection {

    private final String driver = "org.sqlite.JDBC";
    private final String url = "jdbc:sqlite:musicplayer.sqlite";
    public Connection con = null;

    public Connection getCon() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.out.println("Error in Class.forName " + e);
        } catch (SQLException e) {
            System.out.println("Error in con " + e);
        }
        return con;
    }
}
