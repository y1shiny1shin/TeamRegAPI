package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;
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
public class JoinPmsjTrace {
    /**
     * 主逻辑:
     * 1.验证身份(通过POST JSON 传入的 username 和 userId 和 password)
     * 2.加入到PmsjStu表中
     */
    @ResponseBody
    @RequestMapping(value = "/joinpmsjtrace" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject joinPmsjTrace(@RequestBody JSONObject jsonObject , HttpSession httpSession){
        String sessUserId = (String) httpSession.getAttribute("UserId");
        String sessPassword = (String) httpSession.getAttribute("Password");

        String jsonUsername = jsonObject.getString("username");
        String jsonUserId = jsonObject.getString("userId");
        String jsonPassword = jsonObject.getString("password");

        // 一个简单的身份验证
        if (sessUserId == null || sessPassword == null){
            return Utils.resultJsonString("用户未登录");
        }
        if (!(sessUserId.equals(jsonUserId)&&sessPassword.equals(jsonPassword))){
            return Utils.resultJsonString("请输入正确的学号和密码");
        }
        if (checkUserIdExist(jsonUserId)){
            return Utils.resultJsonString("该用户已加入过赛道，请勿重复加入");
        }

        Connection connection = new DbUtils().getConn();
        String joinPmsjSqlQuery = "insert into PmsjStu (Username ,UserId) values (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(joinPmsjSqlQuery);
            preparedStatement.setString(1 ,jsonUsername);
            preparedStatement.setString(2 ,jsonUserId);

            System.out.println("joinPmsjTrace->joinPmsjSqlQuery: " + preparedStatement);

            int effectRow = preparedStatement.executeUpdate();
            if (effectRow==1){
                return Utils.resultJsonString("加入赛道成功");
            }else {
                return Utils.resultJsonString("加入赛道失败");
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

    private boolean checkUserIdExist(String userId){
        Connection connection = new DbUtils().getConn();
        String checkUserIdExistSqlQuery = "select id from PmsjStu where UserId=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(checkUserIdExistSqlQuery);
            preparedStatement.setString(1 ,userId);

            System.out.println("joinPmsjTrace->checkUserIdExistSqlQuery: " + preparedStatement);
            return preparedStatement.executeQuery().next();
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
