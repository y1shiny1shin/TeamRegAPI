package com.jixiecompitionregistration.dao.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DbTeamCreate {
    private Connection connection = new DbUtils().getConn();

    public JSONObject createTeam(String teamName ,String captainName ,String captainId){
        String checkTeamPosSqlQuery = "select team_name ,team_name2 ,team_name3 from TeamInfo where Username=? and UserId=?";
        // 用数组来表示第几次参加队伍，代码简洁一点
        String[][] fields = {{"team_name","team_name2","team_name3"} ,{"is_captain","is_captain2","is_captain3"}};

        if (checkTeamNameRepeat(teamName)==-1){
            return Utils.resultJsonString("该队伍名已存在");
        } else if (checkTeamNameRepeat(teamName)==0) {
            return Utils.resultJsonString("服务器错误");
        }


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkTeamPosSqlQuery);
            preparedStatement.setString(1 ,captainName);
            preparedStatement.setString(2 ,captainId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
                // 利用三层三元表达式，判断出来是第几次加入队伍
                int TeamPos = (rs.getString("team_name")==null)?0:(rs.getString("team_name2")==null?1:(rs.getString("team_name3")==null?2:-1));
                if (TeamPos==-1){
                    return Utils.resultJsonString("该用户已加入了3个队伍，无法创建队伍");
                }
                String updateTeamSqlQuery = "update TeamInfo set "+ fields[0][TeamPos] +"=? ,"+ fields[1][TeamPos] +"=1 where (Username=? and Userid=?)";
                preparedStatement = connection.prepareStatement(updateTeamSqlQuery);
                preparedStatement.setString(1 ,teamName);
                preparedStatement.setString(2 ,captainName);
                preparedStatement.setString(3 ,captainId);

                System.out.println("createTeam: " + preparedStatement);
                int effectRow = preparedStatement.executeUpdate();

                if (effectRow==1){
                    return Utils.resultJsonString("创建成功");
                } else {
                    return Utils.resultJsonString("Sql语句执行失败");
                }

            } else {
                return Utils.resultJsonString("创建失败");
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


    /**
     * 判断队伍名是否重复
     */
    private int checkTeamNameRepeat(String teamName){
        String getAllTeamNameSqlQuery = "select group_concat(DISTINCT result_field order by result_field separator \", \") as result from (select team_name as result_field from TeamInfo UNION select team_name2 from TeamInfo UNION select team_name3 from TeamInfo)as combined_results";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getAllTeamNameSqlQuery);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
                List<String> teamNames = List.of(rs.getString("result").split(", "));
                if (teamNames.contains(teamName)){
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e){
            return 1;
        }
    }
}
