package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.VoucherExcel;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/14 15:01
 * description:
 */
public interface VoucherService {

    /**
     * 导入凭证
     * @param info
     * @param voucherExcels
     * @return
     * @throws OwnerException
     */
    boolean importVoucher(RsUserInfo info, List<VoucherExcel> voucherExcels) throws OwnerException;
}
