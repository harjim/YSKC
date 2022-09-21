package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.PatentDetailExcel;

import java.util.List;

/**
 * Created by hck
 * on 2020/6/29 11:36
 * description:
 */
public interface PatentDetailService {
    /**
     * 导入专利
     *
     * @param userInfo
     * @param list
     * @return
     * @throws OwnerException
     */
    String importPatent(RsUserInfo userInfo, List<PatentDetailExcel> list) throws OwnerException;

}
