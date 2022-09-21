package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.SolutionEntity;
import com.yskc.ms.entity.ms.SolutionShareEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.solution.QuerySolutionModel;
import com.yskc.ms.models.solution.SolutionModel;
import com.yskc.ms.models.solution.SolutionShareModel;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-09 10:23
 * @Description: 知识库业务接口层
 */
public interface SolutionService {
    /**
     * 获取解决方案列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageModel<List<SolutionModel>> getList(QuerySolutionModel query, DataPermModel dataPerm);

    /**
     * 获取分享的解决方案
     *
     * @param query
     * @param userInfo
     * @return
     */
    PageModel<List<SolutionModel>> getShareList(QuerySolutionModel query, UserInfo userInfo);

    /**
     * 添加解决方案
     *
     * @param solution
     * @param userInfo
     * @return
     */
    Boolean add(SolutionModel solution, UserInfo userInfo);

    /**
     * 编辑解决方案
     *
     * @param solution
     * @param userInfo
     * @return
     */
    Boolean edit(SolutionModel solution, UserInfo userInfo);

    /**
     * 删除解决方案
     *
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean delete(List<Integer> ids) throws OwnerException;

    /**
     * 获取解决方案分享详情
     *
     * @param solutionId
     * @return
     */
    List<SolutionShareModel> getShareInfo(Integer solutionId);

    /**
     * 分享解决方案
     *
     * @param share
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean share(List<SolutionShareEntity> share, UserInfo userInfo)throws OwnerException;

    /**
     * 删除解决方案分享
     * @param ids
     * @return
     */
    Boolean deleteShare(List<Integer> ids);

    /**
     * 评星
     * @param solution
     * @return
     */
    Boolean rate(SolutionEntity solution,Integer userId);
}
