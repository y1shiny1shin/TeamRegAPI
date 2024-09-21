package com.jixiecompitionregistration.controller.api.user;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.DbAdd;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api/user")
public class Register {
    @RequestMapping(value = "/register",method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject TeamRegister(@RequestBody JSONObject jsonObject , HttpServletResponse response) {
        String userId = jsonObject.getString("userid");
        String password = jsonObject.getString("password");
        String username = jsonObject.getString("username");
        String academy = jsonObject.getString("academy");

//        List<String> academyList = List.of(new String[]{"文学院", "中华传统文化学院", "法学院", "哲学学院", "马克思主义学院", "纪检监察学院", "历史文化与旅游学院", "教育科学学院", "数学科学学院", "心理学院", "化学与材料科学学院", "影视与传媒学院", "国际中文教育学院", "外国语学院", "经济与管理学院", "物理与电子工程学院", "生命科学学院", "计算机科学学院", "地理与资源科学学院", "体育学院", "工学院", "商学院", "美术学院•书法学院", "音乐学院", "舞蹈学院", "服装与设计艺术学院", "遂宁校区（师范学院、产业学院、治理学院、文旅学院）"});
        String[] academyList = {"文学院", "中华传统文化学院", "法学院", "哲学学院", "马克思主义学院", "纪检监察学院", "历史文化与旅游学院", "教育科学学院", "数学科学学院", "心理学院", "化学与材料科学学院", "影视与传媒学院", "国际中文教育学院", "外国语学院", "经济与管理学院", "物理与电子工程学院", "生命科学学院", "计算机科学学院", "地理与资源科学学院", "体育学院", "工学院", "商学院", "美术学院•书法学院", "音乐学院", "舞蹈学院", "服装与设计艺术学院", "遂宁校区（师范学院、产业学院、治理学院、文旅学院）"};
        int count = 0;
        for (String s : academyList) {
            if (s.equals(academy)) {
                count++;
            }
        }
        if (count!=1){
            return Utils.resultJsonString("请选择正确的学院");
        }

        if (!(userId == null || password == null || username == null || academy == null)){
            boolean successOrNot = new DbAdd().addUser(username ,userId ,password ,academy);
            if (successOrNot){
                return Utils.resultJsonString("注册成功");
            } else {
                return Utils.resultJsonString("注册失败");
            }

        } else {
            return Utils.resultJsonString("不可输入空");
        }
    }

}
