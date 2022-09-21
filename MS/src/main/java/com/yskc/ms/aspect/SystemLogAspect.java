package com.yskc.ms.aspect;

import com.yskc.common.annotation.SystemLog;
import com.yskc.common.model.SystemLogModel;
import com.yskc.common.utils.IpUtils;
import com.yskc.common.utils.JsonUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.entity.ms.SysLog;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.ms.SysLogService;
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
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.aspect
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-13 13:36
 * @Description: 日志切面
 */
@Aspect
@Component
@SuppressWarnings("all")
public class SystemLogAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemLogAspect.class);
    private String messageFormat = "[{4}]  [请求地址]{0} -- [用户]:{1},[Ip]:{2},[请求参数]:{3}";
    @Autowired
    private SysLogService sysLogService;

    @Pointcut("@annotation(com.yskc.common.annotation.SystemLog)")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            SystemLogModel systemLogModel = getSystemLogArgs(joinPoint);
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(Constant.TOKEN_KEY);
            UserInfo userInfo = (UserInfo) request.getAttribute(Constant.USER_SESSION_KEY);
            SysLog sysLog = getSysLog(joinPoint, userInfo, request, systemLogModel.getDescription());
            if (((systemLogModel.getMode() & SystemLog.SAVE_DB) == SystemLog.SAVE_DB)) {
                sysLogService.save(sysLog);
            }
            LOGGER.info(MessageFormat.format(messageFormat, sysLog.getLogUrl(),
                    sysLog.getUserId(), sysLog.getRequestIp(), sysLog.getLogParams(), sysLog.getDescription()));
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
                String token = request.getHeader(Constant.TOKEN_KEY);
                UserInfo userInfo = (UserInfo) request.getAttribute(Constant.USER_SESSION_KEY);
                SysLog sysLog = getSysLog(joinPoint, userInfo, request, systemLogModel.getDescription());
                if (((systemLogModel.getMode() & SystemLog.SAVE_DB) == SystemLog.SAVE_DB)) {
                    sysLogService.save(sysLog);
                }
                LOGGER.info(MessageFormat.format(messageFormat, sysLog.getLogUrl(),
                        sysLog.getUserId(), sysLog.getRequestIp(), sysLog.getLogParams(), sysLog.getDescription()));
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
        return SysLog.build(userInfo.getId(), userInfo.getUserName(),
                userInfo.getRealName(), desc, url, params, ip);
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
            String className = joinPoint.getTarget().getClass().getName();
            Object[] params = joinPoint.getArgs();
            String classType = joinPoint.getTarget().getClass().getName();
            Class<?> clazz = null;
            clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            Map<String, Object> nameAndArgs = getFieldsName(clazz, clazzName, methodName, params);
            StringBuffer bf = new StringBuffer();
            if (!CollectionUtils.isEmpty(nameAndArgs)) {
                Iterator it = nameAndArgs.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    Object object = entry.getValue();
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
        } catch (ClassNotFoundException e) {
            LOGGER.error("getArguments", e);
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
