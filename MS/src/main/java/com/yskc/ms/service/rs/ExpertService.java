package com.yskc.ms.service.rs;

import java.util.Date;
import java.util.List;

import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.Expert;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.expert.ExpertModel;
import com.yskc.ms.models.expert.QueryExpertModel;


public interface ExpertService {
    /**
     * 查询专家库
     *
     * @param query
     * @param perm
     * @param beighDate
     * @param endDate
     * @return
     */
    PageModel<List<ExpertModel>> queryExpert(QueryExpertModel query, DataPermModel perm, Date beighDate, Date endDate);

    /**
     * 新增专家信息s
     *
     * @param info
     * @param model
     * @return
     */
    boolean addExpert(UserInfo info, ExpertModel model);

    /**
     * 修改专家信息
     *
     * @param info
     * @param model
     * @return
     */
    boolean updateExpertStatus(UserInfo info, ExpertModel model);

    /**
     * 删除专家信息
     *
     * @param model
     * @return
     */
    boolean delExpert(ExpertModel model);

    /**
     * 查询编号
     *
     * @return
     */
    String getMaxExpertNumber();

    /**
     * 更新专家信息
     *
     * @return
     */
    boolean updateExpert(UserInfo info, ExpertModel model);

    /**
     * 通过uuid获取专家信息
     * @param expertUuid
     * @return
     */
    Expert getExpertByUuid(String expertUuid);

    /**
     * 通过id获取专家信息
     * @param id
     * @return
     */
    Expert getExpertById(int id);


}
