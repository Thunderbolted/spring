package com.luting.spring.helloworld;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author:luting
 * Date:2018-09-27 13:32
 * Description:测试类
 */
public class Test {

    public static void main(String[] args) {

        //创建spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        //通过id获取bean
        HelloWorld hw = (HelloWorld) ac.getBean("helloWorld");

        HelloWorld hw2 = (HelloWorld) ac.getBean("helloWorld");
        //spring默认为单利
        System.out.println(hw == hw2);

        hw.someMethod();


    }

}
