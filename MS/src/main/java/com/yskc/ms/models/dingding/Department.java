package com.yskc.ms.models.dingding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 钉钉部门
 * @author huronghua
 */
public class Department {
    /**
     * 部门id
     */
    private Integer id;
    /**
     * 部门名称。长度限制为1~64个字符。不允许包含字符‘-’‘，’以及‘,’。
     */
    private String name;
    /**
     * 是    父部门id。根部门id为1
     */
    private String parentid;

    /**
     * 否    是否创建一个关联此部门的企业群，默认为false
     */
    private Boolean createDeptGroup;

    private String deptManagerUseridList;

    private List<String> managerUserIdList;

    public String getDeptManagerUseridList() {
        return deptManagerUseridList;
    }

    public void setDeptManagerUseridList(String deptManagerUseridList) {
        this.deptManagerUseridList = deptManagerUseridList;
    }

    public List<String> getManagerUserIdList() {
        if (StringUtils.isEmpty(deptManagerUseridList)) {
            return new ArrayList<>();
        } else {
            String[] deptManagerUserIds = deptManagerUseridList.split("\\|");
            return Arrays.asList(deptManagerUserIds);
        }
    }
    public void setManagerUserIdList(List<String> managerUserIdList) {
        this.managerUserIdList = managerUserIdList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Boolean getCreateDeptGroup() {
        return createDeptGroup;
    }

    public void setCreateDeptGroup(Boolean createDeptGroup) {
        this.createDeptGroup = createDeptGroup;
    }
}