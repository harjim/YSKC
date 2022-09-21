package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.InitMemberEntity;
import org.springframework.stereotype.Repository;


/**
 * Created by hck
 * on 2020/9/2 16:55
 * description:
 */
@Repository
public interface InitMemberDao extends BaseMapper<InitMemberEntity> {

    /**
     * 获取研发人员总数
     * @return
     */
    Integer getMemberStatNumber();


}
