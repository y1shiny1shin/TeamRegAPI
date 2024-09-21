package com.jixiecompitionregistration.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/admin")
public class AdminUpdate {
    @RequestMapping(value = "/update" ,method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject adminUpdateDb(@RequestBody JSONObject jsonObject , HttpSession httpSession){
        // TODO 实现前端更新，传入fieldName和fieldValue和id



        return null;
    }

}
