package com.yskc.rs.service;

import com.yskc.rs.entity.SupportEntity;
import com.yskc.rs.models.UserInfo;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-09-25 11:29
 * @Description: SupportService
 */
public interface SupportService {
    /**
     * 查询扶持情况
     *
     * @param companyId
     * @return
     */
    List<SupportEntity> querySupportList(Integer companyId, Date checkTime, String supportTime, String supportDeptName, String projectName);

    /**
     * 添加
     *
     * @param info
     * @param entity
     * @return
     */
   boolean addSupport(UserInfo info, SupportEntity entity);

    /**
     * 修改
     * @param info
     * @param entity
     * @return
     */
    boolean updateSupport(UserInfo info, SupportEntity entity);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean delSupport(Integer id);

}
