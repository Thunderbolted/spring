package com.luting.spring.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * Author:luting
 * Date:2018-09-27 14:04
 * Description:测试类
 */
public class Test {

    public static void main(String[] args) {

        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        SomeBean someBean = (SomeBean) ac.getBean("someBean");
        System.out.println(someBean);

        File file = (File) ac.getBean("file");
        File f = new File("d:/settings.jar");
        System.out.println(file.exists());

        SomeBean bean = (SomeBean) ac.getBean("someBean01");
        System.out.println(bean);

        String someBean1 = (String) ac.getBean("someBean02");
        System.out.println(someBean1);

        SomeBean someBean2 = (SomeBean) ac.getBean("someBean03");
        System.out.println(someBean2);


    }
}
