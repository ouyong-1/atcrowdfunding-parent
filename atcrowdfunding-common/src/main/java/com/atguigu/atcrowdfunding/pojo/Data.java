package com.atguigu.atcrowdfunding.pojo;

import java.util.List;

/**
 * @Author: ouyong
 * @Date: 2020/08/03 23:31
 */
public class Data {
    private List<Integer> ids;

    public Data() {
    }

    public Data(List<Integer> ids) {
        this.ids = ids;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "Data{" +
                "ids=" + ids +
                '}';
    }
}
