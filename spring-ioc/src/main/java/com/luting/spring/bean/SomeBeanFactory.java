package com.luting.spring.bean;

/**
 * Author:luting
 * Date:2018-09-27 13:56
 * Description:Bean工厂
 */
public class SomeBeanFactory {

    private static SomeBean getSomeBean(){
        return new SomeBean("witty",18);
    }

    public static String getStr(){
        return "this getStr";
    }

    private SomeBean getSomeBeans(){
        return new SomeBean("kitty",19);
    }
}
