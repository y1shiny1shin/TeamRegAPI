package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.team.DbTeamDestroy;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api/team")
public class TeamDestroy {
    /**
     * 删除队伍，仅限队长
     * POST JSON
     * userId: 队长学号
     * teamName: 队伍名
     * password: 队长密码
     */
    @RequestMapping(value = "/destroy" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject teamDestroy(@RequestBody JSONObject jsonObject , HttpSession httpSession) {

        String sessUserId = (String) httpSession.getAttribute("UserId");
        String sessPassword = (String) httpSession.getAttribute("Password");

        String jsonUserId = jsonObject.getString("userId");
        String teamName = jsonObject.getString("teamName");
        String jsonPassword = jsonObject.getString("password");

        // 简单的身份验证
        if (sessUserId == null || sessPassword == null){
            return Utils.resultJsonString("用户未登录");
        }
        if (!sessUserId.equals(jsonUserId)||!sessPassword.equals(jsonPassword)){
            return Utils.resultJsonString("请输入正确的学号和密码");
        }
        if (!DbUtils.isCaptainCheck(teamName ,jsonUserId ,jsonPassword)){
            return Utils.resultJsonString("该用户不是该队队长，无法删除该队或该队伍不存在");
        }

        return new DbTeamDestroy().teamDestroy(teamName);

    }
}
