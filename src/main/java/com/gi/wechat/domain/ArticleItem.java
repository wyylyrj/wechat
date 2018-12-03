package com.gi.wechat.domain;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class ArticleItem {
    private String Title;
    private String Description;
    private String PicUrl;
    private String Url;
}
