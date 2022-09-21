package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.DeptExcel;

import java.util.List;

/**
 * 组织架构
 *
 * @author Administrator
 */
public interface DeptService {

    String importDept(RsUserInfo info, List<DeptExcel> data) throws OwnerException;
}
