package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.PatentDataEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentDataModel;
import com.yskc.ms.models.patent.QueryPatentDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/9/11 8:33
 * description:
 */
@Repository
public interface PatentDataDao extends BaseMapper<PatentDataEntity> {

    List<PatentDataModel> getList(@Param("page") Pagination page, @Param("query") QueryPatentDataModel query, @Param("perm") DataPermModel perm);
}
