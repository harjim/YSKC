package com.yskc.ms.models.rs.summary;

import java.math.BigDecimal;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.rs.summary
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-23 14:06
 * @Description: 研发费用remain表
 */
public class BaseRemainModel {

    /**
     * d_* 表id
     */
    private Integer id;

    /**
     * 剩余费用
     */
    private BigDecimal remain;

    /**
     * 研发使用[费用或数量]
     */
    private BigDecimal rdUsed;

    /**
     * 总数[remain不能超过这个数值]
     */
    private BigDecimal total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRemain() {
        return remain;
    }

    public void setRemain(BigDecimal remain) {
        this.remain = remain;
    }

    public BigDecimal getRdUsed() {
        return rdUsed;
    }

    public void setRdUsed(BigDecimal rdUsed) {
        this.rdUsed = rdUsed;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void addRemain(BigDecimal rdUsed) {
        BigDecimal tempRemain = this.remain.add(rdUsed);
        if (tempRemain.compareTo(total) > 0) {
            this.remain = total;
        } else {
            this.remain = tempRemain;
        }
    }
}
