package com.yskc.rs.models;

import java.io.Serializable;

/**
 * @program: rs
 * @description:
 * @author: cyj
 * @create: 2022-01-26 14:38
 **/
public class IdSwitchModel implements Serializable {
    Integer id1;
    Integer id2;

    public Integer getId1() {
        return id1;
    }

    public void setId1(Integer id1) {
        this.id1 = id1;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }
}
