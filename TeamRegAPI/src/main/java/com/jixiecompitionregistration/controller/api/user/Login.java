package com.jixiecompitionregistration.controller.api.user;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.Util.Utils;
import com.jixiecompitionregistration.dao.DbSelect;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Controller
@RequestMapping("/api/user")
public class Login {
    private final HttpSession httpSession;

    public Login(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST ,produces = "application/json;charset=UTF-8")
    @ResponseBody
    // 通过访问api/login 接口，传入 userid 和 password 参数，再连接数据库，验证账号密码
    public JSONObject TeamLogin(@RequestBody JSONObject jsonObject ) throws IOException {
        String userid = (String) jsonObject.get("userid");
        String password = (String) jsonObject.get("password");

        Integer checkResult = new DbSelect().isValidUser(userid,password);
        if (checkResult == 1){
            String JSESSIONID = httpSession.getId();
            httpSession.setAttribute("UserId",userid);
            httpSession.setAttribute("Password",password);

            return Utils.resultJsonString("登陆成功");
        }else if (checkResult == 0){
            return Utils.resultJsonString("登陆失败");
        } else {
            return Utils.resultJsonString("服务器错误");
        }
    }

}
