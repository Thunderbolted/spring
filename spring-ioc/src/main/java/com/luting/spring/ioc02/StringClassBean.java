package com.luting.spring.ioc02;

/**
 * Author:luting
 * Date:2018-09-28 9:40
 * Description:String/Class Bean
 */
public class StringClassBean {

    private String str;

    private Class dateClass;

    public StringClassBean() {
    }

    public StringClassBean(String str, Class dateClass) {
        this.str = str;
        this.dateClass = dateClass;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Class getDateClass() {
        return dateClass;
    }

    public void setDateClass(Class dateClass) {
        this.dateClass = dateClass;
    }

    @Override
    public String toString() {
        return "StringClassBean{" +
                "str='" + str + '\'' +
                ", dateClass=" + dateClass +
                '}';
    }
}
