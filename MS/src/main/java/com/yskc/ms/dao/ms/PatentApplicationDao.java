package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.patent.PatentDemandModel;
import com.yskc.ms.models.patent.PatentMemberModel;
import com.yskc.ms.models.patent.QueryPatentApplicationModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public interface PatentApplicationDao {

    List<PatentDemandModel> getList(@Param("page") Pagination page, @Param("query")QueryPatentApplicationModel query, @Param("dataPerm") DataPermModel dataPerm);

    List<PatentMemberModel> getMemberList(@Param("demandIds") List<Integer> demandIds);

    List<HashMap<Integer,String>> getName(@Param("set") Set set);

    String getPath(@Param("type") String type,@Param("id") int id);

    Integer updatePath(@Param("type") String type,@Param("id") int id,@Param("path") String path);

    Integer setMember(@Param("list") List<PatentMemberModel> list);

    Integer delMember(@Param("ids") List<Integer> list,@Param("mType")Integer mType);

    List<PatentMemberModel> selectMember();
}
