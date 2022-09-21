package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.es.UserFilesEntity;
import com.yskc.ms.models.newexpert.expert.UserFilesModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/9/24 10:38
 * @Description:
 * @author: hsx
 */
@Repository
public interface UserFilesDao extends BaseMapper<UserFilesEntity> {

    /**
     * @param userIds 查询用户对应上传的文件
     * @return
     */
    List<UserFilesModel> getFiles(@Param("userIds") List<Integer> userIds);
}
