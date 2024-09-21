package com.jixiecompitionregistration.controller.api.team;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.DbUtils;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.team.DbKickMember;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/team")
public class KickMember {
    /**
     * 踢出队员，仅限队长使用
     * POST JSON传入三个参数
     * teamName: 目标队伍
     * memberName: 目标队员姓名
     * memberId: 目标队员学号
     */
    @ResponseBody
    @RequestMapping(value = "/kickmember",method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject kickMember(@RequestBody JSONObject jsonObject , HttpSession httpSession){

        String sessUserId = (String) httpSession.getAttribute("UserId");
        String sessPassword = (String) httpSession.getAttribute("Password");

        String teamName = jsonObject.getString("teamName");
        String memberName = jsonObject.getString("memberName");
        String memberId = jsonObject.getString("memberId");

        // 简单的身份验证
        if (sessUserId == null || sessPassword == null){
            return Utils.resultJsonString("用户未登录");
        }
        if (teamName == null || memberName == null || memberId == null){
            return Utils.resultJsonString("不可踢出空队员");
        }
        if (memberId.equals(sessUserId)){
            return Utils.resultJsonString("队长不可踢出队长，如有需求请请解散队伍");
        }
        if (!DbUtils.isCaptainCheck(teamName ,sessUserId ,sessPassword)){
            return Utils.resultJsonString("没有权限踢出队员");
        }


        // 踢出队员主逻辑
        return new DbKickMember().kickMember(teamName ,memberName ,memberId);

    }
}
