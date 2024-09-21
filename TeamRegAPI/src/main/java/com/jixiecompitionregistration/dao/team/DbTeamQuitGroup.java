package com.jixiecompitionregistration.dao.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbTeamQuitGroup {
    Connection connection = new DbUtils().getConn();
    public JSONObject teamQuitGroup(String groupName , String teamName) {
        String teamQuitGroupSqlQuery = """
                update TeamGroup
                SET
                	group_type = IF (group_type=?,NULL,group_type),
                	group_type2 = IF (group_type2=?,NULL,group_type2),
                	group_type3 = IF (group_type3=?,NULL,group_type3)
                WHERE team_name=?""";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(teamQuitGroupSqlQuery);
            for (int i = 1 ; i <= 3 ; i++){
                preparedStatement.setString(i ,groupName);
            }
            preparedStatement.setString(4 ,teamName);

            System.out.println("teamQuitGroup->teamQuitGroupSqlQuery: " + preparedStatement);

            int effectRow = preparedStatement.executeUpdate();
            if (effectRow == 1){
                return Utils.resultJsonString("退出成功");
            } else {
                return Utils.resultJsonString("退出失败");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
