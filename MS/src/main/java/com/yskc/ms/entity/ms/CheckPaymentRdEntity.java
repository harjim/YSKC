package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.ms.models.MsBaseEntity;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-09-06 13:38
 **/
@TableName("checkPayment_rd")
public class CheckPaymentRdEntity extends MsBaseEntity {
    @TableId
    private Integer id;
    private Integer checkPaymentId;
    private String rdTitle;
    private String pname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCheckPaymentId() {
        return checkPaymentId;
    }

    public void setCheckPaymentId(Integer checkPaymentId) {
        this.checkPaymentId = checkPaymentId;
    }

    public String getRdTitle() {
        return rdTitle;
    }

    public void setRdTitle(String rdTitle) {
        this.rdTitle = rdTitle;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}
