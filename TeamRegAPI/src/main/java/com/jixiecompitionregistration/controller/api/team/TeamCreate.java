package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.team.DbTeamCreate;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
@RequestMapping("/api/team")
public class TeamCreate {
    /**
     * 创建时默认不选择赛道，传入三个参数
     * teamName: 不重复的字段
     * captainName: 队长姓名
     * captainId: 队长学号
     */
    @ResponseBody
    @RequestMapping(value = "/create" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject teamCreate(@RequestBody JSONObject jsonObject , HttpSession httpSession) {
        // 身份验证，验证用户有效
        String captainName = jsonObject.getString("captainName");
        String captainId = jsonObject.getString("captainId");
        String teamName = jsonObject.getString("teamName");

        String sessUserId = (String) httpSession.getAttribute("UserId");
        String sessPassword = (String) httpSession.getAttribute("Password");
        // 一段非常长的判断，就是验证用户是否有效，防止越权，任意一个条件成立，都未登录
        if (
            (sessUserId == null || sessPassword == null)
          ||(captainName == null || captainId == null || teamName == null)
          ||(!captainId.equals(sessUserId))
          ||(!checkIdAndUsername(captainId ,captainName ,sessPassword))){
            return Utils.resultJsonString("用户未登陆");
        }

        return new DbTeamCreate().createTeam(teamName ,captainName ,captainId);

    }

    private boolean checkIdAndUsername(String userId ,String username ,String password){
        // 通过sessionID中的字段，判断用户是不是有效的
        Connection connection = new DbUtils().getConn();
        String sqlQuery = "select id from TeamInfo where (UserId=? and Username=? and Password=?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1 ,userId);
            preparedStatement.setString(2 ,username);
            preparedStatement.setString(3 ,password);

            System.out.println("checkIdAndUsername: " + preparedStatement);
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
