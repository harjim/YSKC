package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.proposal.ProposalListEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.proposal.ProposalListModel;
import com.yskc.rs.models.proposal.ProposalRelevanceModel;
import com.yskc.rs.models.proposal.QueryProposalModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/3/24 15:23
 * @Description:
 * @author: hsx
 */
@Repository
public interface ProposalListDao extends BaseMapper<ProposalListEntity> {

    /**
     * 获取提案列表数据
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<ProposalListModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryProposalModel query);

    /**
     * 获取提案列表数据详情
     * @param id
     * @return
     */
    ProposalListModel getInfo(@Param("id") Integer id);
    /**
     * 编辑提案列表数据
     * @param entity
     * @return
     */
    int edit(@Param("entity") ProposalListEntity entity);

    /**
     * 根据项目获取提案列表数据
     * @param ids
     * @return
     */
    List<ProposalListModel> getByProjectIds(@Param("ids") List<Integer> ids);

    /**
     * 获取关联项目和未关联项目的列表数据
     * @param id
     * @return
     */
    List<ProposalListModel> getByProjectId(Integer id);

    /**
     * 关联项目
     * @param model
     * @param date
     * @param info
     * @return
     */
    int relevance(@Param("model") ProposalRelevanceModel model, @Param("date") Date date, @Param("info")UserInfo info);

    /**
     * 取消关联
     * @param projectId
     * @param date
     * @param info
     * @return
     */
    int cancelRelevance(@Param("projectId") Integer projectId, @Param("date") Date date, @Param("info")UserInfo info);
}
