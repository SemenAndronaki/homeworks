package ru.addressbook.tests;

import org.testng.annotations.Test;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import java.sql.*;

public class DbConnectionTest {

    @Test
    public void dbConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/addressbook?user=root&password=&serverTimezone=UTC");
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select group_id, group_name, group_header, group_footer from group_list");
            Groups groups = new Groups();
            while (rs.next()) {
                groups.add(new GroupData().withGroupId(rs.getInt("group_id")).withGroupName(rs.getString("group_name"))
                        .withGroupHeader(rs.getString("group_header")).withGroupFooter(rs.getString("group_footer")));
            }
            rs.close();
            statement.close();
            conn.close();
            System.out.println(groups);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
