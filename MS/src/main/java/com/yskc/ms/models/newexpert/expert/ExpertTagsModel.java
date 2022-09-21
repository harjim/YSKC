package com.yskc.ms.models.newexpert.expert;

import java.util.List;

/**
 * @program: ms
 * @description: 专家标签
 * @author: cyj
 * @create: 2022-07-29 09:32
 **/
public class ExpertTagsModel {
    private List<ExpertAuditModel> list;

    public List<ExpertAuditModel> getList() {
        return list;
    }

    public void setList(List<ExpertAuditModel> list) {
        this.list = list;
    }
}
