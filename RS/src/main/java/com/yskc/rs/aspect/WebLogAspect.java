package com.yskc.rs.aspect;

import javax.servlet.http.HttpServletRequest;

import com.yskc.rs.config.Constant;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javassist.NotFoundException;

/**
 * 日志记录
 *
 * @author huronghua
 */
@Aspect
@Component
public class WebLogAspect {
    private static Logger log = LoggerFactory.getLogger(WebLogAspect.class);

    /**
     * logPointCut
     */
    @Pointcut("!execution(public * com.yskc.rs.controller..*.initBinder(..)) && execution(public * com.yskc.rs.controller..*.*(..))")
    public void logPointCut() {
    }

    /**
     * doBefore
     *
     * @param joinPoint
     */
    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) throws NotFoundException, ClassNotFoundException {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(Constant.TOKEN_KEY);
        Object[] params = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        log.info((StringUtils.isEmpty(token) ? "" : "token:" + token + ", ") + " 请求地址 : "
                + request.getRequestURL().toString());
    }

    /**
     * doAfterReturning
     *
     * @param ret
     */
    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) {
        // 处理完请求，返回内容
        // log.info("返回值 : " + JsonUtils.objectToJson(ret));
    }

}