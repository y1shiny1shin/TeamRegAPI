package com.jixiecompitionregistration.dao.team;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;

import java.sql.*;

public class DbTeamSelect {
    Connection connection = new DbUtils().getConn();
    public JSONObject teamSelect(String userId ,String password)
    {
        JSONObject resultJson = new JSONObject();
        String[][] teamPosArray = {
                {"team1","team2","team3"},
                {"team_name","team_name2","team_name3"},
                {"is_captain","is_captain2","is_captain3"},
                {"memberName1","memberName2","memberName3"},
                {"memberId1","memberId2","memberId3"},
                {"academy1","academy2","academy3"}
        };

        String teamNameSelectSqlQuery = "select team_name,is_captain,team_name2,is_captain2,team_name3,is_captain3 from TeamInfo where (UserId=? and Password=?)";
        String teamMemberSelectSqlQuery = "select Username,UserId,Academy from TeamInfo where (team_name=? or team_name2=? or team_name3=?)";
        String teamGroupSelectSqlQuery = "select * from TeamGroup where team_name=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(teamNameSelectSqlQuery);
            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,password);

            System.out.println("teamSelect->teamNameSelect: "+ preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                for (int i=0;i<3;i++){
                    JSONArray jsonArray = new JSONArray();
                    JSONObject jsonObject = new JSONObject();

                    // 查询队伍名称
                    String teamName = rs.getString(teamPosArray[1][i]);
                    jsonObject.put("teamName",teamName);
                    // 如果为空，返回空数据
                    if (teamName==null){
                        jsonArray.add(null);
                        resultJson.put(teamPosArray[0][i],jsonArray);
                        continue;
                    }

                    // 查询该队的成员信息
                    PreparedStatement memberSelectPsmt = connection.prepareStatement(teamMemberSelectSqlQuery);
                    for (int x = 1 ;x<=3 ;x++){
                        memberSelectPsmt.setString(x , teamName);
                    }
                    System.out.println("teamSelect->teamMemberSelect: "+ memberSelectPsmt);

                    // 获取到成员信息，存入jsonObject对象
                    int count = 0;
                    ResultSet memberSet = memberSelectPsmt.executeQuery();
                    while (memberSet.next()){
                        String memberNamePos = teamPosArray[3][count];
                        String memberIdPos = teamPosArray[4][count];
                        String memberAcademyPos = teamPosArray[5][count];

                        jsonObject.put(memberAcademyPos,memberSet.getString("Academy"));
                        jsonObject.put(memberNamePos , memberSet.getString("Username"));
                        jsonObject.put(memberIdPos , memberSet.getString("UserId"));

                        count++;
                    }
                    // 存入空的队友，方便前端处理
                    for (int x = 2-count ; x>=0 ; x--){
                        int pos = 2 - x;
                        String memberNamePos = teamPosArray[3][pos];
                        String memberIdPos = teamPosArray[4][pos];

                        jsonObject.put(memberNamePos , null);
                        jsonObject.put(memberIdPos , null);

                    }

                    // 获取队伍参与赛道详情，存入jsonObject对象
                    PreparedStatement teamGroupSelectPsmt = connection.prepareStatement(teamGroupSelectSqlQuery);
                    teamGroupSelectPsmt.setString(1 , teamName);

                    System.out.println("teamSelect->teamGroupSelect: "+ teamGroupSelectPsmt);

                    // 获取队伍参赛赛道信息，存入jsonObject对象
                    ResultSet groupSet = teamGroupSelectPsmt.executeQuery();
                    if (groupSet.next()){
                        jsonObject.put("groupType1",groupSet.getString("group_type"));
                        jsonObject.put("groupType2",groupSet.getString("group_type2"));
                        jsonObject.put("groupType3",groupSet.getString("group_type3"));
                    } else {
                        jsonObject.put("groupType1",null);
                        jsonObject.put("groupType2",null);
                        jsonObject.put("groupType3",null);
                    }



                    // 将jsonObject放入jsonArray
                    jsonArray.add(jsonObject);

                    resultJson.put(teamPosArray[0][i],jsonArray);

                }
                return resultJson;
            } else {
                return resultJson.fluentPut("msg","出现错误");
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
