/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package includes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author kavindu
 */
public class MysqlConnection {

    public static Connection connection;

    public static void createConnection() throws Exception {
        if (connection == null) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/adyapana", "root", "root");
        }
    }

    public static ResultSet executeSearch(String query) throws Exception {
        createConnection();
        return connection.createStatement().executeQuery(query);
    }

    public static ResultSet executeIUD(String query) throws Exception {

        createConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Integer generatedId = null;
        // Prepare the statement with RETURN_GENERATED_KEYS
        pstmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        // Execute the update (for INSERT/UPDATE/DELETE)
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return pstmt.getGeneratedKeys();
    }
}
