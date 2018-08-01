package com.kingyee.fuxi.dic.bean;

/**
 * Created by ph on 2017/1/6.
 */
public class Dic {

    public static String TYPE_TOMCAT = "tomcat";
    public static String TYPE_JDK = "jdk";

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
