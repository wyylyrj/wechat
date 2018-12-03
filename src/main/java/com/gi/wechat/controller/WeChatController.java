package com.gi.wechat.controller;

import com.gi.wechat.domain.ArticleItem;
import com.gi.wechat.domain.InMsgEntity;
import com.gi.wechat.domain.OutMsgEntity;
import com.gi.wechat.util.SecurityUtil;
import com.gi.wechat.util.WeChatUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

//@Controller
@RestController
//@EnableAutoConfiguration
public class WeChatController {
    @RequestMapping(value="/wechat",method = RequestMethod.GET)
    @ResponseBody
    public String validate(String signature,String timestamp,String nonce,String echostr){
        //1）将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = {WeChatUtil.TOKEN,timestamp,nonce};
        Arrays.sort(arr);
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuilder sb = new StringBuilder();
        for (String temp : arr) {
            sb.append(temp);
        }
        String mySignature = SecurityUtil.getSha1(sb.toString());
        // 3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        if(mySignature.equals(signature)){
            System.out.println("接入成功！");
            return echostr;
        }
        System.out.println("接入失败");
        return null;
    }
    @RequestMapping(value="/wechat",produces = "application/xml",method = RequestMethod.POST)
    @ResponseBody
    public Object handleMessage(@RequestBody InMsgEntity inMsg){
        OutMsgEntity outMsg = new OutMsgEntity();
        outMsg.setToUserName(inMsg.getFromUserName());
        outMsg.setFromUserName(inMsg.getToUserName());
        outMsg.setCreateTime(new Date().getTime());
        String msgType=inMsg.getMsgType();
        if(msgType.equals("text")){
            outMsg.setMsgType("text");
            String inContent = inMsg.getContent();
            String outContent = null;
            if(inContent.contains("全称")){
                outContent = "深圳市宜美特科技有限公司\n深圳市指南信息技术有限公司\n湖北宜美特全息科技有限公司";
            }else if(inContent.contains("地址")){
                outContent = "深圳\n深圳\n湖北\n宜昌";
            }else {

                outContent = inContent;//WeChatUtil.chat(inContent);
            }
            outMsg.setContent(outContent);
        }else if(msgType.equals("image")){
            outMsg.setMsgType("image");
            outMsg.setMediaId(new String[]{inMsg.getMediaId()});
            //outMsg.setMediaId(new String[]{"ddddddddddddddddddddddd"});
        }else if(msgType.equals("event")){
            if(inMsg.getEvent().equals("subscribe")){
                //outMsg.setContent("欢迎关注！！/::)");
                outMsg.setMsgType("news");
                outMsg.setArticleCount(1);
                ArticleItem item = new ArticleItem();
                item.setTitle("宜美特官网");
                item.setDescription("湖北宜美特全息科技有限公司官方网站");
                item.setPicUrl("http://www.emeteq.com.cn/imageRepository/60e9f938-edf8-44f0-aaa1-cb45456ff6ae.jpg");
                item.setUrl("http://www.emeteq.com.cn/");
                outMsg.setItem(new ArticleItem[]{item});
            }else if(inMsg.getEvent().equals("CLICK")){
                String eventKey = inMsg.getEventKey();
                String outContent = null;
                if("classinfo".equals(eventKey)){
                    outContent ="123\n"+"456";
                }else if("address".equals(eventKey)){
                    outContent ="abc\n"+"def";
                }
                outMsg.setMsgType("text");
                outMsg.setContent(outContent);
            }
        }
        return outMsg;
    }
}
