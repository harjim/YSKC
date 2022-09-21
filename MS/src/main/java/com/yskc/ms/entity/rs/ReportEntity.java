package com.yskc.ms.entity.rs;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-12 16:17:24
 */
@TableName("p_report")
public class ReportEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 *
	 */
	private Integer ryear;
	/**
	 *
	 */
	private String rname;
	/**
	 *
	 */
	private Integer companyId;
	/**
	 *
	 */
	private String remark;

	private int cnt;
	/**
	 *
	 */
	private Integer creatorId;
	/**
	 *
	 */
	private Date createTime;


	private Integer lastUpdatorId;

	private Date lastUpdateTime;

	private Integer msCreatorId;
	private Integer msLastUpdatorId;


	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public Integer getLastUpdatorId() {
		return lastUpdatorId;
	}

	public void setLastUpdatorId(Integer lastUpdatorId) {
		this.lastUpdatorId = lastUpdatorId;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Integer getMsCreatorId() {
		return msCreatorId;
	}

	public void setMsCreatorId(Integer msCreatorId) {
		this.msCreatorId = msCreatorId;
	}

	public Integer getMsLastUpdatorId() {
		return msLastUpdatorId;
	}

	public void setMsLastUpdatorId(Integer msLastUpdatorId) {
		this.msLastUpdatorId = msLastUpdatorId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setRyear(Integer ryear) {
		this.ryear = ryear;
	}

	public Integer getRyear() {
		return ryear;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRname() {
		return rname;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

}
