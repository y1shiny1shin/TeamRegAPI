package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.team.DbQuitTeam;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/team")
public class QuitTeam {
    /**
     * 退出队伍，仅限队员使用
     * POST JSON
     * teamName: 队伍名称
     */
    @ResponseBody
    @RequestMapping(value = "/quitteam" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject quitTeam(@RequestBody JSONObject jsonObject , HttpSession httpSession) {

        String sessUserId = (String) httpSession.getAttribute("UserId");
        String sessPassword = (String) httpSession.getAttribute("Password");

        String teamName = (String) jsonObject.getString("teamName");

        // 简单的逻辑处理一下
        if (sessUserId == null || sessPassword == null){
            return Utils.resultJsonString("请先登陆后再操作");
        }
        if (teamName==null){
            return Utils.resultJsonString("不可退出空队伍");
        }
        int isMemberCheck = DbUtils.isMemberCheck(teamName ,sessUserId ,sessPassword);
        if (isMemberCheck!=1){
            return Utils.resultJsonString((isMemberCheck==-1?"用户不是该队伍的队员":"用户不存在"));
        }

        return new DbQuitTeam().quitTeam(teamName ,sessUserId ,sessPassword);
    }

}
