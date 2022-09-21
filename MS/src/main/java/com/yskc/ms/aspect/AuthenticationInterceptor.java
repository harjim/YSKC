package com.yskc.ms.aspect;

import com.yskc.common.annotation.NotLoginRequired;
import com.yskc.common.annotation.PermissionRequired;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.model.ResultModel;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.enums.UserStatusEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.role.MenuPermModel;
import com.yskc.ms.service.ms.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 登录拦截
 *
 * @author huronghua
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private UserService userService;

    /**
     * 重设HttpServletResponse
     *
     * @param response
     * @throws IOException
     */
    private void resetUnauthorized(HttpServletResponse response) throws IOException {
        //重置response
        response.reset();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setCharacterEncoding(Constant.UTF_8_KEY);
        response.setContentType(Constant.JSON_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.flushBuffer();
    }

    private void setResponse(HttpServletResponse response, ResultModel resultModel) throws IOException {
        response.reset();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setCharacterEncoding(Constant.UTF_8_KEY);
        response.setContentType(Constant.JSON_CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.print(JsonUtils.objectToJson(resultModel));
        out.flush();
        response.flushBuffer();
    }

    /**
     * 禁止访问
     *
     * @param response
     * @throws IOException
     */
    private void setForbiddenResponse(HttpServletResponse response) throws IOException {
        response.reset();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setCharacterEncoding(Constant.UTF_8_KEY);
        response.setContentType(Constant.JSON_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        ResultModel resultModel = ResultModel.failure(ErrorEnum.USER_DISABLED.getCode() + "", ErrorEnum.USER_DISABLED.getMessage());
        out.print(JsonUtils.objectToJson(resultModel));
        out.flush();
        response.flushBuffer();
    }

    /**
     * 未授权访问
     *
     * @param response
     * @throws IOException
     */
    private void setNoPermissionResponse(HttpServletResponse response) throws IOException {
        response.reset();
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With,content-type,token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setCharacterEncoding(Constant.UTF_8_KEY);
        response.setContentType(Constant.JSON_CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        PrintWriter out = response.getWriter();
        ResultModel resultModel = ResultModel.failure(ErrorEnum.NO_PERMISSION.getCode() + "", ErrorEnum.NO_PERMISSION.getMessage());
        out.print(JsonUtils.objectToJson(resultModel));
        out.flush();
        response.flushBuffer();
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        NotLoginRequired methodAnnotation = method.getAnnotation(NotLoginRequired.class);
        // 没有@NotLoginRequired 注解，需要认证
        if (methodAnnotation == null) {
            try {
                // 执行认证
                String token = request.getHeader(Constant.TOKEN_KEY);
                if (token == null) {
                    resetUnauthorized(response);
                    return false;
                }
                UserInfo userInfo = userService.getUserInfoByToken(token);
                if (userInfo == null) {
                    resetUnauthorized(response);
                    return false;
                }
                if (userInfo.getStatus() == UserStatusEnum.DISABLED.getType()) {
                    setForbiddenResponse(response);
                    return false;
                }
                DataPermModel dataPerm = new DataPermModel();
                dataPerm.setDeptPaths(new ArrayList<>());
                dataPerm.setUserId(userInfo.getId());
                dataPerm.setPermType(0);
                PermissionRequired permissionRequired = method.getAnnotation(PermissionRequired.class);
                if (permissionRequired != null && !msConfig.getLogin().getAdminName().equals(userInfo.getRealName())) {
                    //注解中权限按逗号分隔，只要当前用户有其中一个权限即可通过验证
                    String[] perms = permissionRequired.perms().split(",");
                    boolean hasPerm = false;
                    for (String perm : perms) {
                        if (userInfo.getPermDataMap().containsKey(perm)) {
                            hasPerm = true;
                            MenuPermModel menuPermModel = userInfo.getPermDataMap().get(perm);
                            Integer dType = menuPermModel.getDataType();
                            if (menuPermModel.getDeptFullPaths()!=null){
                                dataPerm.getDeptPaths().addAll(menuPermModel.getDeptFullPaths());
                            }
                            if (dType!=null && dType > dataPerm.getPermType()) {
                                dataPerm.setPermType(dType);
                            }
                        }
                    }
                    if (!hasPerm) {
                        setNoPermissionResponse(response);
                        return false;
                    }
                }
                // 设置当前数据权限对象
                request.setAttribute(Constant.DATA_PERMISSION_KEY,dataPerm);
                request.setAttribute(Constant.USER_SESSION_KEY, userInfo);
            } catch (Exception ex) {
                resetUnauthorized(response);
                logger.error("认证错误", ex);
                return false;
            }
            return true;
        }
        return true;
    }
}