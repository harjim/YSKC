package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.SupportEntity;
import com.yskc.ms.models.company.SupportModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/12/4 14:27
 * description:
 */
@Repository
public interface SupportDao extends BaseMapper<SupportEntity> {
    /**
     * 获取公司（年）财政扶持情况
     *
     * @param eYear
     * @param bYear
     * @param companyId
     * @return
     */
    List<SupportModel> getSupport(@Param("eYear") Integer eYear, @Param("bYear") Integer bYear,
                                  @Param("companyId") Integer companyId);

    /**
     * 删除符合条件且ids外的数据
     *
     * @param saveIds
     * @param year
     * @param companyId
     * @return
     */
    Integer deleteData(@Param("saveIds") List<Integer> saveIds, @Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 批量插入
     *
     * @param insertList
     * @return
     */
    Integer insertList(@Param("insertList") List<SupportEntity> insertList);

    /**
     * 批量更新
     *
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<SupportEntity> updateList);
}
