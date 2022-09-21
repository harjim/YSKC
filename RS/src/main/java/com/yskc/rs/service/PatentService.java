package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.PatentEntity;
import com.yskc.rs.models.Patent.PatentModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.PatentExcel;

import java.util.List;

public interface PatentService {

    /**
     * 查询专利
     *
     * @param model
     * @return
     */
    PageModel<List<PatentModel>> queryPatent(PatentModel model);

    /**
     * 添加专利
     *
     * @param userInfo
     * @param entity
     * @return
     */
    boolean addPatent(UserInfo userInfo, PatentEntity entity);

    /**
     * 编辑专利
     *
     * @param userInfo
     * @param entity
     * @return
     */
    boolean editPatent(UserInfo userInfo, PatentEntity entity);

    /**
     * 检查重复专利号
     *
     * @param patentNo
     * @param projectId
     * @param pid
     * @return
     */
    boolean checkPatentNo(String patentNo, Integer projectId, Integer pid);

    /**
     * 删除专利
     *
     * @param entity
     * @return
     */
    boolean delPatent(PatentEntity entity);

    /**
     * 导入专利
     *
     * @param info
     * @param data
     * @param projectId
     * @return
     */
    String importPatent(UserInfo info, List<PatentExcel> data, Integer projectId) throws OwnerException;
}
