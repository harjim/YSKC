package com.xxl.job.executor.utils;

import cn.hutool.core.date.DateUtil;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-21 16:26
 * @Description: webclient
 */
public class WebClientUtils {
    /**
     * js超时 3分钟
     */
    private final static Integer JS_TIMEOUT = 30 * 1000;
    /**
     * js后台运行时间 60 秒
     */
    private final static Integer JS_RUN_BACKGROUND = 30 * 1000;
    private final static Integer HISTORY_SIZE = 30;
    private final static Integer MAX_MEMORY = 256 * 1024;

    private Date prevProxyDate = new Date();
    private int FIVE_MIN = 5 * 60 * 1000;

    private WebClient webClient;

    WebClient getWebClient() {
        return webClient;
    }

    int next = 0;

    static List<ProxyConfig> proxyList = new ArrayList<>();

    static {
        // proxyList.add(new ProxyConfig("106.12.80.178", 8888));
        //  proxyList.add(new ProxyConfig("127.0.0.1", 8888));
    }

    public WebClientUtils() {
        this(true);
    }

    public WebClientUtils(Boolean noJs) {
        webClient = new WebClient(BrowserVersion.CHROME);
        // webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8888));
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(noJs);
        webClient.setJavaScriptTimeout(JS_TIMEOUT);
        webClient.waitForBackgroundJavaScript(JS_RUN_BACKGROUND);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setDoNotTrackEnabled(true);
        webClient.getOptions().setTimeout(JS_TIMEOUT);
        webClient.getOptions().setMaxInMemory(MAX_MEMORY);
        webClient.getOptions().setHistorySizeLimit(HISTORY_SIZE);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getCache().setMaxSize(128);
    }

    void nextProxy() throws Exception {
        Date now = new Date();
        if (DateUtil.betweenMs(now, prevProxyDate) < FIVE_MIN) {
            throw new Exception("重连间隔太小。");
        }
        next++;
        if (next >= proxyList.size()) {
            next = 0;
        }
        webClient.getOptions().setProxyConfig(proxyList.get(next));
    }

    Page getPage(String url, HttpMethod method, Map<String, String> header, List<NameValuePair> params, String requestBody) throws Exception {
        WebRequest request = new WebRequest(new URL(url), method);
        if (!CollectionUtils.isEmpty(header)) {
            request.setAdditionalHeaders(header);
        }
        if (!CollectionUtils.isEmpty(params)) {
            request.setRequestParameters(params);
        }
        if (!StringUtils.isEmpty(requestBody)) {
            request.setRequestBody(requestBody);
        }
        return getWebClient().getPage(request);
    }
}
