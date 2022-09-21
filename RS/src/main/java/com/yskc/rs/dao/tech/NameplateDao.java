package com.yskc.rs.dao.tech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.tech.Nameplate;
import com.yskc.rs.models.tech.NameplateModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zdf-hck123
 * @since 2021-03-18
 */
@Repository
public interface NameplateDao extends BaseMapper<Nameplate> {
    /**
     * 获取铭牌
     * @param ids
     * @return
     */
    List<NameplateModel> getByInvestmentIds(@Param("ids") List<Integer> ids);

    /**
     * 删除投资铭牌
     * @param investmentIds
     * @return
     */
    Integer delByInvestmentId(@Param("investmentIds") List<Integer> investmentIds);

    /**
     * 批量插入
     * @param nameplates
     * @return
     */
    Integer insertList(@Param("nameplates") List<Nameplate> nameplates);
}
