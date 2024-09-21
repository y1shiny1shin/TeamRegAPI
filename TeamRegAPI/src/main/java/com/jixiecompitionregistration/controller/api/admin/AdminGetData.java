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
public class AdminGetData {
    // 传入两个参数，页数 pageCount 和 每页显示的条数 pageSize
    @ResponseBody
    @RequestMapping(value = "/getdata" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    public JSONObject adminGetData(@RequestBody JSONObject jsonObject , HttpSession httpSession){
        JSONObject resultJson = new JSONObject();

        if (!SessionIdCheck.adminCheck(httpSession)){
            resultJson.put("msg","请登陆");
            return resultJson;
        }

        int pageCount = Integer.parseInt(jsonObject.getString("pageCount"));
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));

        return new DbAdminSelect().getDataFromPage(pageCount ,pageSize);



    }
}
