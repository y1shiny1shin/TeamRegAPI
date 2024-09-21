package com.jixiecompitionregistration.Util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbUtils {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private String dbDriver;
    public DbUtils(){
        Properties properties = new Properties();
        try {
            // 流式读取文件
            InputStream is = DbUtils.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(is);

            this.dbUrl = properties.getProperty("spring.datasource.url");
            this.dbUser = properties.getProperty("spring.datasource.username");
            this.dbPassword = properties.getProperty("spring.datasource.password");
            this.dbDriver = properties.getProperty("spring.datasource.driver");
        } catch (IOException e) {
            System.out.println("[-] 未发现配置文件");
        }
    }

    public static boolean isCaptainCheck(String teamName , String userId , String password){
        Connection connection = new DbUtils().getConn();
        String checkCaptainSqlQuery = "select team_name,is_captain,team_name2,is_captain2,team_name3,is_captain3 from TeamInfo where (UserId=? and Password=?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkCaptainSqlQuery);
            preparedStatement.setString(1 ,userId);
            preparedStatement.setString(2 ,password);

            System.out.println("teamGroupJoin->checkCaptainSqlQuery: "+ preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                String dbTeamName1 = rs.getString("team_name");
                String dbTeamName2 = rs.getString("team_name2");
                String dbTeamName3 = rs.getString("team_name3");
                String dbIsCaptain1 = rs.getString("is_captain");
                String dbIsCaptain2 = rs.getString("is_captain2");
                String dbIsCaptain3 = rs.getString("is_captain3");

                if (
                    (teamName.equals(dbTeamName1)&&"1".equals(dbIsCaptain1))
                        ||
                    (teamName.equals(dbTeamName2)&&"1".equals(dbIsCaptain2))
                        ||
                    (teamName.equals(dbTeamName3)&&"1".equals(dbIsCaptain3))
                ){
                    return true;
                }else {
                    return false;
                }

            }else {
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
    public static int isMemberCheck(String teamName ,String memberId ,String memberPassword){
        Connection connection = new DbUtils().getConn();
        String checkMemberSqlQuery = "select team_name,is_captain,team_name2,is_captain2,team_name3,is_captain3 from TeamInfo where (UserId=? and Password=?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkMemberSqlQuery);
            preparedStatement.setString(1,memberId);
            preparedStatement.setString(2,memberPassword);

            System.out.println("isMemberCheck->checkMemberSqlQuery: "+ preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                String dbTeamName1 = rs.getString("team_name");
                String dbTeamName2 = rs.getString("team_name2");
                String dbTeamName3 = rs.getString("team_name3");
                String dbIsCaptain1 = rs.getString("is_captain");
                String dbIsCaptain2 = rs.getString("is_captain2");
                String dbIsCaptain3 = rs.getString("is_captain3");

                if (
                    (teamName.equals(dbTeamName1)&&"0".equals(dbIsCaptain1))
                        ||
                    (teamName.equals(dbTeamName2)&&"0".equals(dbIsCaptain2))
                        ||
                    (teamName.equals(dbTeamName3)&&"0".equals(dbIsCaptain3))
                ){
                    // 为该队伍的队员
                    return 1;
                }else {
                    // 不为该队伍的队员
                    return -1;
                }


            } else {
                // 账号密码错误
                return 0;
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

    public Connection getConn() {

        try{
            Class.forName(this.dbDriver);
            Connection connection = DriverManager.getConnection(this.dbUrl,this.dbUser,this.dbPassword);
            System.out.println("[+] 数据库连接成功");
            return connection;
        }catch (ClassNotFoundException e) {
            System.out.println("[-] 数据库驱动错误");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("[-] 数据库连接失败");
            throw new RuntimeException(e);
        }
    }
    // 防止sql注入的静态方法
    public static String sqlEscape(String str){
        if (str == null) return null;
        return  str.trim().replaceAll("\\s", "").replace("\\", "\\\\\\\\")
                .replace("_", "\\_").replace("\'", "\\'")
                .replace("%", "\\%").replace("*", "\\*");
    }

    public static JSONObject resultSetToJson(ResultSet rs){
        JSONObject resultJson = new JSONObject();
        JSONArray resultJsonArray = new JSONArray();

        try {
            ResultSetMetaData metaData = rs.getMetaData();
            long columnCount = metaData.getColumnCount();

            while (rs.next()){
                JSONObject row = new JSONObject();
                for (int i = 1; i <= columnCount; i++){
                    String columnName = metaData.getColumnName(i);
                    String columnValue = rs.getString(i);

                    row.put(columnName ,columnValue);
                }
                resultJsonArray.add(row);
            }
            resultJson.put("data", resultJsonArray);

            return resultJson;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
