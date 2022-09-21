package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsPatentApplyEntity;
import com.yskc.ms.models.patent.PatentModel;
import com.yskc.ms.models.patent.RsPatentApplyModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/1 13:40
 * description:
 */
@Repository
public interface RsPatentApplyDao extends BaseMapper<RsPatentApplyEntity> {

    List<PatentModel> queryListByPatentNos(@Param("patentNos") List<String> patentNos);

    Integer deleteByPatentNo(String patentNo);

    List<RsPatentApplyModel> getDataByPatentNo(@Param("patentNo") String patentNo);

    /**
     * 获取entity
     *
     * @param patentNo
     * @return
     */
    List<RsPatentApplyEntity> getEntityDataByPatentNo(@Param("patentNo") String patentNo);


    /**
     * 批量插入
     *
     * @param applyList
     * @return
     */
    int insertBatch(@Param("applyList") List<RsPatentApplyEntity> applyList);
}
