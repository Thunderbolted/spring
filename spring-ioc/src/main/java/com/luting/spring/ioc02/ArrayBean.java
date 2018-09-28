package com.luting.spring.ioc02;

import java.util.*;

/**
 * Author:luting
 * Date:2018-09-28 9:24
 * Description: 集合Bean
 */
public class ArrayBean {

    private String[] arr;

    private List<String> list;

    private Set<Integer> set;

    private Properties props;

    private Map<String,String> map;

    public ArrayBean() {
    }

    public String[] getArr() {
        return arr;
    }

    public void setArr(String[] arr) {
        this.arr = arr;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public Set<Integer> getSet() {
        return set;
    }

    public void setSet(Set<Integer> set) {
        this.set = set;
    }

    public Properties getProps() {
        return props;
    }

    public void setProps(Properties props) {
        this.props = props;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "ArrayBean{" +
                "arr=" + Arrays.toString(arr) +
                ", list=" + list +
                ", set=" + set +
                ", props=" + props +
                ", map=" + map +
                '}';
    }

    public ArrayBean(String[] arr, List<String> list, Set<Integer> set, Properties props, Map<String, String> map) {
        this.arr = arr;
        this.list = list;
        this.set = set;
        this.props = props;
        this.map = map;
    }
}
