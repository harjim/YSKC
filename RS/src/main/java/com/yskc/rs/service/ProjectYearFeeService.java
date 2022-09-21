package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.entity.project.ProjectYearFeeEntity;
import com.yskc.rs.models.UserInfo;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-07 08:56
 * @Description: 优惠明细表年费用业务接口层
 */
public interface ProjectYearFeeService {
    /**
     * 导出明细表
     *
     * @param year
     * @param info
     * @param out
     * @throws OwnerException
     */
    void exportDetailData(Integer year, UserInfo info, OutputStream out) throws OwnerException;

    /**
     * 获取明细表数据
     *
     * @param year
     * @param userInfo
     * @param list
     * @param sheetNames
     * @return
     */
    Map<String, Object> getDetailData(Integer year, UserInfo userInfo, List<Map<String, Object>> list, List<String> sheetNames);

    /**
     * 保存明细表数据
     *
     * @param entity
     * @param userInfo
     * @return
     */
    Boolean save(ProjectYearFeeEntity entity, UserInfo userInfo);
}
