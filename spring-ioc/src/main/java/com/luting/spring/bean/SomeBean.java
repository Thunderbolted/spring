package com.luting.spring.bean;

/**
 * Author:luting
 * Date:2018-09-27 13:53
 * Description:Bean基础类
 */
public class SomeBean {

    private String name;

    private Integer id;

    public SomeBean(String name, Integer id) {

        this.name = name;
        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SomeBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
