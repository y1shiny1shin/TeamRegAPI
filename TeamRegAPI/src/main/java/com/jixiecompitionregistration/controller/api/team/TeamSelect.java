package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.team.DbTeamSelect;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/team")
public class TeamSelect {
    @ResponseBody
    @RequestMapping(value = "/select")
    public JSONObject teamSelect(HttpSession httpSession){
        String sessUserId = (String) httpSession.getAttribute("UserId");
        String sessPassword = (String) httpSession.getAttribute("Password");

        if (sessUserId == null || sessPassword == null){
            return Utils.resultJsonString("用户未登陆未登录");
        }


        return new DbTeamSelect().teamSelect(sessUserId,sessPassword);
    }

}
