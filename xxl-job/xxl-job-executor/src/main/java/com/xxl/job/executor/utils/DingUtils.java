package com.xxl.job.executor.utils;

import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.core.config.XxlJobConfig;
import com.yskc.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 15:52
 * @Description: 钉钉util
 */
@Component
public class DingUtils {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private XxlJobConfig xxlJobConfig;

    /**
     * 获取钉钉授权码
     *
     * @return
     */
    public String getAccessToken() {
        if (redisUtils.hasKey(Constant.DING_TOKEN)) {
            return redisUtils.get(Constant.DING_TOKEN, String.class);
        } else {
            String url = MessageFormat.format(Constant.DING_GETTOKEN, xxlJobConfig.getAppKey(),
                    xxlJobConfig.getAppSecret());
            Map<String, Object> map = restTemplate.getForObject(url, Map.class);
            if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_VALUE)) {
                String token = map.get(Constant.DING_TOKEN).toString();
                redisUtils.set(Constant.DING_TOKEN, token, Constant.DING_TIME);
                return token;
            }
            return "";
        }
    }


}
