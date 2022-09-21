package com.yskc.ms.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.ms
 * @Author: wangxing
 * @CreateTime: 2019-10-16 15:11
 * @Description: DeptManager
 */
@TableName("ys_dept_manager")
public class DeptManager implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId
    public int id ;
    /// <summary>
    /// 部门Id
    /// </summary>
    public int depId ;
    /// <summary>
    /// 钉钉用户id，员工在当前企业内的唯一标识，也称staffId。可由企业在创建时指定，并代表一定含义比如工号，创建后不可修改
    /// </summary>

    public String dingUserId ;
    /// <summary>
    /// 钉钉员工在当前开发者企业账号范围内的唯一标识，系统生成，固定值，不会改变
    /// </summary>
    public String unionid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepId() {
        return depId;
    }

    public void setDepId(int depId) {
        this.depId = depId;
    }

    public String getDingUserId() {
        return dingUserId;
    }

    public void setDingUserId(String dingUserId) {
        this.dingUserId = dingUserId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
