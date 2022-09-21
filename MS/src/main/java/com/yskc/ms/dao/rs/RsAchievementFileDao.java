package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsAchievementFileEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: rs成果文件dao层
 */
@Repository
public interface RsAchievementFileDao extends BaseMapper<RsAchievementFileEntity> {

    Integer addList(@Param("list")List<RsAchievementFileEntity> list);


}
