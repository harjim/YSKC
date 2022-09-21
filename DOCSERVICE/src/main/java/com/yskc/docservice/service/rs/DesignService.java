package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.DesginExcel;

import java.util.List;

/**
 * @author Administrator
 */
public interface DesignService {

    /**
     * 导入数据
     *
     * @param info
     * @param desginExcels
     * @return
     * @throws OwnerException
     */
    boolean importDesign(RsUserInfo info, List<DesginExcel> desginExcels, Integer year) throws OwnerException;

}
