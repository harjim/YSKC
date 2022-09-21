package com.yskc.ms.models.qualityscore;

import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2022/6/9 17:24
 * @Description:
 * @author: hsx
 */
public class SaveScoreTypeModel implements Serializable {

    private List<ScoreTypeModel> list;

    public List<ScoreTypeModel> getList() {
        return list;
    }

    public void setList(List<ScoreTypeModel> list) {
        this.list = list;
    }
}
