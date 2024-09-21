package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.dao.team.DbTeamJoin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/team")
public class TeamJoin {
    /**
     * 不做身份验证，直接加入队伍，传入学号和队伍名即可
     * teamName: 队伍名
     * userId: 学号
     */
    @ResponseBody
    @RequestMapping(value = "/join",method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject teamJoin(@RequestBody JSONObject jsonObject){
        String teamName = jsonObject.getString("teamName");
        String userId = jsonObject.getString("userId");

        return new DbTeamJoin().teamJoin(teamName,userId);
    }

}
