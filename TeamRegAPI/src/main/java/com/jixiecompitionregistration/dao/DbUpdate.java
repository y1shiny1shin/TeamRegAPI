package com.jixiecompitionregistration.dao;

import com.jixiecompitionregistration.Util.DbUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.jixiecompitionregistration.Util.DbUtils.sqlEscape;

public class DbUpdate {
    private Connection connection = new DbUtils().getConn();

    public boolean updateUser(String userid ,String oldPassword ,String newPassword){
        userid = sqlEscape(userid);
        oldPassword = sqlEscape(oldPassword);
        newPassword = sqlEscape(newPassword);

        try {

            Statement statement = connection.createStatement();
            String sqlQuery = "update TeamInfo set Password = '"+newPassword+"' where (UserId = '"+userid+"' and Password = '"+oldPassword+"')";
            statement.executeUpdate(sqlQuery);

            Boolean resultBool = checkUserExist(userid ,newPassword);

            connection.close();
            return resultBool;
        } catch (SQLException e) {
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private boolean checkUserExist(String userid ,String newPassword){
        try {
            Statement stmt = connection.createStatement();
            String sqlQuery = "select Password from TeamInfo where UserId = '"+userid+"'";
            stmt.execute(sqlQuery);

            ResultSet rs = stmt.getResultSet();
            if (rs.next()){
                return rs.getString("Password").equals(newPassword);
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

}
