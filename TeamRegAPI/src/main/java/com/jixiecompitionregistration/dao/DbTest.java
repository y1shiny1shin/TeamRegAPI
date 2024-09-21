package com.jixiecompitionregistration.dao;

import com.jixiecompitionregistration.Util.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DbTest {
    public void test() throws SQLException {
        Connection connection = new DbUtils().getConn();
        Statement statement = connection.createStatement();
        Integer result = statement.executeUpdate("update TeamInfo SET Password='123456'");

        System.out.println(result);
    }
}
