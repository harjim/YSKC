package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.RdDeptEntity;
import com.yskc.rs.models.CommonOrgModel;
import com.yskc.rs.models.rddept.RdDeptTree;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-08 15:23:42
 */
@Repository
public interface RdDeptDao extends BaseMapper<RdDeptEntity> {
    /**
     * 查询部门树
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RdDeptTree> queryRdDeptTree(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 匹配是否存在部门
     *
     * @param deptName
     * @param companyId
     * @param level
     * @param pid
     * @param year
     * @return
     */
    int checkedByDeptNameAndLevelAndPid(@Param("deptName") String deptName, @Param("companyId") Integer companyId,
                                        @Param("level") Integer level, @Param("pid") Integer pid,
                                        @Param("year") Integer year);

    /**
     * 查询部门使用次数
     *
     * @param id
     * @return
     */
    Integer checkedUsed(@Param("id") Integer id);

    /**
     * 根据名称和公司查询该部门是否存在
     *
     * @param deptName
     * @param companyId
     * @return
     */
    List<RdDeptEntity> getRdDeptByDeptName(@Param("deptName") String deptName, @Param("companyId") Integer companyId);

    /**
     * 获取该公司的第一级部门
     *
     * @param companyId
     * @return
     */
    RdDeptEntity getRdDeptOneData(@Param("companyId") Integer companyId);

    /**
     * 通过名字查询
     *
     * @param companyId
     * @param rdDeptNames
     * @return
     */
    List<RdDeptEntity> selectByNames(@Param("companyId") int companyId, @Param("rdDeptNames") List<String> rdDeptNames, @Param("year") Integer year);

    /**
     * 获取最大年份
     *
     * @param companyId
     * @return
     */
    Integer selectMaxYear(@Param("companyId") Integer companyId);

    /**
     * 获取最大年份 < 传入年份
     *
     * @param companyId
     * @param year
     * @return
     */
    List<Integer> getPrevYear(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取年份数据
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RdDeptEntity> getRdDeptByYear(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    @Options(useGeneratedKeys = true)
    Integer addBatch(@Param("list") List<RdDeptEntity> list);

    /**
     * 批量更新identity
     *
     * @param list
     * @return
     */
    Integer updateIdentity(@Param("list") List<RdDeptEntity> list);

    /**
     * 批量更新identity+ parentId + fullName
     *
     * @param list
     * @return
     */
    Integer updateIdentities(@Param("list") List<RdDeptEntity> list);

    /**
     * 获取当前节点及所有子节点
     *
     * @param identity
     * @param companyId
     * @return
     */
    List<RdDeptEntity> queryChildrenByTerm(@Param("identity") String identity, @Param("companyId") Integer companyId);

    /**
     * 获取公司研发组织
     *
     * @param companyId
     * @param year
     * @return
     */
    List<CommonOrgModel> getCompanyOrg(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * parentId
     *
     * @param parentId
     * @return
     */
    int countNode(@Param("parentId") Integer parentId);

    /**
     * 获取部门下所有子节点
     *
     * @param parentIds
     * @return
     */
    List<RdDeptEntity> getChildNode(@Param("parentIds") List<Integer> parentIds);

    /**
     * 批量更新
     *
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<RdDeptEntity> updateList);

    /**
     * 获取部门 全名称
     *
     * @param id
     * @return
     */
    String getFullName(@Param("id") Integer id);

    /**
     * 获取研发架构根节点
     *
     * @param year
     * @param companyId
     * @return
     */
    RdDeptEntity getParentNode(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取同级最大排序
     *
     * @param companyId
     * @param year
     * @param level
     * @return
     */
    Integer getMaxSeq(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("level") Integer level);

    /**
     * 获取下一个排序对象
     *
     * @param companyId
     * @param year
     * @param level
     * @param seq
     * @param left
     * @return
     */
    RdDeptEntity getNextSort(@Param("companyId") Integer companyId, @Param("year") int year,
                             @Param("level") Integer level, @Param("seq") int seq, @Param("left") boolean left);

    /**
     * @param sortId   排序id
     * @param seq      序号
     * @param nextId   切换序号id
     * @param nextSeq  切换序号
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    int updateSeq(@Param("sortId") Integer sortId, @Param("seq") Integer seq, @Param("nextId") Integer nextId,
                  @Param("nextSeq") Integer nextSeq, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId,
                  @Param("now") Date now);

    /**
     * 获取部门名称
     *
     * @param id
     * @return
     */
    String getDeptName(@Param("id") Integer id);
}
