package com.yskc.ms.utils;

import com.yskc.ms.config.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-08 10:55
 * @Description: 钉钉信息utils
 */
public class DingMsgUtils {

    public static Map<String, Object> buildTextMsg(String content) {
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put("content", content);
        Map<String, Object> result = new HashMap<>();
        result.put(Constant.DING_TEXT, msgMap);
        result.put("msgtype", Constant.DING_TEXT);
        return result;
    }

}
