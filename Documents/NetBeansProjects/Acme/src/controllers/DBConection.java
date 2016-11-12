/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author RoseLandjlord
 */
public class DBConection {
    private Connection DBConection;
    public Connection conect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        
        
        }catch(ClassNotFoundException e)
        {
                   
        
        }
        String url="jdbc:mysql://localhost:3306/controllers";
        try{
            DBConection=(Connection) DriverManager.getConnection(url,"root","");
        }catch(SQLException e)
        {
        
        }
        return DBConection;
    }
    
}
