package com.jixiecompitionregistration.dao.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DbTeamGroupJoin {
    Connection connection = new DbUtils().getConn();

    /**
     * 主逻辑:
     * 1.判断该队伍是不是已经参加了多个赛道
     * 2.判断该队伍是否已经参加了这个赛道
     * 3.添加到TeamGroup表
     */
    public JSONObject teamGroupJoin(String teamName , String groupType){
        JSONObject resultJson = new JSONObject();
        // 判断该队伍是不是已经参加了多个赛道
        if (checkGroupIsFull(teamName)){
            return Utils.resultJsonString("该队伍已经参加了三个赛道，到达参加赛道上限");
        }

        // 判断该队伍是否已经参加了这个赛道
        if (checkGroupIsExist(teamName ,groupType)){
            return Utils.resultJsonString("该队伍已经参加了该赛道，请勿重复参加赛道");
        }

        // 判断赛道类型是否正确
        if (checkGroupIsValid(groupType)){
            return Utils.resultJsonString("赛道类型不正确，请重新选择赛道类型");
        }

        // 主逻辑，加入赛道
        String checkGroupPosSqlQuery = "select group_type,group_type2,group_type3 from TeamGroup where team_name=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkGroupPosSqlQuery);
            preparedStatement.setString(1 ,teamName);

            System.out.println("teamGroupJoin->checkGroupPos: " + preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                String groupPos = (rs.getString("group_type")==null)?"group_type":(rs.getString("group_type2")==null?"group_type2":"group_type3");

                String insertGroupSqlQuery = "update TeamGroup set "+ groupPos +"=? where team_name=?";
                preparedStatement = connection.prepareStatement(insertGroupSqlQuery);
                preparedStatement.setString(1 ,groupType);
                preparedStatement.setString(2 ,teamName);

                System.out.println("teamGroupJoin->insertGroupSqlQuery: " + preparedStatement);

                int effectRow = preparedStatement.executeUpdate();
                if (effectRow==1){
                    return Utils.resultJsonString("加入赛道成功");
                }else {
                    return Utils.resultJsonString("加入赛道失败");
                }
            }else {
                // 从零开始新建
                String insertNewGroupSqlQuery = "insert into TeamGroup (team_name ,group_type) values (? ,?)";
                preparedStatement = connection.prepareStatement(insertNewGroupSqlQuery);
                preparedStatement.setString(1 ,teamName);
                preparedStatement.setString(2 ,groupType);

                System.out.println("teamGroupJoin->insertNewGroupSqlQuery: " + preparedStatement);

                int effectRow = preparedStatement.executeUpdate();
                if (effectRow==1){
                    return Utils.resultJsonString("加入赛道成功");
                }else {
                    return Utils.resultJsonString("加入赛道失败");
                }
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
    private boolean checkGroupIsFull(String teamName){
        String checkGroupIsFullSqlQuery = "select (if(group_type is not NULL,1,0)+if(group_type2 is not NULL,1,0)+if(group_type3 is not NULL,1,0)) as result from TeamGroup where team_name=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkGroupIsFullSqlQuery);
            preparedStatement.setString(1 ,teamName);

            System.out.println("teamGroupJoin->checkGroupIsFull: " + preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return rs.getInt("result")==3;
            }else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkGroupIsValid(String groupType){
        String[] raceTrack = {"计算机综合素质竞赛类","微课与课件设计类","程序应用设计类","数字媒体设计类","算法程序设计类"};
        // 判断赛道类型是否正确
        return Arrays.asList(raceTrack).contains(groupType);
    }

    private boolean checkGroupIsExist(String teamName ,String groupType){
        String checkGroupIsExistSqlQuery = "select group_type,group_type2,group_type3 from TeamGroup where team_name=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkGroupIsExistSqlQuery);
            preparedStatement.setString(1 ,teamName);

            System.out.println("teamGroupJoin->checkGroupIsExist: " + preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return groupType.equals(rs.getString("group_type"))||groupType.equals(rs.getString("group_type2"))||groupType.equals(rs.getString("group_type3"));
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
