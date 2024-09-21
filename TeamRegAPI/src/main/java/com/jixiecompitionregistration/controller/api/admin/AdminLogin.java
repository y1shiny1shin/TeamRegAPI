package com.jixiecompitionregistration.controller.api.admin;


import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.dao.admin.DbAdminSelect;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/admin")
public class AdminLogin {

    @RequestMapping(value = "/login" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject adminLogin(@RequestBody JSONObject jsonObject , HttpSession httpSession)
    {
        JSONObject resultJson = new JSONObject();

        String adminName = jsonObject.getString("adminName");
        String adminPassword = jsonObject.getString("adminPassword");

        if(adminName == null || adminPassword == null){
            resultJson.put("msg","请输入账号密码");
            return resultJson;
        }

        if (new DbAdminSelect().isAdmin(adminName ,adminPassword)){
            httpSession.setAttribute("adminName",adminName);
            httpSession.setAttribute("adminPassword",adminPassword);

            resultJson.put("msg","登陆成功");
            return resultJson;
        } else {
            resultJson.put("msg","账号密码错误");
            return resultJson;
        }

    }
}
