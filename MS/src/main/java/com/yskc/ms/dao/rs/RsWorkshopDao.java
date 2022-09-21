package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.WorkshopEntity;
import com.yskc.ms.models.project.WorkshopModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RsWorkshopDao extends BaseMapper<WorkshopEntity> {

    /**
     * 获取工艺线/车间
     *
     * @param companyId
     * @param workshopName
     * @return
     */

    List<WorkshopModel> queryWorkshop(@Param("companyId") Integer companyId, @Param("workshopName") String workshopName);
}
