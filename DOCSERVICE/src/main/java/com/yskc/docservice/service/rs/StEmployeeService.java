package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.StEmployeeExcel;


import java.io.OutputStream;
import java.util.List;

public interface StEmployeeService {

    String importStEmployee(RsUserInfo info, List<StEmployeeExcel> excels, Integer year) throws OwnerException;
}
