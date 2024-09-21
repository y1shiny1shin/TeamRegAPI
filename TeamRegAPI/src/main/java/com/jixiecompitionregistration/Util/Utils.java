package com.jixiecompitionregistration.Util;

import com.alibaba.fastjson.JSONObject;

public class Utils {
    public static JSONObject resultJsonString(String statusMsg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg",statusMsg);

        return jsonObject;
    }
}
