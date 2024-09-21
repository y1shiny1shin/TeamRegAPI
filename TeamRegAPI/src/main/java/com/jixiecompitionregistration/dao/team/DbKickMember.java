package com.jixiecompitionregistration.dao.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbKickMember {
    Connection connection = new DbUtils().getConn();

    public JSONObject kickMember(String teamName , String memberName , String memberId){
        String kickMemberSqlQuery = """
                UPDATE TeamInfo
                SET
                    is_captain = IF(team_name = ?, NULL, is_captain),
                    team_name = IF(team_name = ?, NULL, team_name),
                    is_captain2 = IF(team_name2 = ?, NULL, is_captain2),
                    team_name2 = IF(team_name2 = ?, NULL, team_name2),
                    is_captain3 = IF(team_name3 = ?, NULL, is_captain3),
                    team_name3 = IF(team_name3 = ?, NULL, team_name3)
                WHERE (Username=? and UserId=?)""";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(kickMemberSqlQuery);
            for (int i = 1; i <= 6; i++){
                preparedStatement.setString(i ,teamName);
            }
            preparedStatement.setString(7 ,memberName);
            preparedStatement.setString(8 ,memberId);

            System.out.println("kickMember->kickMemberSqlQuery: " + preparedStatement);

            int effectRow = preparedStatement.executeUpdate();
            if (effectRow != 1){
                return Utils.resultJsonString("踢出失败");
            } else {
                return Utils.resultJsonString("踢出成功");
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
