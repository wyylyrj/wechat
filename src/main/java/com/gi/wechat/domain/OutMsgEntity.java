package com.gi.wechat.domain;


import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class OutMsgEntity {
    private String ToUserName;    //用户OPENID
    private String FromUserName;  //测试号的微信号
    private long CreateTime;      //创建时间 （整型）
    private String MsgType;       //text/image/..

    private String Content;       //文本消息内容
    @XmlElementWrapper(name="Image")
    private String[] MediaId;	  //图片消息媒体id，可以调用多媒体文件下载接口拉取数据。

    private Integer ArticleCount; //图文个数
    //图文列表明细
    @XmlElementWrapper(name="Articles")
    private ArticleItem[] item;
}
