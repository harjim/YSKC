package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.SysSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
/**
 * 会话dao
 * @author huronghua
 */
@Repository
public interface SysSessionDao  extends BaseMapper<SysSession> {
    /**
     * 插入
     * @param sysSession
     * @return
     */
    Integer insertOne(@Param("sysSession") SysSession sysSession);

    /**
     * 根据token获取用户会话
     * @param token
     * @return
     */
    SysSession getSessionByToken(@Param("token") String token);
}
