package com.jixiecompitionregistration.dao;

import com.jixiecompitionregistration.Util.DbUtils;

import java.sql.*;

import static com.jixiecompitionregistration.Util.DbUtils.sqlEscape;

//
public class DbAdd {
    public boolean addUser(String username ,String userid ,String password ,String academy){
        Connection connection = new DbUtils().getConn();

        username = sqlEscape(username);
        userid = sqlEscape(userid);
        password = sqlEscape(password);

        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "INSERT INTO TeamInfo (Username,UserId,Password,Academy) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1 ,username);
            preparedStatement.setString(2 ,userid);
            preparedStatement.setString(3 ,password);
            preparedStatement.setString(4 ,academy);

            int effectRow = preparedStatement.executeUpdate();
            connection.close();
            if (this.checkUserExist(username ,userid ,password ,academy)){
                return true;
            } else {
                return false;
            }

        } catch (SQLIntegrityConstraintViolationException e){
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
    private boolean checkUserExist(String username ,String userid ,String password ,String academy){
        Connection connection = new DbUtils().getConn();
        try {
            Statement statement = connection.createStatement();
            String sqlQuery = "select Username,Academy from TeamInfo where (UserId='" + userid + "' and Password='" + password +"')";
            statement.execute(sqlQuery);
            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next()){
                boolean b = username.equals(resultSet.getString("Username"))&& academy.equals(resultSet.getString("Academy"));
                connection.close();
                return b;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
