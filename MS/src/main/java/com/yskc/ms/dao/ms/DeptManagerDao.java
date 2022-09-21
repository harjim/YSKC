package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.DeptManager;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: wangxing
 * @CreateTime: 2019-10-16 15:22
 * @Description: DeptManagerDao
 */
@Repository
public interface DeptManagerDao extends BaseMapper<DeptManager> {
    /**
     * 批量新增
     * @param deptManagers
     * @return
     */
    int insertBatch(@Param("deptManagers")List<DeptManager> deptManagers);

    /**
     * 批量修改
     * @param deptManagers
     * @return
     */
    int updateBatch(@Param("deptManagers") List<DeptManager> deptManagers);

    /**
     * 根据部门id查询数据
     * @param deptId
     * @return
     */
    List<DeptManager> getDeptManagerByDeptId(@Param("deptId")Integer deptId);

    /**
     *
     * @param deptId
     * @return
     */
    int deleteByDeptId(@Param("deptId")Integer deptId);
}
