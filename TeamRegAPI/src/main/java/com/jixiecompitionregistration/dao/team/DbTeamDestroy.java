package com.jixiecompitionregistration.dao.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbTeamDestroy {
    Connection connection = new DbUtils().getConn();
    public JSONObject teamDestroy(String teamName) {
        JSONObject resultJson = new JSONObject();
        String setTeamNameToNullSqlQuery =
                """
                UPDATE TeamInfo
                SET
                    is_captain = IF(team_name = ?, NULL, is_captain),
                    team_name = IF(team_name = ?, NULL, team_name),
                    is_captain2 = IF(team_name2 = ?, NULL, is_captain2),
                    team_name2 = IF(team_name2 = ?, NULL, team_name2),
                    is_captain3 = IF(team_name3 = ?, NULL, is_captain3),
                    team_name3 = IF(team_name3 = ?, NULL, team_name3)
                WHERE ? IN (team_name, team_name2, team_name3)""";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(setTeamNameToNullSqlQuery);
            for (int i = 1; i <= 7; i++){
                preparedStatement.setString(i ,teamName);
            }

            System.out.println("teamDestroy->setTeamNameToNullSqlQuery: " + preparedStatement);

            int effectRow = preparedStatement.executeUpdate();
            if (effectRow==0){
                return Utils.resultJsonString("该队伍不存在");
            }

            String deleteTeamGroupSqlQuery = "delete from TeamGroup where team_name=?";
            preparedStatement = connection.prepareStatement(deleteTeamGroupSqlQuery);
            preparedStatement.setString(1 ,teamName);

            System.out.println("teamDestroy->deleteTeamGroupSqlQuery: " + preparedStatement);

            preparedStatement.executeUpdate();
            return Utils.resultJsonString("删除成功");

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
