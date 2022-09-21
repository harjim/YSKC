package com.yskc.ms.utils;

import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.DingUserDao;
import com.yskc.ms.models.ding.DingMsgModel;
import com.yskc.ms.models.dingding.Department;
import com.yskc.ms.models.dingding.DingInfo;
import com.yskc.ms.models.dingding.DingUser;
import com.yskc.ms.models.dingding.DingUserModel;
import com.yskc.ms.models.mobile.AppDataModel;
import com.yskc.ms.models.mobile.CodeLoginModel;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 钉钉帮助类
 *
 * @author huronghua
 */
@Component
public class DingUtils {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private DingUserDao dingUserDao;

    private String getUrl(String url, Object... arguments) {
        ArrayList arrList = new ArrayList(arguments.length + 1);
        arrList.add(getAccessToken());
        if (arguments.length != 0) {
            arrList.addAll(Arrays.asList(arguments));
        }
        return MessageFormat.format(url, arrList.toArray());
    }


    /**
     * 获取单条信息
     *
     * @param url
     * @param tClass
     * @param <T>
     * @return
     */
    private <T> T getMapToPojo(String url, Class<T> tClass, Object... arguments) {
        String tempUrl = getUrl(url, arguments);
        Map<String, Object> map = restTemplate.getForObject(tempUrl, Map.class);
        if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_VALUE)) {
            return JsonUtils.mapToPojo(map, tClass);
        } else if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_TOKEN_FAILL_VALUE)) {
            redisUtils.del(Constant.DING_TOKEN);
            logger.error(map.get(Constant.DING_ERRMSG).toString());
            return getMapToPojo(url, tClass, arguments);
        } else {
            logger.error(map.get(Constant.DING_ERRMSG).toString());
            return null;
        }
    }

    /**
     * 获取单条信息
     *
     * @param url
     * @param filed
     * @param tClass
     * @param <T>
     * @return
     */
    private <T> T getJsonToPojo(String url, String filed, Class<T> tClass, Object... arguments) {
        String tempUrl = getUrl(url, arguments);
        Map<String, Object> map = restTemplate.getForObject(tempUrl, Map.class);
        if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_VALUE)) {
            return JsonUtils.jsonToPojo(JsonUtils.objectToJson(map.get(filed)), tClass);
        } else if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_TOKEN_FAILL_VALUE)) {
            redisUtils.del(Constant.DING_TOKEN);
            logger.error(map.get(Constant.DING_ERRMSG).toString());
            return getJsonToPojo(url, filed, tClass, arguments);
        } else {
            logger.error(map.get(Constant.DING_ERRMSG).toString());
            return null;
        }
    }

    /**
     * 列表请求
     *
     * @param url
     * @param filed
     * @param tClass
     * @param <T>
     * @return
     */
    private <T> List<T> getJsonToList(String url, String filed, Class<T> tClass, Object... arguments) {
        String tempUrl = getUrl(url, arguments);
        Map<String, Object> map = restTemplate.getForObject(tempUrl, Map.class);
        if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_VALUE)) {
            return JsonUtils.jsonToList(JsonUtils.objectToJson(map.get(filed)), tClass);
        } else if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_TOKEN_FAILL_VALUE)) {
            redisUtils.del(Constant.DING_TOKEN);
            logger.error(map.get(Constant.DING_ERRMSG).toString());
            return getJsonToList(url, filed, tClass, arguments);
        } else {
            logger.error(map.get(Constant.DING_ERRMSG).toString());
            return new ArrayList<>();
        }
    }

    /**
     * 获取钉钉授权码
     *
     * @return
     */
    public String getAccessToken() {
        if (redisUtils.hasKey(Constant.DING_TOKEN)) {
            return redisUtils.get(Constant.DING_TOKEN, String.class);
        } else {
            String url = MessageFormat.format(Constant.DING_GETTOKEN, msConfig.getDingDingConfig().getAppKey(),
                    msConfig.getDingDingConfig().getAppSecret());

            Map<String, Object> map = restTemplate.getForObject(url, Map.class);
            if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_VALUE)) {
                String accessToken = JsonUtils.jsonToPojo(JsonUtils.objectToJson(map.get(Constant.DING_TOKEN)),
                        String.class);
                redisUtils.set(Constant.DING_TOKEN, accessToken, Constant.DING_TIME);
                return accessToken;
            }
            return "";
        }
    }

    /**
     * 发送文本信息到用户（传入用户id）
     *
     * @param userIds
     * @param content
     * @return
     * @throws OwnerException
     */
    public String sendTextMsgToUser(List<Integer> userIds, String content) throws OwnerException {
        if (CollectionUtils.isEmpty(userIds)) {
            String errorMsg = "发送钉钉用户工作通知失败，未指定发送用户。";
            logger.error(errorMsg);
            throw new OwnerException(errorMsg);
        }
        List<String> dingUserIds = dingUserDao.getDingUserIds(userIds);
        String userIdList = null;
        if (!CollectionUtils.isEmpty(dingUserIds)) {
            userIdList = String.join(",", dingUserIds);
        }
        return sendTextMsgToUser(userIdList, content);
    }

    /**
     * 发送文本消息到用户(钉钉用户id可多个','隔开)
     *
     * @param userIdList
     * @param content
     * @return
     * @throws OwnerException
     */
    public String sendTextMsgToUser(String userIdList, String content) throws OwnerException {
        if (StringUtils.isEmpty(userIdList)) {
            String errorMsg = "发送钉钉用户工作通知失败，未指定发送用户。";
            logger.error(errorMsg);
            throw new OwnerException(errorMsg);
        }
        if (StringUtils.isEmpty(content)) {
            String errorMsg = "发送钉钉用户工作通知失败，发送内容不能为空。";
            logger.error(errorMsg);
            throw new OwnerException(errorMsg);
        }
        String token = getAccessToken();
        if (StringUtils.isEmpty(token)) {
            String errorMsg = "获取钉钉token失败，请稍后重试。";
            logger.error(errorMsg);
            throw new OwnerException(errorMsg);
        }
        String url = MessageFormat.format(Constant.DING_SEND_MSG, token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String paramJson = DingMsgModel.buildToUser(msConfig.getDingDingConfig().getAgentId(), DingMsgUtils.buildTextMsg(content), userIdList);
        HttpEntity<String> request = new HttpEntity<>(paramJson, headers);
        Object obj = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        return obj.toString();
    }

    /**
     * 获取钉钉部门
     *
     * @return
     */
    public List<Department> getAllDepartmentList() {
        return getJsonToList(Constant.DING_DEPARTMENT_LIST, Constant.DING_DEPARTMENT, Department.class);
    }

    /**
     * 获取钉钉部门详情
     *
     * @param deptId
     * @return
     */
    public Department getDepartment(Integer deptId) {
        return getMapToPojo(Constant.DING_DEPARTMENT_INFO, Department.class, deptId.toString());
    }

    /**
     * 根据部门ID获取钉钉用户列表
     *
     * @param deptId 部门ID
     * @return
     */
    public List<DingUser> getDingUserList(Integer deptId) {
        List<DingUser> dingUsers = new ArrayList<>();
        Integer offset = 0;
        Integer size = 100;
        while (true) {
            List<DingUser> tmpDingUsers = getJsonToList(Constant.DING_USER_LIST, Constant.DING_USERLIST, DingUser.class, deptId.toString(),
                    offset, size);
            if (tmpDingUsers.size() > 0) {
                dingUsers.addAll(tmpDingUsers);
            }
            if (tmpDingUsers.size() < size) {
                break;
            }
            offset += size;
        }
        return dingUsers;
    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    public DingInfo getAllDingUser() {
        DingInfo dingInfo = new DingInfo();
        List<Department> departments = getAllDepartmentList();
        List<DingUser> dingUsers = new ArrayList<>();
        Map<String, Boolean> userMap = new HashMap<>();
        for (Department department : departments) {
            List<DingUser> tmpDingUsers = getDingUserList(department.getId());
            String mUserId = "";
            for (DingUser dingUser : tmpDingUsers) {
                if (dingUser.getIsLeader()) {
                    mUserId = mUserId + dingUser.getUserid() + "|";
                }
                if (userMap.get(dingUser.getUserid()) == null) {
                    userMap.put(dingUser.getUserid(), true);
                    dingUsers.add(dingUser);
                }
            }
            department.setDeptManagerUseridList(mUserId);
        }
        dingInfo.setDepartments(departments);
        dingInfo.setDingUsers(dingUsers);
        return dingInfo;
    }

    /**
     * 获取企业员工人数
     *
     * @return
     */
    public Integer getAllUserCount() {
        String tempUrl = getUrl(Constant.DING_GET_ORG_USER_COUNT);
        Map<String, Object> map = restTemplate.getForObject(tempUrl, Map.class);
        if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_VALUE)) {
            return Integer.valueOf(map.get("count").toString());
        } else if (map.get(Constant.DING_ERRCODE).toString().equals(Constant.DING_ERRCODE_TOKEN_FAILL_VALUE)) {
            redisUtils.del(Constant.DING_TOKEN);
            logger.error(map.get(Constant.DING_ERRMSG).toString());
            return getAllUserCount();
        } else {
            logger.error(map.get(Constant.DING_ERRMSG).toString());
            return null;
        }
    }

    /**
     * 获取部门用户userid列表
     *
     * @param deptId 部门id
     * @return
     */
    public List<String> getDeptMember(Integer deptId) {
        return getJsonToList(Constant.DING_GET_DEPT_MEMBER, Constant.DING_USER_IDS, String.class,
                deptId.toString());
    }

    /**
     * 获取用户详情
     *
     * @param userId
     * @return
     */
    public DingUser getUserInfo(String userId) {
        return getMapToPojo(Constant.DING_GET_USER_INFO, DingUser.class, userId);
    }

    private String urlEncode(String value, String encoding) {
        if (value == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, encoding);
            return encoded.replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("FailedToEncodeUri", e);
        }
    }

    /**
     * 根据扫码获取用户唯一标志
     *
     * @param loginTmpCode
     * @return
     */
    public DingUserModel getUserUnionIdByCode(String loginTmpCode, String ip) throws OwnerException {

        try {
            String stringToSign = String.valueOf(System.currentTimeMillis());
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(msConfig.getDingLoginCinfig().getAppSecret().getBytes("UTF-8"), "HmacSHA256"));
            byte[] signatureBytes = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String signature = new String(Base64.encodeBase64(signatureBytes));
            String urlEncodeSignature = urlEncode(signature, "UTF-8");
            String url = MessageFormat.format(Constant.DING_GETUSERINFO_BYCODE, urlEncodeSignature, stringToSign,
                    msConfig.getDingLoginCinfig().getAppId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> map = new HashMap<>(1);
            map.put("tmp_auth_code", loginTmpCode);
            AppDataModel result = postJson(url, JsonUtils.objectToJson(map));
            if (!result.success()) {
                logger.error(MessageFormat.format("ip:{0},参数：{1}，url:{2}，结果：{3}", ip, loginTmpCode, url, JsonUtils.objectToJson(result)));
                throw new OwnerException(ErrorEnum.HS_ERROR);
            }
            return JsonUtils.mapToPojo(result.getUser_info(), DingUserModel.class);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
        } catch (InvalidKeyException e) {
            logger.error("InvalidKeyException", e);
        } catch (OwnerException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public DingUser codeLogin(CodeLoginModel codeLogin) throws OwnerException {
        String url = getUrl(Constant.DING_CODE_LOGIN);
        try {
            AppDataModel result = postJson(url, JsonUtils.objectToJson(codeLogin));
            if (!result.success()) {
                logger.error(result.getErrmsg());
                throw new OwnerException(ErrorEnum.HS_ERROR);
            }
            return JsonUtils.mapToPojo(result.getResult(), DingUser.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("登录失败！");
        }
    }

    /**
     * post方式提交json代码
     *
     * @return
     * @throws Exception
     */
    public AppDataModel postJson(String url, String json) throws Exception {
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = null;
        // 接收响应结果
        CloseableHttpResponse response = null;
        String result = "";
        // 创建httppost
        httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        // 参数
        StringEntity se = new StringEntity(json);
        se.setContentEncoding("UTF-8");
        se.setContentType("application/json");// 发送json需要设置contentType
        httpPost.setEntity(se);
        response = httpclient.execute(httpPost);
        // 解析返结果
        org.apache.http.HttpEntity entity = response.getEntity();
        if (entity != null) {
            result = EntityUtils.toString(entity, "UTF-8");
        }
        httpclient.close();
        response.close();
        return JsonUtils.jsonToPojo(result, AppDataModel.class);
    }

}
