package com.ys.springboot_test.entity;

import java.io.Serializable;

public class City implements Serializable {

    private String name;

    private String remark;

    public City() {}
    public City(String name, String remark) {
        this.name = name;
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
