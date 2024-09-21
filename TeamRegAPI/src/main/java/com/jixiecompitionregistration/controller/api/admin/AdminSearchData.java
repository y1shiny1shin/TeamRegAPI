package com.jixiecompitionregistration.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.SessionIdCheck;
import com.jixiecompitionregistration.dao.admin.DbAdminSelect;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/admin")
public class AdminSearchData {
    // 传入搜索条件，返回搜索结果 Username,UserId,Password,team_name,is_captain
    @ResponseBody
    @RequestMapping(value = "/search" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject adminSearchData(@RequestBody JSONObject jsonObject , HttpSession httpSession)
    {
        JSONObject resultJson = new JSONObject();

        if (!SessionIdCheck.adminCheck(httpSession)){
            resultJson.put("msg","请登陆");
            return resultJson;
        }

        return new DbAdminSelect().searchData(jsonObject);
    }
}
