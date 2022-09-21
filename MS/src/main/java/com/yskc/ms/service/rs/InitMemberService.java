package com.yskc.ms.service.rs;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.rs.InitMemberModel;
import com.yskc.ms.models.rs.QueryProjectInitMemberModel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:17
 * @Description: 初始化项目人员列表业务层接口
 */
public interface InitMemberService {


    /**
     * 根据项目id和年份获取当前员工人数
     *
     * @param projectIds
     * @param year
     * @return
     */
    Map<Integer, Map<String,Long>> getMemberByProAndYear(List<Integer> projectIds, Integer year);

    /**
     * 获取项目人员列表
     * @param query
     * @return
     */
    PageModel<List<InitMemberModel>> getList(QueryProjectInitMemberModel query);
}
