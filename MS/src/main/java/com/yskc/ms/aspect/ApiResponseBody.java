package com.yskc.ms.aspect;

import com.yskc.common.model.ResultModel;
import com.yskc.common.utils.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 自定义封装接口返回
 * 正确返回在此定义
 *
 * @author huronghua
 */
@ControllerAdvice
public class ApiResponseBody implements ResponseBodyAdvice<Object> {
    private static final String UI_CONFIGURATION = "uiConfiguration";
    private static final String SWAGGER_RESOURCES = "swaggerResources";
    private static final String GET_DOCUMENTATION = "getDocumentation";
    private static final String GET_ACTUATOR = "links";
    private static final String HANDLE = "handle";

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        String methodName = methodParameter.getMethod().getName();
        switch (methodName) {
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

    /**
     * 包装返回的响应
     */
    @Override
    public Object beforeBodyWrite(Object res, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> converterType,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

//        if (resObj == null) {
//            return new ResultModel<>(true);
//        }
//        if (resObj instanceof ResultModel || resObj instanceof UiConfiguration) {
//            return resObj;
//        }
//        ResultModel resultModel = ResultModel.setToSuccess(resObj);
//        if (resObj instanceof String) {
//            return JsonUtils.objectToJson(resultModel);
//        }
//        return resultModel;
        ResultModel result;
        if (res != null && res.getClass() == ResultModel.class) {
            result = (ResultModel) res;
        } else {
            result = ResultModel.setToSuccess(res);
        }
        if (String.class == methodParameter.getParameterType()) {
            return JsonUtils.objectToJson(result);
        }
        return result;
    }

}
