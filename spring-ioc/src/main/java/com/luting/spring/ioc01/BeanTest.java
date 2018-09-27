package com.luting.spring.ioc01;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

/**
 * Author:luting
 * Date:2018-09-27 15:15
 * Description:<描述>
 */
public class BeanTest {

    /**
     * 初始化容器
     */
    ApplicationContext ac = null;

    public BeanTest() {
        ac = new ClassPathXmlApplicationContext("ioc01/spring.xml");
    }

    /**
     * 获取Calendar对象
     */
    private void getCalendar(){
        Calendar calendar = (Calendar) ac.getBean("calendar");
        System.out.println(calendar);
    }

    /**
     * 获取Date对象
     */
    private void getDate(){
        Date date = (Date) ac.getBean("date");
        System.out.println(date);
    }

    /**
     * 获取java_home值
     */
    private void getJavaHome(){
        String javaHome = (String) ac.getBean("java_home");
        System.out.println(javaHome);
    }

    /**
     * 获取sql查询结果
     */
    private void getSqlResult() throws SQLException {
        ResultSet resultSet = (ResultSet) ac.getBean("rs");
        while (resultSet.next()){
            String username = resultSet.getString("USERNAME");
            System.out.println(username);
        }
    }

    public static void main(String[] args) throws SQLException {
        BeanTest beanTest = new BeanTest();
        beanTest.getCalendar();
        beanTest.getDate();
        beanTest.getJavaHome();
        beanTest.getSqlResult();
    }

}
