package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.WorkshopEntity;
import com.yskc.rs.models.CommonOrgModel;
import com.yskc.rs.models.workshop.WorkshopTreeModel;
import com.yskc.rs.models.workshop.WorkshopModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-21 09:07:37
 */
@Repository
public interface WorkshopDao extends BaseMapper<WorkshopEntity> {
    /**
     * 获取工艺线/车间
     *
     * @param companyId
     * @param workshopName
     * @return
     */

    List<WorkshopModel> queryWorkshop(@Param("companyId") Integer companyId, @Param("workshopName") String workshopName);


    /**
     * 获取所有数据
     *
     * @param companyId
     * @return
     */

    List<WorkshopTreeModel> getWorkshopTree(@Param("companyId") Integer companyId);

    /**
     * @param companyId
     * @param parentId
     * @return
     */
    Integer getDataByparentId(@Param("companyId") Integer companyId, @Param("parentId") Integer parentId);


    /**
     *获取所有子节点
     * @param companyId
     * @param fullPath
     * @param id
     * @return
     */
    List<WorkshopEntity> queryChildrenByTerm(@Param("companyId") Integer companyId, @Param("fullPath") String fullPath, @Param("id") Integer id);


    /**
     * 批量更新
     *
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<WorkshopEntity> updateList);

    /**
     * 获取相同层级的同名数据
     * @param companyId
     * @param parentId
     * @param workshopName
     * @return
     */
    WorkshopEntity getSameLevelShop(@Param("companyId") Integer companyId,@Param("parentId") Integer parentId,@Param("workshopName") String workshopName);

    /**
     * 根据工艺线名称集合查出工艺线list
     * @param companyId
     * @param workshopList
     * @return
     */
    List<WorkshopEntity> selectByNames(@Param("companyId") Integer companyId, @Param("workshopList") List<String> workshopList);

    /**
     * 根据pid和序号查询是否已存在
     * @param parentId
     * @param seq
     * @return
     */
    List<WorkshopEntity> selectSeq(@Param("companyId")Integer companyId, @Param("parentId") Integer parentId,@Param("seq") Integer seq);

    Integer selectMaxSeq(@Param("companyId")Integer companyId, @Param("parentId")Integer parentId);

    List<WorkshopEntity> queryByCompanyId(@Param("companyId") Integer companyId);

    /**
     * 批量插入
     * @param insertList
     */
    @Options(useGeneratedKeys = true)
    void addBatch(List<WorkshopEntity> insertList);

    /**
     * 获取公司工艺线组织
     * @param companyId
     * @return
     */
    List<CommonOrgModel> getCompanyOrg(@Param("companyId") Integer companyId);
}
