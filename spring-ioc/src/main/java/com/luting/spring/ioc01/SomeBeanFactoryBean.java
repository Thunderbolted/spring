package com.luting.spring.ioc01;

import org.springframework.beans.factory.FactoryBean;

/**
 * Author:luting
 * Date:2018-09-27 14:57
 * Description:FactoryBean工厂对象，是一个特殊的bean,必须实现一个FactoryBean接口
 * 通过接口中的方法创建对象
 */
public class SomeBeanFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return new SomeBean("witty",22);
    }

    @Override
    public Class<?> getObjectType() {
        return SomeBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
