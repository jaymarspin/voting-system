/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package votingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class connectionString {
    
    
    
     public Connection conn()throws SQLException{
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/voting","root","");
        return conn;
    }
}
