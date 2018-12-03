package com.gi.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {
    public static final String TOKEN = "wyyl";
    public static final String APPID = "wx67e9875a0e20a3a1";
    public static final String APPSECRET = "d543a1ea3f878b198389c40778a190e7";
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String GET_WEB_ACCESSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";


    private static final String APPKEY = "1fec136dbd19f44743803f89bd55ca62";

    public static final String APP_ID = "14870692";
    public static final String API_KEY = "Iq8y9Itg7hDG2czFK4ATC0Ef";
    public static final String SECRET_KEY = "fDNRbbHjuBijTuffiF4CNKoRSWuFLIqO";



    public static String accessToken;
    public static Long expiresTime;

    public static String chat(String msg) {

        String result =null;
        String url ="http://op.juhe.cn/robot/index";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//您申请到的本接口专用的APPKEY
        params.put("info",msg);//要发送给机器人的内容，不要超过30个字符
        params.put("dtype","");//返回的数据的格式，json或xml，默认为json
        params.put("loc","");//地点，如北京中关村
        params.put("lon","");//经度，东经116.234632（小数点后保留6位），需要写为116234632
        params.put("lat","");//纬度，北纬40.234632（小数点后保留6位），需要写为40234632
        params.put("userid","");//1~32位，此userid针对您自己的每一个用户，用于上下文的关联

        try {
            result =HttpUtil.sendGet(url,"");
            JSONObject jsonObject = JSONObject.parseObject(result);
            int code = jsonObject.getInteger("error_code");
            if(code!=0) {
                return null;
            }
            String resp = jsonObject.getJSONObject("result").getString("text");
            System.out.println(result);
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static JSONObject getWebAccessToken(String code){
        String result = HttpUtil.sendGet(GET_WEB_ACCESSTOKEN_URL.replace("APPID",APPID).replace("SECRET",APPSECRET).replace("CODE",code),"");
        JSONObject json = JSONObject.parseObject(result);
        System.out.println(json);
        return json;
    }

    public static JSONObject getUserInfo(String webAccessToken,String openId){
        String result = HttpUtil.sendGet(GET_USERINFO_URL.replace("ACCESS_TOKEN",webAccessToken).replace("OPENID",openId),"");
        JSONObject json = JSONObject.parseObject(result);
        System.out.println(json);
        return json;
    }

    public static String getAccessToken(){
        if(accessToken == null || new Date().getTime()>expiresTime){
            String result = HttpUtil.sendGet(GET_ACCESSTOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET),"");
            JSONObject json = JSONObject.parseObject(result);
            accessToken = json.getString("access_token");
            Long expires_in = json.getLong("expires_in");
            expiresTime = new Date().getTime() + ((expires_in-60)*1000);
            System.out.println(result);
        }
        return accessToken;
    }

    public static void creatMenu(String menuJson){
        String result = HttpUtil.sendPost(CREATE_MENU_URL.replace("ACCESS_TOKEN",getAccessToken()),menuJson);
        System.out.println(result);
    }

    public static void main(String[] args) {
        creatMenu("{\n" +
                "     \"button\":[\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"开班信息\",\n" +
                "          \"key\":\"classinfo\"\n" +
                "      },\n" +
                "     {    \n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"校区地址\",\n" +
                "          \"key\":\"address\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"学科介绍\",\n" +
                "           \"sub_button\":[\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"Java课程\",\n" +
                "               \"url\":\"http://www.wolfcode.cn/zt/java/index.html\"\n" +
                "            },\n" +
                "           {    \n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"Python课程\",\n" +
                "               \"url\":\"http://www.wolfcode.cn/zt/python/index.html\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }\n" +
                "\n");
        sentTempl("      {\n" +
                "           \"touser\":\"oNG5B5s2HRjTh_cs3dCk2CqSMEvE\",\n" +
                "           \"template_id\":\"Ui0uEOxx9Wn_4PYy65eeHKi5nC6yiX06ItR_T7yQKnM\",\n" +
                "           \"url\":\"http://www.baidu.com\",      \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"company\":{\n" +
                "                       \"value\":\"巧克力\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"time\": {\n" +
                "                       \"value\":\"2014年9月22日\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"result\": {\n" +
                "                       \"value\":\"2014年9月22日\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }");
    }

    public static void sentTempl(String data){
        String result = HttpUtil.sendPost(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN",getAccessToken()),data);
        System.out.println(result);
    }

}
