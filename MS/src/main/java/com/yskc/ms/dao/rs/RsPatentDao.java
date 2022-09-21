package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.models.patent.PatentModel;
import com.yskc.ms.models.patent.PatentSpecificationModel;
import com.yskc.ms.models.patent.QueryPatentModel;
import com.yskc.ms.models.patent.RsPatentImportModel;
import com.yskc.ms.models.patentPlan.PatentAuditModel;
import com.yskc.ms.models.projectAudit.QueryAuditDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/1 8:35
 * description:
 */
@Repository
public interface RsPatentDao extends BaseMapper<RsPatentEntity> {
    List<PatentModel> queryPatentList(@Param("page") Pagination page, @Param("model") QueryPatentModel queryPatentModel);

    RsPatentEntity queryPatentByNo(@Param("patentNo") String patentNo);

    List<RsPatentEntity> queryListByPatentNos(@Param("patentNos") List<String> patentNos);

    Integer insertPatentList(@Param("models") List<RsPatentImportModel> models);

    Integer updatePatentList(@Param("models") List<RsPatentEntity> models);


    /**
     * 获取所有专利
     *
     * @param ids
     * @param patentNos
     * @return
     */
    List<RsPatentEntity> getAll(@Param("ids") List<Integer> ids, @Param("patentNos") List<String> patentNos);

    RsPatentEntity getPlanByPatentNo(@Param("patentNo") String patentNo);

    /**
     * 获取公司专利列表
     *
     * @param page
     * @param query
     * @param type  2:排除未提交
     * @return
     */
    List<PatentAuditModel> getPatentList(@Param("page") Pagination page, @Param("query") QueryAuditDataModel query, @Param("type") Integer type);

    /**
     * 获取公司专利列表
     *
     * @param query
     * @return
     */
    List<PatentAuditModel> getPatentList(@Param("query") QueryAuditDataModel query, Integer type);

    /**
     * 批量插入专利
     *
     * @param patents
     * @return
     */
    Integer insertPatents(@Param("patents") List<RsPatentEntity> patents);

    /**
     * 检查专利号是否存在
     *
     * @param patentNo
     * @param patentId
     * @return
     */
    List<RsPatentEntity> checkPatentNo(@Param("patentNo") String patentNo, @Param("patentId") Integer patentId);

    /**
     * 获取权利要求内容/说明书内容
     *
     * @param id
     * @return
     */
    PatentSpecificationModel getSpecification(@Param("id") Integer id);
}
