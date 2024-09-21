package com.jixiecompitionregistration.controller.api.user;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.DbUpdate;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/user")
public class UpdatePwd {
    // 传入 userid password newpassword 修改密码
    @RequestMapping(value = "/updatepwd" ,method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject UpdatePwd(@RequestBody JSONObject jsonObject , HttpSession httpSession)
    {
        Boolean useridCheck = jsonObject.get("userid").equals(httpSession.getAttribute("UserId"));
        Boolean passwordCheck = jsonObject.get("password").equals(httpSession.getAttribute("Password"));

        System.out.println(httpSession.getAttribute("UserId")+" "+httpSession.getAttribute("Password"));
        if (useridCheck && passwordCheck){
            boolean resultBool = new DbUpdate().updateUser(
                    jsonObject.get("userid").toString(),
                    jsonObject.get("password").toString(),
                    jsonObject.get("newpassword").toString()
            );
            // 密码为空返回-1 ，
            String resultStr = resultBool ? "修改成功" : "新密码不能为空";
            return Utils.resultJsonString(resultStr);
        } else {
            // SESS验证不通过，
            return Utils.resultJsonString("登陆后修改密码");
        }

    }
}
