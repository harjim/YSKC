package com.yskc.ms.models.ding;


import com.yskc.common.utils.JsonUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.ding
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-06 15:09
 * @Description: 钉钉信息
 */
public class DingMsgModel {
    private Long agentId;

    private String userIdList;

    private String deptIdList;

    private Boolean toAllUser = false;

    private Map<String,Object> msg;

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(String userIdList) {
        this.userIdList = userIdList;
    }

    public String getDeptIdList() {
        return deptIdList;
    }

    public void setDeptIdList(String deptIdList) {
        this.deptIdList = deptIdList;
    }

    public Boolean getToAllUser() {
        return toAllUser;
    }

    public void setToAllUser(Boolean toAllUser) {
        this.toAllUser = toAllUser;
    }

    public Map<String, Object> getMsg() {
        return msg;
    }

    public void setMsg(Map<String, Object> msg) {
        this.msg = msg;
    }

    /**
     * 个人消息
     *
     * @param agentId
     * @param msg
     * @param userIdList
     * @return
     */
    public static String buildToUser(Long agentId, Map<String,Object> msg, String userIdList) {
        DingMsgModel dingMsgModel = new DingMsgModel();
        dingMsgModel.userIdList = userIdList;
        dingMsgModel.setDefault(agentId, msg);
        return dingMsgModel.toUser();
    }

    /**
     * 部门消息
     *
     * @param agentId
     * @param msg
     * @param deptIdList
     * @return
     */
    public static String buildToDept(Long agentId, Map<String,Object> msg, String deptIdList) {
        DingMsgModel dingMsgModel = new DingMsgModel();
        dingMsgModel.setDefault(agentId, msg);
        dingMsgModel.deptIdList = deptIdList;
        return dingMsgModel.toDept();
    }

    /**
     * 全体消息
     *
     * @param agentId
     * @param msg
     * @return
     */
    public static String buildToAllUser(Long agentId, Map<String,Object> msg) {
        DingMsgModel dingMsgModel = new DingMsgModel();
        dingMsgModel.setDefault(agentId, msg);
        return dingMsgModel.toAllUser();
    }

    private void setDefault(Long agentId, Map<String,Object> msg) {
        this.agentId = agentId;
        this.msg = msg;
    }

    /**
     * 所有用户+
     *
     * @return
     */
    public String toAllUser() {
        Map<String, Object> map = defaultMap();
        map.put("to_all_user", true);
        return JsonUtils.objectToJson(map);
    }

    public String toDept() {
        Map<String, Object> map = defaultMap();
        map.put("dept_id_list", deptIdList);
        return JsonUtils.objectToJson(map);
    }

    public String toUser() {
        Map<String, Object> map = defaultMap();
        map.put("userid_list", userIdList);
        return JsonUtils.objectToJson(map);
    }

    /**
     * 默认map
     *
     * @return
     */
    private Map<String, Object> defaultMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("agent_id", agentId);
       map.put("msg", msg);
        return map;
    }
}
