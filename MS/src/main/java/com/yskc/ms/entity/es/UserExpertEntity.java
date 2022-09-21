package com.yskc.ms.entity.es;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.es.models.EsBaseEntity;

import java.io.Serializable;

/**
 * @DateTime: 2021/9/23 15:38
 * @Description:
 * @author: hsx
 */
@TableName("user_expert")
public class UserExpertEntity extends EsBaseEntity implements Serializable {

    private static final long serialVersionUID = 1092364375649700616L;

    @TableId
    private Integer id;

    private Integer userId;

    private String types;                   //专家类型 0：技术专家，1：财务专家，2：管理专家，3：风险评估专家，4：知识产权专家

    private Boolean transferResult;         //有技术成果愿意科技转化

    private Boolean govReview;              // 参加过'政府资助项目'评审

    private String govReviewName;           // 参加过'政府资助项目'名称(当govReview=true时,govReviewName应有值)

    private String researchResult;          // 研究成果

    private Integer status;                 //  0已提交，1通过，2驳回

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public Boolean getTransferResult() {
        return transferResult;
    }

    public void setTransferResult(Boolean transferResult) {
        this.transferResult = transferResult;
    }

    public Boolean getGovReview() {
        return govReview;
    }

    public void setGovReview(Boolean govReview) {
        this.govReview = govReview;
    }

    public String getGovReviewName() {
        return govReviewName;
    }

    public void setGovReviewName(String govReviewName) {
        this.govReviewName = govReviewName;
    }

    public String getResearchResult() {
        return researchResult;
    }

    public void setResearchResult(String researchResult) {
        this.researchResult = researchResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
