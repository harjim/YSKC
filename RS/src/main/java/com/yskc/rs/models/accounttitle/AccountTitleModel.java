package com.yskc.rs.models.accounttitle;

import com.baomidou.mybatisplus.annotations.TableId;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 原科目
 *
 * @author zhangdingfu
 */
public class AccountTitleModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer id;
    /**
     *
     */
    @Size(max = 50, message = "科目编码不能超过50个字")
    private String accountNumber;
    /**
     *
     */

    @Size(max = 200, message = "科目名称不能超过200个字")
    private String accountName;
    /**
     *
     */
    private Integer parentId;
    /**
     *
     */
    private Integer accoutType;

    private String fullAccountName;

    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    private List<AccountTitleModel> children;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setAccoutType(Integer accoutType) {
        this.accoutType = accoutType;
    }

    public Integer getAccoutType() {
        return accoutType;
    }

    public List<AccountTitleModel> getChildren() {
        return children;
    }

    public void setChildren(List<AccountTitleModel> children) {
        this.children = children;
    }

    public String getFullAccountName() {
        return fullAccountName;
    }

    public void setFullAccountName(String fullAccountName) {
        this.fullAccountName = fullAccountName;
    }
}
