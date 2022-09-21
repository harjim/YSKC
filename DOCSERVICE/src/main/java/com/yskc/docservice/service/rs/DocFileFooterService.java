package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.SignatureExcel;
import java.io.OutputStream;
import java.util.List;

/**
 * @DateTime: 2022/3/8 8:22
 * @Description:
 * @author: wjy
 */
public interface DocFileFooterService {

    String importSignature(RsUserInfo info, List<SignatureExcel> list,Integer year) throws OwnerException;
 }
