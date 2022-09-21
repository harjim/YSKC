package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.entity.User;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.VoucherExcel;
import com.yskc.rs.models.voucher.QueryVoucherModel;
import com.yskc.rs.models.voucher.RelatedVoucherModel;
import com.yskc.rs.models.voucher.VoucherModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/14 15:01
 * description:
 */
public interface VoucherService {

    PageModel<List<VoucherModel>> getList(QueryVoucherModel query,Integer companyId);

    /**
     * 检查凭证号是否唯一
     * @param voucherNo
     * @param companyId
     * @return
     */
    Boolean checkVoucherNo(String voucherNo,Integer companyId) throws OwnerException;

    /**
     * 添加凭证
     * @param model
     * @param userInfo
     * @return
     */
    Boolean addVoucher(VoucherModel model, UserInfo userInfo) throws OwnerException;

    /**
     * 修改凭证
     * @param userInfo
     * @param model
     * @return
     */
    Boolean updateVoucher(UserInfo userInfo,VoucherModel model) throws OwnerException;

    /**
     * 删除凭证
     * @param companyId
     * @param id
     * @return
     * @throws OwnerException
     */
    Boolean deleteById(Integer companyId,Integer id)throws OwnerException;

    /**
     * 导入凭证
     * @param info
     * @param voucherExcels
     * @return
     * @throws OwnerException
     */
    boolean importVoucher(UserInfo info, List<VoucherExcel> voucherExcels) throws OwnerException;

    /**
     * 关联项目
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    Boolean relatedVoucher(UserInfo userInfo, RelatedVoucherModel model) throws OwnerException;
}
