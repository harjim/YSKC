package com.xxl.job.executor.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.Page;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.executor.entity.ms.PatentDataEntity;
import com.xxl.job.executor.models.patent.PatentParamsModel;
import com.xxl.job.executor.models.patent.PatentStarReflectModel;
import com.yskc.common.utils.JsonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-22 13:52
 * @Description: patentStar
 */
public class PatentStarUtils extends PatentUtils {

    private final static String PATENT_STAR_LOGIN_URL = "http://www.patentstar.cn/frmLogin.aspx";

    private final static String PATENT_STAR_SEARCH = "http://www.patentstar.cn/my/SmartQuery.aspx/DoPatSearch";

    private final static String PATENT_STAR_PAGE_URL = "http://www.patentstar.cn/comm/GetList.aspx/GetPageList";

    private final static String FLZT_URL = "http://www.patentstar.cn/my/frmFLZT.aspx?AppNo={0}";

    private final static String D = "d";

    private final static String ROWS = "rows";

    private final static String DATA = "data";

    private final static String DATALIST = "dataList";

    private final static int nodeIdIndex = 1;
    private final static int itemCountIndex = 2;
    private final static String VALID_SUF = "1.jpg";
    private final static String VERIFY_SUF = "2.jpg";
    private final static String NOT_VALID_SUF = "3.jpg";
    private final static Integer VALID_SLEEP_TIME = 3 * 1000;

    private PatentParamsModel paramsModel;

    private Date maxPublicDate;

    private Boolean restart = false;

    private Boolean finish = false;


    public Boolean getRestart() {
        return restart;
    }

    public void setRestart(Boolean restart) {
        this.restart = restart;
    }

    public boolean finish() {
        return finish;
    }

    public PatentStarUtils(Date maxDate, String queryWord) {
        super(queryWord);
        finish = false;
        restart = false;
        if (maxPublicDate == null) {
            this.maxPublicDate = new Date(-1);
        } else {
            this.maxPublicDate = maxDate;
        }
        valid();
    }

    public void valid() {
        try {
            // getPage(PATENT_STAR_LOGIN_URL, HttpMethod.GET, null,null, null);
            getPage(PATENT_STAR_LOGIN_URL, HttpMethod.POST, PatentParamsModel.LOGIN_HEADER, null, PatentParamsModel.getLogParams());
        } catch (Exception e) {
            XxlJobLogger.log(e);
        }
    }

    public Integer loadItemCount() {
        String content = null;
        Page page;
        try {
            paramsModel = new PatentParamsModel(queryWord);
            page = getPage(PATENT_STAR_SEARCH, HttpMethod.POST, PatentParamsModel.QUERY_HEADER, null, paramsModel.getSearchParams());
            content = page.getWebResponse().getContentAsString();
            List<String> list = (ArrayList) JsonUtils.jsonToPojo(content, Map.class).get(D);
            paramsModel.initParams(list.get(nodeIdIndex), list.get(itemCountIndex));
            return Integer.valueOf(list.get(itemCountIndex));
        } catch (Exception e) {
            XxlJobLogger.log(content);
            XxlJobLogger.log(e);
        }
        return 0;
    }

    public void getNextPage(Integer pageNum) {
        try {
            // 沉睡3--15秒
            Thread.sleep(RandomUtil.randomInt(10, 30) * 1000);
            Page page = getPage(PATENT_STAR_PAGE_URL, HttpMethod.POST, PatentParamsModel.QUERY_HEADER, null, paramsModel.nextPageParams(pageNum));
            Map map = JsonUtils.jsonToPojo(page.getWebResponse().getContentAsString(), Map.class);
            List<Map<String, String>> data = CollectionUtil.isEmpty(map) ? null : (ArrayList) JsonUtils.jsonToPojo(map.get(D).toString(), Map.class).get(ROWS);
            if (CollectionUtil.isEmpty(data)) {
                restart = true;
                return;
            }
            for (Map m : data) {
                PatentStarReflectModel model = JsonUtils.mapToPojo(m, PatentStarReflectModel.class);
//                Thread.sleep(VALID_SLEEP_TIME);
//                Page flzt = getPage(MessageFormat.format(FLZT_URL, model.getStrANX()), HttpMethod.GET, null, null, null);
//                String flztStr = flzt.getWebResponse().getWebRequest().getUrl().toString();
//                if (flztStr.contains(VALID_SUF)) {
//                } else if (flztStr.contains(VERIFY_SUF)) {
//                    model.setCaseStatus("审中");
//                } else if (flztStr.contains(NOT_VALID_SUF)) {
//                    model.setCaseStatus("失效");
//                }
                model.setCaseStatus("");
                model.setQueryWord(queryWord);
                PatentDataEntity entity = model.reflectEntity();
                if (entity.getPublicDate().compareTo(maxPublicDate) <= 0) {
                    finish = true;
                    return;
                }
                dataList.add(entity);
            }
        } catch (Exception e) {
            XxlJobLogger.log("当前页：" + pageNum);
            XxlJobLogger.log(e);
        }
    }
}
