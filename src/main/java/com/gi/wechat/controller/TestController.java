package com.gi.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.gi.wechat.util.WeChatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Set;

@Controller
public class TestController {
    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }
    @RequestMapping(value = "/person")
    public String person(String code, Model model){
        JSONObject json = WeChatUtil.getWebAccessToken(code);
        String webAccessToke = json.getString("access_token");
        String openId = json.getString("openid");
        JSONObject userInfo = WeChatUtil.getUserInfo(webAccessToke,openId);
        Set<Map.Entry<String,Object>> entries = userInfo.entrySet();
        for(Map.Entry<String,Object> entry:entries){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            model.addAttribute(entry.getKey(),entry.getValue());
        }
        return "person";
    }
}
