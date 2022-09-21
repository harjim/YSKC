package com.xxl.job.executor.utils;

import cn.hutool.core.util.RandomUtil;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.models.patent.PatentParamsModel;
import com.xxl.job.executor.models.patent.SiPopReflectModel;
import com.yskc.common.utils.JsonUtils;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-22 15:10
 * @Description: sipop
 */
public class SiPopUtils extends PatentUtils {

    private final static String SI_POP_URL = "http://www.sipop.cn/patent-interface-search/caixunController/queryByKeyword?dz={0}&dac=US%2CJP%2CKR%2CEP%2CGB%2CFR%2CRU%2CELSE%2CCN&f-kind=01%2C02%2C03&pageSize={1,number,#}&pageNum={2,number,#}&sortField=pd&sortType=asc";

    private final static String DATE = "data";

    private final static String TOTAL = "total";

    private final static String DATA_LIST = "dataList";


    public SiPopUtils(String queryWord) {
        super(queryWord);
    }

    public Integer getPageSize() {
        try {
            Page page = getPage(MessageFormat.format(SI_POP_URL, URLEncoder.encode(queryWord, "utf-8"), 5, 1), HttpMethod.GET, PatentParamsModel.SIPOP_HEADER, null, null);
            Map map = JsonUtils.jsonToPojo(page.getWebResponse().getContentAsString(), Map.class);
            if (!CollectionUtils.isEmpty(map)) {
                Integer rows = Integer.valueOf(((Map) map.get(DATE)).get(TOTAL).toString());
                if (rows % 20 == 0) {
                    return rows / 20;
                }
                return (rows / 20) + 1;
            }
        } catch (Exception e) {
            XxlJobLogger.log(e);
        }
        return 0;
    }

    public void getNextPage(Integer pageNum) throws Exception {
        try {
            Thread.sleep(RandomUtil.randomInt(10, 25) * 1000);
            Page page = getPage(MessageFormat.format(SI_POP_URL, URLEncoder.encode(queryWord, "utf-8"), 100, pageNum), HttpMethod.GET, PatentParamsModel.SIPOP_HEADER, null, null);
            Map map = JsonUtils.jsonToPojo(page.getWebResponse().getContentAsString(), Map.class);
            List<Map<String, Object>> list = (List<Map<String, Object>>) ((Map) map.get(DATE)).get(DATA_LIST);
            for (Map m : list) {
                try {
                    SiPopReflectModel siPopReflectModel = JsonUtils.mapToPojo(m, SiPopReflectModel.class);
                    siPopReflectModel.setQueryWord(queryWord);
                    dataList.add(siPopReflectModel.reflectEntity());
                } catch (Exception e) {
                    XxlJobLogger.log(e);
                }
            }
        } catch (Exception e) {
            XxlJobLogger.log(e);
            throw new Exception(e);
        }
    }
}
