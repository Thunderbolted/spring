package com.luting.spring.ioc02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Author:luting
 * Date:2018-09-28 10:11
 * Description:<描述>
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("ioc02/spring.xml");
        SimpleDataBean simpleDataBean = (SimpleDataBean) ac.getBean("simpleDataBean");
        System.out.println(simpleDataBean);

        StringClassBean stringClassBean = (StringClassBean) ac.getBean("stringClassBean");
        System.out.println(stringClassBean);

        ResourceBean resourceBean = (ResourceBean) ac.getBean("resourceBean");
        System.out.println(resourceBean.getFileResource());
        System.out.println(resourceBean.getPathResource());

        ArrayBean arrayBean = (ArrayBean) ac.getBean("arrayBean");
        System.out.println(arrayBean);
    }
}
