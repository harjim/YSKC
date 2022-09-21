package com.xxl.job.executor.utils;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.RandomUtil;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.models.patent.BaiTenParamsModel;
import com.xxl.job.executor.models.patent.BaiTenReflectModel;
import com.xxl.job.executor.models.patent.PatentParamsModel;
import com.yskc.common.utils.JsonUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-19 10:42
 * @Description: 佰腾util
 */
public class BaiTenUtils extends PatentUtils {

    private static String TOKEN = "";

    private final static String QUERY_URL = "https://www.baiten.cn/results/s/aa:{0}/.html?type=s#/100/{1,number,#}";
    private final static String PAGE_URL = "https://www.baiten.cn/results/list/{0}";
    private final static String QUERY_FORMAT = "({0}) AND (ad:[{1} TO {2}])";
    private final static String CUBE_PATENT_SEARCH_RESPONSE = "cubePatentSearchResponse";
    private final static String DOCUMENTS = "documents";
    private final static String FILED_VALUES = "field_values";

    public BaiTenUtils(String queryWord) {
        super(queryWord, false);
    }

    public Integer getPageNum(Integer currentPage, String begin, String end) {
        Integer pageNum = 0;
        try {

            HtmlPage htmlPage = (HtmlPage) getPage(MessageFormat.format(QUERY_URL, MessageFormat.format(QUERY_FORMAT,
                    URLEncoder.encode(URLEncoder.encode(queryWord, "utf-8"), "utf-8"), begin, end), currentPage),
                    HttpMethod.GET, null, null, null);
            String totalStr = htmlPage.getByXPath("//div[@class='pl-dn']/div/em[2]/text()").get(0).toString();
            String contentStr = htmlPage.getWebResponse().getContentAsString();
            contentStr = contentStr.substring(contentStr.indexOf("window.token = '") + 16);
            TOKEN = Base64Encoder.encode(contentStr.substring(0, contentStr.indexOf("'")));
            if (!StringUtils.isEmpty(totalStr)) {
                Integer p = Integer.valueOf(totalStr);
                if (p % 100 == 0) {
                    pageNum = p / 100;
                } else {
                    pageNum = (p / 100) + 1;
                }
            }
        } catch (Exception e) {
            XxlJobLogger.log(e);
        }
        return pageNum > 20 ? 20 : pageNum;
    }


    public Date nextRangeDate(Integer pageNum, String begin, String end) throws Exception {
        try {
            String query = MessageFormat.format(QUERY_FORMAT, queryWord, begin, end);
            // 沉睡25--55秒
            Thread.sleep(RandomUtil.randomInt(5, 20) * 1000);
            getPageNum(pageNum, begin, end);
            Page page = getPage(MessageFormat.format(PAGE_URL, TOKEN), HttpMethod.POST, PatentParamsModel.QUERY_HEADER, BaiTenParamsModel.baiTenNextPage(query, pageNum), null);
            String content = page.getWebResponse().getContentAsString();
            if ("5".equals(content.trim())) {
                nextProxy();
                nextRangeDate(pageNum, begin, end);
            }
            Map<String, Object> map = JsonUtils.jsonToPojo(content, Map.class);
            try {
                List<Map> data = (ArrayList) ((Map) map.get(CUBE_PATENT_SEARCH_RESPONSE)).get(DOCUMENTS);
                for (Map datum : data) {

                    dataList.add(BaiTenReflectModel.mapToModelAndReflect((Map) datum.get(FILED_VALUES), queryWord));
                }
            } catch (Exception e) {
                XxlJobLogger.log(e);
            }
        } catch (
                Exception e) {
            XxlJobLogger.log("当前页失败页：" + pageNum);
            XxlJobLogger.log(e);
            throw new Exception(e);
        }
        if (CollectionUtils.isEmpty(dataList)) {
            return null;
        }
        return dataList.get(dataList.size() - 1).getApplyDateTime();
    }
}
