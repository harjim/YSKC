package com.xxl.job.executor.utils;

import com.xxl.job.executor.core.config.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-08 10:55
 * @Description: 钉钉信息utils
 */
public class DingMsgUtils {

    private static Map<String, Object> buildMsg(Map<String, Object> msgMap, String msgType) {
        Map<String, Object> result = new HashMap<>();
        result.put(msgType, msgMap);
        result.put("msgtype", msgType);
        return result;
    }

    /**
     * 文本信息
     *
     * @param content
     * @return
     */
    public static Map<String, Object> buildTextMsg(String content) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("content", content);
        return buildMsg(msgMap, Constant.DING_TEXT);
    }

    /**
     * 链接信息
     *
     * @param title      标题
     * @param text       内容、简介
     * @param messageUrl 地址
     * @param picUrl     图片地址
     * @return
     */
    public static Map<String, Object> buildLinkMsg(String title, String text, String messageUrl, String picUrl) {
        Map<String, Object> linkMap = new HashMap<>();
        linkMap.put("messageUrl", messageUrl);
        linkMap.put("picUrl", picUrl);
        linkMap.put("title", title);
        linkMap.put("text", text);
        return buildMsg(linkMap, Constant.DING_LINK);
    }

    public static Map<String, Object> buildOAMsg(Map<String, String> head, List<Map<String,String>> form, String messageUrl) {
        Map<String, Object> oaMap = new HashMap<>();
        oaMap.put("message_url", messageUrl);
        oaMap.put("pc_message_url", messageUrl);
        oaMap.put("head", head);
        Map<String, Object> body = new HashMap<>();
        body.put("form", form);
        oaMap.put("body", body);
        return buildMsg(oaMap, Constant.DING_OA);
    }

}
