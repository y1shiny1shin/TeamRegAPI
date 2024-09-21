package com.jixiecompitionregistration.controller.api.user;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.DbSelect;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/api/user")
public class GetUserInfo {
    @RequestMapping("/getuserinfo")
    @ResponseBody
    public JSONObject getUserInfo(HttpServletResponse response ,HttpSession httpSession) throws IOException {

        String userId = (String) httpSession.getAttribute("UserId");
        String password = (String) httpSession.getAttribute("Password");

        if (userId == null){
            return Utils.resultJsonString("获取失败");
        }

        System.out.println(userId + " " + password);
        return new DbSelect().getUser(userId ,password);
    }
}
