package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ServiceUser;
import com.yskc.ms.models.servicelog.ServiceUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/5/5 14:41
 * 服务人员
 */
@Repository
public interface ServiceUserDao extends BaseMapper<ServiceUser> {

   Integer insertServiceUsers(@Param("users")List<ServiceUser> users);

   Integer delByServiceId(Integer id);

    /**
     * serviceIds
     * @param serviceIds
     * @return
     */
    List<ServiceUserModel> getUserByServiceIds(@Param("serviceIds") List<Integer> serviceIds);
}
