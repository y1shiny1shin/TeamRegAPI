package com.jixiecompitionregistration.dao.admin;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbAdminSelect {
    private Connection connection = new DbUtils().getConn();

    public JSONObject getDataFromPage(int pageCount ,int pageSize){
        String sqlQuery = "select * from TeamInfo limit ?,?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            // 前端传入的是 第一页 的页码，所以减一
            preparedStatement.setInt(1 , pageCount-1);
            preparedStatement.setInt(2 , pageSize);

            ResultSet rs = preparedStatement.executeQuery();
            return DbUtils.resultSetToJson(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public JSONObject searchData(JSONObject jsonObject){
        String sqlQuery = "select * from TeamInfo where (Username like ? or UserId like ? or Password like ? or team_name like ? or is_captain like ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            for (int i=1;i<=5;i++){
                preparedStatement.setString(i , "%"+jsonObject.getString("search")+"%");
            }

            System.out.println("搜索代码sql: "+preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            return DbUtils.resultSetToJson(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean isAdmin(String adminName ,String adminPassword){
        String sqlQuery = "select count(*) result from Admin where (name=? and password=?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1 , adminName);
            preparedStatement.setString(2 , adminPassword);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                String result = rs.getString("result");
                return result.equals("1");
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
