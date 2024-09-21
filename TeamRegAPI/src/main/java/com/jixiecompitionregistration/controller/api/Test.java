package com.jixiecompitionregistration.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.jixiecompitionregistration.dao.team.DbTeamSelect;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

@Controller
@RequestMapping("/api")
public class Test extends HttpServlet {

    @RequestMapping("/test")
    @ResponseBody
    public JSONObject  test(HttpServletRequest request , HttpSession httpSession) throws SQLException {

        return new DbTeamSelect().teamSelect("123","123");

    }

}
