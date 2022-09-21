package com.xxl.job.executor.utils;

import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.core.config.Constant;
import com.xxl.job.executor.core.config.WeChatConfig;
import com.xxl.job.executor.models.wechat.TemplateMsgModel;
import com.xxl.job.executor.models.wechat.WeChatAccessTokenModel;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Map;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-12-28 10:46
 * @Description: 微信工具
 */
@Component
public class WeChatUtils {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private WeChatConfig weChatConfig;

    public String getWeChatAccessToken() throws OwnerException {
        String accessToken = redisUtils.get(Constant.WE_CHAT_ACCESS_TOKEN_KEY, String.class);
        if (StringUtils.isEmpty(accessToken)) {
            WeChatAccessTokenModel accessTokenModel = restTemplate.getForObject(
                    MessageFormat.format(Constant.WE_CHAT_ACCESS_TOKEN_URL, weChatConfig.getAppid(),
                            weChatConfig.getSecret()), WeChatAccessTokenModel.class);
            accessToken = accessTokenModel.getAccess_token();
            if (StringUtils.isEmpty(accessToken)) {
                XxlJobLogger.log(accessTokenModel.toString());
                throw new OwnerException("验证失败。");
            }
            redisUtils.set(Constant.WE_CHAT_ACCESS_TOKEN_KEY, accessToken, Constant.WE_CHAT_ACCESS_TOKEN_TIMEOUT);
        }
        return accessToken;
    }

    public void sendMsg(String openId, Map<String, Map<String, Object>> data, String token) throws OwnerException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> request = new HttpEntity<>(TemplateMsgModel.build(openId, weChatConfig.getMsgTemplateId(), "pages/index/index", data), headers);
        Object obj = restTemplate.exchange(MessageFormat.format(Constant.WE_CHAT_SUBSCRIBE_MSG_URL, token), HttpMethod.POST, request, Object.class);
        XxlJobLogger.log(obj.toString());
    }
}
