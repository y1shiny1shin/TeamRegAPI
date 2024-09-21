package com.jixiecompitionregistration.dao;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;


import java.sql.*;

import static com.jixiecompitionregistration.Util.DbUtils.sqlEscape;

public class DbSelect {
    public Integer isValidUser(String userid ,String password) {
        Connection connection = new DbUtils().getConn();

        Statement statement = null;
        try {
            userid = sqlEscape(userid);
            password = sqlEscape(password);

            statement = connection.createStatement();
            String sqlQuery = "select id from TeamInfo where (UserId='" + userid + "' and Password='" + password +"')";
            System.out.println(sqlQuery);
            statement.execute(sqlQuery);

            ResultSet rs = statement.getResultSet();

            Integer count = 0;
            while (rs.next()){
                count++;
            }
            connection.close();

            if (count==1){
                // 有效用户
                return 1;
            } else if (count==0) {
                // 无效用户
                return 0;
            } else {
                // 服务器错误
                return -1;
            }

        } catch (SQLException e) {
            return -1;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    // 获取用户的基本信息，方便前端处理，返回值是JSON
    public JSONObject getUser(String userid , String password){
        Connection connection = new DbUtils().getConn();
        try {
            userid = sqlEscape(userid);
            password = sqlEscape(password);

            Statement statement = connection.createStatement();
            String sqlQuery = "select Academy,Username from TeamInfo where (UserId='" + userid + "'and Password='" + password +"')";
            statement.execute(sqlQuery);

            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("academy",resultSet.getString("Academy"));
                jsonObject.put("username",resultSet.getString("Username"));

                connection.close();
                return jsonObject;
            } else {
                connection.close();

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg","-1");
                return jsonObject;
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
