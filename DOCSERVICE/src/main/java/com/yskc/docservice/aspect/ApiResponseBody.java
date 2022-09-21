package com.yskc.docservice.aspect;

import com.yskc.common.model.ResultModel;
import com.yskc.common.utils.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @BelongsProject: es-web
 * @BelongsPackage: com.yskc.esweb.aspect
 * @Author: zhangdingfu
 * @CreateTime: 2021-09-16 09:09
 * @Description: 返回对象封装
 */
@RestControllerAdvice
public class ApiResponseBody implements ResponseBodyAdvice<Object> {
    private static final String UI_CONFIGURATION = "uiConfiguration";
    private static final String SWAGGER_RESOURCES = "swaggerResources";
    private static final String GET_DOCUMENTATION = "getDocumentation";
    private static final String GET_ACTUATOR = "links";
    private static final String HANDLE = "handle";
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        String name = methodParameter.getMethod().getName();
        switch (name) {
            case UI_CONFIGURATION:
            case SWAGGER_RESOURCES:
            case GET_DOCUMENTATION:
            case GET_ACTUATOR:
            case HANDLE:
                return false;
            default:
                return true;
        }
    }

    @Override
    public Object beforeBodyWrite(Object res, MethodParameter methodParameter, MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if(res != null && res.getClass() == ResultModel.class){
            return res;
        }
        ResultModel result = ResultModel.setToSuccess(res);
        if(methodParameter.getParameterType() == String.class){
            return JsonUtils.objectToJson(result);
        }
        return result;
    }
}
