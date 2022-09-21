package com.yskc.ms.service.rs;

import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.rs.RsPatentCostEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.PatentCostModel;
import com.yskc.ms.models.patent.QueryPatentCostModel;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/2 11:06
 * description:专利费用
 */
public interface RsPatentCostService {
    /**
     * 查询专利费用列表
     * @param query
     * @param perm
     * @return
     */
    PageModel<List<PatentCostModel>> queryPatentCost(QueryPatentCostModel query, DataPermModel perm);

    /**
     * 导出费用列表
     * @param query
     * @param info
     * @param perm
     * @param out
     * @param path
     */
    void export(QueryPatentCostModel query, UserInfo info, DataPermModel perm, OutputStream out, String path);

    /**
     * 批量设置备注
     * @param info
     * @param model
     * @return
     */
    boolean updateRemark(UserInfo info, PatentCostModel model);

    /**
     * 设置提醒日期
     * @param model
     * @return
     */
    boolean updateRemindDateTime(PatentCostModel model,UserInfo info);

    /**
     * 删除
     * @param model
     * @return
     */
    boolean delete(PatentCostModel model);

    /**
     * 批量删除专利费用
     * @param patentCosts
     * @return
     */
    boolean deletePatentCosts(List<RsPatentCostEntity> patentCosts);

    /**
     * 查询缴费信息
     * @param patentNo
     * @return
     */
    List<PatentCostModel> getPayDataBypatentNo(String patentNo);
}
