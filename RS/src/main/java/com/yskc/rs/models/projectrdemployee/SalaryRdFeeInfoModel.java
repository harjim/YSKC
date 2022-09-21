package com.yskc.rs.models.projectrdemployee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yskc.rs.models.project.RdSalaryDetailModel;
import com.yskc.rs.utils.ToolUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectrdemployee
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-26 09:53
 * @Description: 薪资研发费model
 */
public class SalaryRdFeeInfoModel {

    private String accountName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal rdFunds;

    private BigDecimal fee;

    private Map<String, BigDecimal> feeDetail;

    public static SalaryRdFeeInfoModel build(String accountName) {
        SalaryRdFeeInfoModel rdFeeInfo = new SalaryRdFeeInfoModel();
        rdFeeInfo.accountName = accountName;
        rdFeeInfo.rdFunds = rdFeeInfo.fee = BigDecimal.ZERO;
        rdFeeInfo.feeDetail = new HashMap<>();
        return rdFeeInfo;
    }


    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getRdFunds() {
        return rdFunds;
    }

    public void setRdFunds(BigDecimal rdFunds) {
        this.rdFunds = rdFunds;
    }

    public Map<String, BigDecimal> getFeeDetail() {
        return feeDetail;
    }

    public void setFeeDetail(Map<String, BigDecimal> feeDetail) {
        this.feeDetail = feeDetail;
    }

    public void add(SalaryRdFeeBaseModel item) {
        ToolUtils.loadDetailMap(item.getFeeDetail(), feeDetail, item.getRdRatio(), true);
        rdFunds = rdFunds.add(item.getRdFunds());
        fee = fee.add(item.getFee());
    }

    public void loadDetailMap() {
        BigDecimal totalFee = BigDecimal.ZERO;
        for (BigDecimal fee : feeDetail.values()) {
            totalFee = totalFee.add(fee);
        }
        RdSalaryDetailModel.loadDetailMap(fee, totalFee, rdFunds, feeDetail);
    }
}
