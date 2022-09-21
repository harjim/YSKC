package com.yskc.ms.models.qualityscore;

import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2022/6/17 14:31
 * @Description: 修改记录完成情况model
 * @author: hsx
 */
public class CompletionEditModel implements Serializable {

    private List<Integer> ids;

    private Integer isFinal;

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(Integer isFinal) {
        this.isFinal = isFinal;
    }
}
