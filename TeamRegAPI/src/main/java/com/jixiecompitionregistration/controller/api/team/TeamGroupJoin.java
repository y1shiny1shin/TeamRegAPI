package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.team.DbTeamGroupJoin;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/team")
public class TeamGroupJoin {
    /**
     * POST JSON传入三个参数:
     * captainId: 队长学号
     * teamName: 队伍名
     * groupType: 队伍赛道
     */
    @ResponseBody
    @RequestMapping(value = "/groupjoin",method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject teamGroupJoin(@RequestBody JSONObject jsonObject , HttpSession httpSession){
        // 一个简单的身份验证，只能由队长选择赛道
        String sessUserId = (String) httpSession.getAttribute("UserId");
        String sessPassword = (String) httpSession.getAttribute("Password");

        String jsonUserId = jsonObject.getString("captainId");
        String teamName = jsonObject.getString("teamName");
        String groupType = jsonObject.getString("groupType");

        if (jsonUserId == null || teamName == null || groupType == null){
            return Utils.resultJsonString("请输入正确的信息");
        }

        if (sessUserId == null || !sessUserId.equals(jsonUserId)){
            return Utils.resultJsonString("用户未登录");
        }
        if (!DbUtils.isCaptainCheck(teamName ,sessUserId ,sessPassword)){
            return Utils.resultJsonString("用户不是该队队长，只能由队长选择赛道");
        }

        return new DbTeamGroupJoin().teamGroupJoin(teamName ,groupType);
    }

}
