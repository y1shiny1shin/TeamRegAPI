package com.jixiecompitionregistration.dao.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbTeamJoin {
    Connection connection = new DbUtils().getConn();
    /**
     * 逻辑为：
     * 1.先做一个身份验证，数据库中是否存在该用户
     * 2.如果存在，在判断是否已经加入了该队伍
     * 3.判断该队伍是否还有空位
     * 3.如果没有，则加入队伍
     */
    public JSONObject teamJoin(String teamName , String userId){
        // 验证用户是否存在
        if (!checkUserExist(userId)){
            return Utils.resultJsonString("该用户不存在");
        }

        // 验证用户是否已经加入了该队伍
        if (checkTeamExist(teamName ,userId)){
            return Utils.resultJsonString("该用户已经加入了该队伍");
        }

        // 判断队伍是否符合要求
        int teamMemberCount = checkTeamFull(teamName);
        if (teamMemberCount>=3){
            return Utils.resultJsonString("该队伍已经满员");
        } else if (teamMemberCount==0) {
            return Utils.resultJsonString("该队伍不存在");
        }

        // 加入队伍，不需要判断哪个位置插入，哪里空，插哪里
        String checkTeamPosSqlQuery = "select team_name ,team_name2 ,team_name3 from TeamInfo where UserId=?";
        String[][] fields = {{"team_name","team_name2","team_name3"} ,{"is_captain","is_captain2","is_captain3"}};
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkTeamPosSqlQuery);
            preparedStatement.setString(1 ,userId);

            System.out.println("teamJoin->checkTeamPosSqlQuery: "+ preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                // 方法和teamCreate中的方法相同，判断数据库中的哪个位置为空
                int TeamPos = (rs.getString("team_name")==null)?0:(rs.getString("team_name2")==null?1:(rs.getString("team_name3")==null?2:-1));
                if (TeamPos==-1){
                    return Utils.resultJsonString("此人已经加入了三个队伍");
                }
                String updateTeamSqlQuery = "update TeamInfo set "+ fields[0][TeamPos] +"=? ,"+ fields[1][TeamPos] +"=0 where Userid=?";
                preparedStatement = connection.prepareStatement(updateTeamSqlQuery);
                preparedStatement.setString(1 ,teamName);
                preparedStatement.setString(2 ,userId);

                System.out.println("teamJoin->updateTeamSqlQuery: " + preparedStatement);

                int effectRow = preparedStatement.executeUpdate();
                if (effectRow!=1){
                    return Utils.resultJsonString("数据库执行失败");
                }

                return Utils.resultJsonString("加入成功");
            } else {
                return Utils.resultJsonString("该用户不存在");
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

    // 验证用户存在
    private boolean checkUserExist(String userId){
        String sqlQuery = "select id from TeamInfo where UserId=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1 ,userId);

            System.out.println("teamJoin->checkUserExist: " + preparedStatement);

            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // 判断这个用户是否存在于目标队伍，存在=>true ,不存在=>false
    private boolean checkTeamExist(String teamName ,String userId){
        String sqlQuery = "select team_name,team_name2,team_name3 from TeamInfo where UserId=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1 ,userId);

            System.out.println("teamJoin->checkTeamExist: " + preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            String dbTeamName1 = rs.getString("team_name");
            String dbTeamName2 = rs.getString("team_name2");
            String dbTeamName3 = rs.getString("team_name3");

            return teamName.equals(dbTeamName1) || teamName.equals(dbTeamName2) || teamName.equals(dbTeamName3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 判断该队伍是否已经满员
    private int checkTeamFull(String teamName){
        String sqlQuery = "select count(*) from TeamInfo where (team_name=? or team_name2=? or team_name3=?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            for (int i = 1; i <= 3; i++){
                preparedStatement.setString(i ,teamName);
            }
            System.out.println("teamJoin->checkTeamFull: " + preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();

            return rs.getInt("count(*)");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
