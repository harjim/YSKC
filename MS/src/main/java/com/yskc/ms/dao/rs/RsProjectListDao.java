package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsProjectListEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/14 8:54
 * description:
 */
@Repository
public interface RsProjectListDao extends BaseMapper<RsProjectListEntity> {
    /**
     * 根据阶段文件类型获取文件
     * @param projectId
     * @return
     */
    List<RsProjectListEntity> getByProject(@Param("projectId") Integer projectId);

    /**
     * 根据阶段资料获取文件列表
     * @param stageListIds
     * @return
     */
    List<RsProjectListEntity> getListByStage(@Param("stageListIds") List<Integer> stageListIds);
}
