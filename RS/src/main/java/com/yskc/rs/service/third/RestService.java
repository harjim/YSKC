package com.yskc.rs.service.third;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.ResultModel;
import com.yskc.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 接口请求服务
 *
 * @author hguronghua
 */
@Service
public class RestService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * LoadBalancedRest post请求
     *
     * @param url     请求地址
     * @param model   请求参数
     * @param typeRef 返回参数类型
     * @param <T>     返回参数
     * @return
     * @throws RestClientException
     */
    public <T> T post(String url, Object model, TypeReference typeRef) throws OwnerException {
        //请求头
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        //请求体
        HttpEntity httpEntity = new HttpEntity<>(model, headers);
        String result = restTemplate.postForObject(url, httpEntity, String.class);
        ResultModel resultModel=JsonUtils.jsonToPojo(result,ResultModel.class);
        if(resultModel.getSuccess()){
            return JsonUtils.jsonToObject(result,typeRef);
        }else{
            throw new OwnerException(resultModel.getErrorMessage());
        }
    }

    /**
     *
     * @param url
     * @param model
     * @param tClass
     * @param <T>
     * @return
     * @throws OwnerException
     */
    public <T> T post(String url, Object model, Class<T> tClass) throws OwnerException {
        //请求头
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        //请求体
        HttpEntity httpEntity = new HttpEntity<>(model, headers);
        String result = restTemplate.postForObject(url, httpEntity, String.class);
        return JsonUtils.jsonToPojo(result,tClass);
    }
    /**
     * LoadBalancedRest get请求
     *
     * @param url
     * @param typeRef
     * @param uriVariables
     * @param <T>
     * @return
     * @throws RestClientException
     */
    public <T> ResponseEntity<T> get(String url, ParameterizedTypeReference<T> typeRef, Object... uriVariables) throws RestClientException {
        //请求头
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), typeRef, uriVariables);
    }
}
