package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentDataModel;
import com.yskc.ms.models.patent.QueryPatentDataModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/9/11 8:34
 * description:
 */
public interface PatentDataService  {

    PageModel<List<PatentDataModel>> getList(QueryPatentDataModel query, DataPermModel perm);
}
