package com.yskc.rs.aspect;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.model.SystemLogModel;
import com.yskc.common.utils.IpUtils;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.entity.SysLog;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.service.SysLogService;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 系统操作日志拦截记录
 *
 * @author huronghua
 */
@Aspect
@Component
@SuppressWarnings("all")
public class SystemLogAspect {

    //本地异常日志记录对象
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogAspect.class);
    private String messageFormat = "[{6}]  [请求地址]{0} -- [公司]:{1},[用户]:{2},[类型]:{3},[Ip]:{4},[请求参数]:{5}";
    @Autowired
    private SysLogService sysLogService;

    //Service层切点
    @Pointcut("@annotation(com.yskc.common.annotation.SystemLog)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//            String token = request.getHeader(Constant.TOKEN_KEY);
//            String companyId = request.getHeader(Constant.COMPANYID_KEY);
            UserInfo userInfo = (UserInfo) request.getAttribute(Constant.USER_SESSION_KEY);
            if (userInfo == null) {
                return;
            }
            SystemLogModel systemLogModel = getSystemLogArgs(joinPoint);
            SysLog sysLog = getSysLog(joinPoint, userInfo, request, systemLogModel.getDescription());
            if (((systemLogModel.getMode() & SystemLog.SAVE_DB) == SystemLog.SAVE_DB)) {
                sysLogService.save(sysLog);
            }

            LOGGER.info(MessageFormat.format(messageFormat, sysLog.getLogUrl(),
                    userInfo == null ? "-" : userInfo.getCompanyId(),
                    sysLog.getUserId(), sysLog.getSource(), sysLog.getRequestIp(), sysLog.getLogParams(), sysLog.getDescription()));
        } catch (Exception e) {
            //捕获异常不操作
        }
    }

    @After("controllerAspect()")
    public void doAfter(JoinPoint joinPoint) {
        try {
            SystemLogModel systemLogModel = getSystemLogArgs(joinPoint);
            if ((systemLogModel.getMode() & SystemLog.AFTER) == SystemLog.AFTER) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//                String token = request.getHeader(Constant.TOKEN_KEY);
//                String companyId = request.getHeader(Constant.COMPANYID_KEY);
                UserInfo userInfo = (UserInfo) request.getAttribute(Constant.USER_SESSION_KEY);
                if (userInfo == null) {
                    return;
                }
                SysLog sysLog = getSysLog(joinPoint, userInfo, request, systemLogModel.getDescription());
                if (((systemLogModel.getMode() & SystemLog.SAVE_DB) == SystemLog.SAVE_DB)) {
                    sysLogService.save(sysLog);
                }
                LOGGER.info(MessageFormat.format(messageFormat, sysLog.getLogUrl(),
                        userInfo == null ? "-" : userInfo.getCompanyId(),
                        sysLog.getUserId(), sysLog.getSource(), sysLog.getRequestIp(), sysLog.getLogParams(), sysLog.getDescription()));
            }
        } catch (Exception e) {
            //捕获异常不操作
        }
    }

    /**
     * 获取日志对象
     *
     * @param joinPoint
     * @param userSession
     * @param request
     * @param desc
     * @return
     */
    private SysLog getSysLog(JoinPoint joinPoint, UserInfo userInfo, HttpServletRequest request, String desc) throws Exception {
        String methodName = joinPoint.getSignature().getName();
        String ip = IpUtils.getIpAddr(request);
        String params = getArguments(joinPoint, methodName, ip, request);
        String url = request.getRequestURI().toString();
        if (null == userInfo) {
            LOGGER.warn(MessageFormat.format("【{3}】获取UserSession会话失败，请求【{0}】,ip【{1}】,参数【{2}】",
                    url, ip, params, desc));
            throw new Exception();
        }
        return SysLog.build(userInfo.getId(), userInfo.getUserSource(), userInfo.getUserName(),
                userInfo.getRealName(), desc, url, params, ip, userInfo.getCompanyId(), userInfo.getCompanyName());
    }

    public static SystemLogModel getSystemLogArgs(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        int mode = 0;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemLog.class).description();
                    mode = method.getAnnotation(SystemLog.class).mode();
                    break;
                }
            }
        }
        return new SystemLogModel(description, mode);
    }

    public static String getArguments(JoinPoint joinPoint, String methodName, String ip, HttpServletRequest request) {
        try {
            Object[] params = joinPoint.getArgs();
            Class<?> clazz = joinPoint.getTarget().getClass();
            String clazzName = clazz.getName();
            Map<String, Object> nameAndArgs = getFieldsName(clazz, clazzName, methodName, params);
            StringBuffer bf = new StringBuffer();
            if (!CollectionUtils.isEmpty(nameAndArgs)) {
                Iterator it = nameAndArgs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object object = entry.getValue();
                    //  跳过上传文件
                    if (object instanceof MultipartFile || object instanceof MultipartFile[]) {
                        continue;
                    }
//                    if (entry.getValue() != null && "java.util.ArrayList".equals(entry.getValue().getClass().getName())) {
//                        List arrayList = (ArrayList) entry.getValue();
//                        List<Object> newList = new ArrayList<>();
//                        if (arrayList.size() > 0) {
//                            for (Object object : arrayList) {
//                                Map<String, Object> objectMap = BeanUtil.beanToMap(object, false, true);
//                                newList.add(objectMap);
//                            }
//                        }
//                        value = JsonUtils.objectToJsonIgnoreNull(newList);
//                    } else {
//
//                    }
                    bf.append((String) entry.getKey()).append("=");
                    bf.append(JsonUtils.objectToJsonIgnoreNull(object)).append("&");
                }
                if (bf.length() > 1) {
                    bf.replace(bf.length() - 1, bf.length(), "");
                }
            }
            if (StringUtils.isEmpty(bf.toString())) {
                bf.append(request.getQueryString());
            }
            return bf.toString();
        } catch (NotFoundException e) {
            LOGGER.error("getArguments", e);
        }
        return "";
    }

    private static Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);
        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            return map;
        }
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);
        }
        return map;
    }
}
