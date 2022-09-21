package com.xxl.job.executor.models.flowInstance;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.xxl.job.executor.utils.ToolUtils;

import java.util.*;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.flowInstance
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-26 09:27
 * @Description: 文档流程信息model
 */
public class AuditDocFileMsgModel extends AuditMsgModel {

    private String docFileName;

    public AuditDocFileMsgModel(Integer status, String modeName, String nodeName, String projectName, Integer initiatorId, Integer instanceId, Integer nodeId, Set<Integer> userIds, Integer type,String docFileName) {
        super(status, modeName, nodeName, projectName, initiatorId, instanceId, nodeId, userIds, type);
        this.docFileName = docFileName;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }


    @Override
    public List<Map<String, String>> buildMsgForm(String creator, Boolean isSendCopy, String state) {
        List<Map<String, String>> form = new ArrayList<>();
        form.add(ToolUtils.kvMap("发起人：", creator));
        form.add(ToolUtils.kvMap("项目：", getProjectName()));
        form.add(ToolUtils.kvMap("文档：", docFileName));
        form.add(ToolUtils.kvMap("模块：", getModeName()));
        form.add(ToolUtils.kvMap("节点：", getNodeName()));
        if (!isSendCopy) {
            form.add(ToolUtils.kvMap("状态：", state));
        } else {
            form.add(ToolUtils.kvMap("状态：", "已审核"));
        }
        form.add(ToolUtils.kvMap("时间：", DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN)));
        return form;
    }
}
