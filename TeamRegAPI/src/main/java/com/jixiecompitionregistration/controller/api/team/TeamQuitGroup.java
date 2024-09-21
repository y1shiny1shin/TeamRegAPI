package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.team.DbTeamQuitGroup;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

@Controller
@RequestMapping("/api/team/")
public class TeamQuitGroup {
    /**
     * 退出赛道，仅允许队长退出
     * POST JSON 传入
     * groupName: 赛道名称
     * teamName: 队伍名称
     */
    @ResponseBody
    @RequestMapping(value = "/quitgroup" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject teamQuitGroup(@RequestBody JSONObject jsonObject , HttpSession httpSession) {

        String sessUserId = (String) httpSession.getAttribute("UserId");
        String sessPassword = (String) httpSession.getAttribute("Password");

        String groupName = jsonObject.getString("groupName");
        String teamName = jsonObject.getString("teamName");

        if (!DbUtils.isCaptainCheck(teamName , sessUserId ,sessPassword)){
            return Utils.resultJsonString("非队长不能退出赛道");
        }

        String[] raceTrack = {"计算机综合素质竞赛类","微课与课件设计类","程序应用设计类","数字媒体设计类","算法程序设计类"};
        // 判断赛道类型是否正确
        boolean isValid = Arrays.asList(raceTrack).contains(groupName);
        if (!isValid){
            return Utils.resultJsonString("赛道类型错误");
        }

        return new DbTeamQuitGroup().teamQuitGroup(groupName , teamName);

    }
}
