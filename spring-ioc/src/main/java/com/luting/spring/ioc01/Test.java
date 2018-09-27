package com.luting.spring.ioc01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author:luting
 * Date:2018-09-27 15:14
 * Description:<描述>
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("ioc01/spring.xml");
        SomeBean someBean = (SomeBean) ac.getBean("someBean");
        SomeBean someBean1 = (SomeBean) ac.getBean("someBean");
        System.out.println(someBean == someBean1);
        System.out.println(someBean);

    }
}
