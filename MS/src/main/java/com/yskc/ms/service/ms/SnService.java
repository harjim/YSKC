package com.yskc.ms.service.ms;

import com.yskc.ms.enums.SnEnum;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: zhangdingfu
 * @CreateTime: 2022-08-31 18:35
 * @Description: 序列号接口层
 */
public interface SnService {

    /**
     * 获取下一个序列号
     *
     * @param sn 序列类型
     * @return
     */
    String getNextNo(SnEnum sn);
    /**
     * 获取下一个序列号
     *
     * @param sn 序列类型
     * @param prefix 自定义前缀
     * @return
     */
    String getNextNo(SnEnum sn,String prefix);

}
