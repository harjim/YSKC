package com.yskc.ms.service.impl.ms;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.yskc.common.utils.RedisUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.enums.SnEnum;
import com.yskc.ms.service.ms.SnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2022-08-31 18:36
 * @Description: 序列号实现层
 */
@Service
public class SnServiceImpl implements SnService {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String getNextNo(SnEnum sn) {
        return getSnNo(sn, null);
    }

    @Override
    public String getNextNo(SnEnum sn, String prefix) {
        return getSnNo(sn, prefix);
    }

    /**
     * 获取序列号
     *
     * @param sn
     * @param prefix
     * @return
     */
    private String getSnNo(SnEnum sn, String prefix) {
        Date now = new Date();
        return MessageFormat.format("{0}{1}{2}", StringUtils.hasLength(prefix) ? prefix : sn.getPrefix(),
                DateUtil.format(now, DatePattern.PURE_DATE_PATTERN),
                StrUtil.fillBefore(getIncrNo(sn.getType(), now).toString(), '0', 4));
    }


    /**
     * 获取自增数
     * step默认为1
     *
     * @param snType 序列类型
     * @param now    当前时间
     * @return
     */
    private Integer getIncrNo(Integer snType, Date now) {
        return getIncrNo(snType, now, 1);
    }

    /**
     * 获取自增数
     *
     * @param snType 序列类型
     * @param now    当前时间
     * @param step   步进
     * @return
     */
    private Integer getIncrNo(Integer snType, Date now, Integer step) {
        String key = MessageFormat.format(Constant.REDIS_KEY_SN_NO, snType);
        Integer maxNo = redisUtils.get(key, Integer.class);
        if (maxNo == null) {
            maxNo = 0;
        }
        maxNo += step;
        redisUtils.set(key, maxNo, DateUtil.between(now, DateUtil.endOfDay(now), DateUnit.SECOND));
        return maxNo;
    }

}
