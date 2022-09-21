package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ProjectOutsourcingExcel;
import com.yskc.rs.models.outsourcing.ProjectOutsourcingModel;
import com.yskc.rs.models.outsourcing.SaveOutsourcingModel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2021-06-08 15:01
 * @Description: 项目委外费用业务层接口
 */
public interface ProjectOutsourcingService {
    /**
     * 获取委外费用列表
     *
     * @param year
     * @param type
     * @param companyId
     * @return
     */
    List<ProjectOutsourcingModel> getOutsourcingList(Integer year, Integer type, Integer companyId);

    /**
     * 保存委外项目费用
     *
     * @param saveOutsourcing
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean save(SaveOutsourcingModel saveOutsourcing, UserInfo userInfo) throws OwnerException;

    /**
     * 导入委外项目费用
     *
     * @param info
     * @param data
     * @param type
     * @param year
     * @return
     * @throws OwnerException
     */
    Boolean importOutsourcing(UserInfo info, List<ProjectOutsourcingExcel> data, Integer type, Integer year) throws OwnerException;

    /**
     * 获取导出数据
     *
     * @param year
     * @param type
     * @param companyId
     * @return
     */
    List<Map<String, Object>> getExportOutsourcing(Integer year, Integer type, Integer companyId);
}
