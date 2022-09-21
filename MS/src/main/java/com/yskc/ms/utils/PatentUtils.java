package com.yskc.ms.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.IoUtil;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.ms.entity.rs.RsPatentApplyEntity;
import com.yskc.ms.entity.rs.RsPatentCostEntity;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.enums.PatentStatusEnum;
import com.yskc.ms.models.patent.PatentConfig;
import com.yskc.ms.models.patent.PatentUnionModel;
import com.yskc.ms.models.patent.PatentValidParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.utils
 * @Author: zhangdingfu
 * @CreateTime: 2020-06-29 11:18
 * @Description: 专利util类
 */
@Component
public class PatentUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private WebClient webClient;

    /**
     * 申请日期
     */
    private final static String EL_APPLY_DATE = "record_zlx:shenqingr";

    /**
     * 分类号
     */
    private final static String EL_MAIN_TYPE_NO = "record_zlx:zhufenlh";
    /**
     * 分案提交日
     */
    private final static String EL_CASE_SUBMISSION_DATE = "record_zlx:fenantjr";
    /**
     * 案件状态
     */
    private final static String EL_CASE_STATUS = "record_zlx:anjianywzt";
    /**
     * 发明人
     */
    private final static String EL_INVENTOR = "record_fmr:famingrxm";
    /**
     * 公司
     */
    private final static String EL_COMPANY_NAME = "record_sqr:shenqingrxm";
    /**
     * 地址
     */
    private final static String EL_COMPANY_ADDRESS = "record_sqr:shenqingrdz";
    /**
     * 未缴费用类型
     */
    private final static String EL_IS_COST_TYPE = "record_yingjiaof:yingjiaofydm";
    /**
     * 未缴缴费金额
     */
    private final static String EL_IS_AMOUNT = "record_yingjiaof:shijiyjje";
    /**
     * 未缴缴费期限
     */
    private final static String EL_IS_LIMIT_DATE = "record_yingjiaof:jiaofeijzr";
    /**
     * 未缴费状态
     */
    private final static String EL_IS_STATUS = "record_yingjiaof:feiyongzt";

    /**
     * 已缴费
     */
    private final static String EL_STATUS_DEFAULT = "已缴费";

    /**
     * 已缴费用类型
     */
    private final static String EL_COST_TYPE = "record_yijiaof:feiyongzldm";
    /**
     * 已缴缴费金额
     */
    private final static String EL_AMOUNT = "record_yijiaof:jiaofeije";
    /**
     * 缴费日期
     */
    private final static String EL_PAY_DATE = "record_yijiaof:jiaofeisj";
    /**
     * 收据号
     */
    private final static String EL_RECEIPT_NO = "record_yijiaof:shoujuh";
    /**
     * 缴费人
     */
    private final static String EL_PAYER = "record_yijiaof:jiaofeirxm";


    private final static String TITLE = "title";

    private final static String VALUE = "value";

    private final static NameValuePair USER_TYPE_2 = new NameValuePair("usertype", "2");
    /**
     * 验证码，消息
     */
    private final static String URL_VALID_MESSAGE = "http://cpquery.cnipa.gov.cn/JcaptchaServlet";
    /**
     * 验证请求
     */
    private final static String URL_LOGIN_SERVLET = "http://cpquery.cnipa.gov.cn/LoginServlet";
    /**
     * 登录请求
     */
    private final static String URL_LOGIN = "http://cpquery.cnipa.gov.cn/txn999999.ajax";
    /**
     * 改变语种
     */
    private final static String URL_LANG_CHANGE = "http://cpquery.cnipa.gov.cn/txnLangChange.ajax";
    /**
     * 查询首页： 获取token
     * "http://cpquery.cnipa.gov.cn/txnQueryOrdinaryPatents.do"
     */
    private final static String URL_QUERY_PATENT = "http://cpquery.cnipa.gov.cn/txnPantentInfoList.do";
    /**
     * 专利信息
     */
    private final static String URL_QUERY_INFO = "http://cpquery.cnipa.gov.cn/txnQueryBibliographicData.do";
    /**
     * 专利费用
     */
    private final static String URL_QUERY_COST = "http://cpquery.cnipa.gov.cn/txnQueryFeeData.do";

    /**
     * js超时 3分钟
     */
    private final static Integer JS_TIMEOUT = 3 * 60 * 1000;
    /**
     * js后台运行时间 60 秒
     */
    private final static Integer JS_RUN_BACKGROUND = 60 * 1000;
    /**
     * 缓存大小
     */
    private final static Integer CACHE_SIZE = 128;

    private final static int NO_CONDITION_CODE = 412;
    private final static int MAX_RETRY = 3;
    private final static int MIN_RETRY = 1;
    private String token;


    public PatentUtils() {
        webClient = new WebClient(BrowserVersion.FIREFOX_68);
        //webClient.getOptions().setProxyConfig(new ProxyConfig("127.0.0.1", 8888));
        webClient.setJavaScriptTimeout(JS_TIMEOUT);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setDoNotTrackEnabled(true);
        webClient.waitForBackgroundJavaScript(JS_RUN_BACKGROUND);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setTimeout(JS_TIMEOUT);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.getCache().setMaxSize(CACHE_SIZE);
    }

    private Page getPage(String url, HttpMethod method, Map<String, String> header, List<NameValuePair> params) throws Exception {
        return getPage(url, method, header, params, MIN_RETRY);
    }

    private Page getPage(String url, HttpMethod method, Map<String, String> header, List<NameValuePair> params, int minRetry) throws Exception {
        WebRequest request = new WebRequest(new URL(url), method);
        if (!CollectionUtils.isEmpty(header)) {
            request.setAdditionalHeaders(header);
        }
        if (!CollectionUtils.isEmpty(params)) {
            request.setRequestParameters(params);
        }
        Page page = webClient.getPage(request);
        if (minRetry <= MAX_RETRY && page.getWebResponse().getStatusCode() == NO_CONDITION_CODE) {
            page = getPage(url, method, header, params, minRetry + 1);
        }
        return page;
    }

    /**
     * 设置操作语言 zh
     *
     * @throws Exception
     */
//    private void setChLanguage() throws Exception {
//        getPage(URL_LANG_CHANGE,
//                HttpMethod.POST, msConfig.getPatentConfig().getLoginHeader(),
//                CollUtil.newArrayList(new NameValuePair("l", msConfig.getPatentConfig().getLanguage())));
//    }
    public Map<String, String> getValid() throws Exception {
        clearCookies();
        List<NameValuePair> picParams = new ArrayList<>();
        picParams.add(new NameValuePair("type", "1"));
        picParams.add(USER_TYPE_2);
        Map<String, String> result = new HashMap<>(2);
        getPage(URL_VALID_MESSAGE, HttpMethod.GET,
                PatentConfig.BASE_HEADER, picParams, MAX_RETRY);
        Page imgPage = getPage(URL_VALID_MESSAGE, HttpMethod.GET,
                PatentConfig.BASE_HEADER, picParams);
        Page wordPage = getPage(URL_VALID_MESSAGE, HttpMethod.GET,
                PatentConfig.BASE_HEADER, CollUtil.newArrayList(USER_TYPE_2));
        InputStream imgIs = imgPage.getWebResponse().getContentAsStream();
        FastByteArrayOutputStream img = IoUtil.read(imgIs);
        byte[] imgArr = img.toByteArray();
        wordPage.getWebResponse().getStatusCode();
        InputStream wordIs = wordPage.getWebResponse().getContentAsStream();
        FastByteArrayOutputStream word = IoUtil.read(wordIs);
        result.put("validPic", Base64.encode(imgArr));
        result.put("validMessage", word.toString());
        return result;
    }


    public Boolean setValid(PatentValidParam params) throws Exception {
        if (StringUtils.isEmpty(params.getUsername()) || StringUtils.isEmpty(params.getOwnerPassword())) {
            throw new OwnerException("未获取任何账号，请先选择账号。");
        }
        List<NameValuePair> param = params.getParams();
        param.add(USER_TYPE_2);
        Page page = getPage(URL_LOGIN_SERVLET, HttpMethod.POST,
                PatentConfig.BASE_HEADER, param);
        String result = page.getWebResponse().getContentAsString();
        if (Boolean.FALSE.toString().equals(result)) {
            logger.error("专利网站登录失败。");
            throw new OwnerException("验证失败，请重新验证!");
        }
        if (Boolean.TRUE.toString().equals(result)) {
            // 登录参数，默认
            List<NameValuePair> loginParams = PatentConfig.getLoginParams(Base64.encode(params.getUsername()), Base64.encode(params.getOwnerPassword()));
            patentLogin(loginParams);
            return true;
        }
        logger.error("专利网站登录失败。");
        throw new OwnerException("登录失败，请重试!");
    }


    public void patentLogin(List<NameValuePair> loginParams) throws Exception {
        Page p = getPage(URL_LOGIN, HttpMethod.POST,
                PatentConfig.LOGIN_HEADER, loginParams, MAX_RETRY + 1);
        if (p.getWebResponse().getStatusCode() == NO_CONDITION_CODE) {
            p = getPage(URL_LOGIN, HttpMethod.GET,
                    PatentConfig.LOGIN_HEADER, loginParams, MAX_RETRY + 1);
            getPage(URL_LOGIN, HttpMethod.POST,
                    PatentConfig.LOGIN_HEADER, loginParams, MAX_RETRY + 1);
        }
        String result = p.getWebResponse().getContentAsString();
        logger.info("专利登录信息：" + result);
        if (result.contains(PatentStatusEnum.WRONG_NAME.getValue()) || result.contains(PatentStatusEnum.BLZ000.getValue())) {
            throw new OwnerException("账户密码错误。");
        } else if (result.contains(PatentStatusEnum.OVER_TIME.getValue())) {
            throw new OwnerException("登录超时，请重试。");
        } else if (result.contains(PatentStatusEnum.TZ001.getValue())) {
            throw new OwnerException("账号密码过时，请前往专利网站修改密码后再试。");
        }
        validLogin();
    }

    /**
     * 验证是否登录
     *
     * @return
     * @throws Exception
     */
    public void validLogin() throws Exception {
        Page page;
        try {
            page = getPage(URL_QUERY_PATENT, HttpMethod.GET, null, null);
        } catch (Exception e) {
            logger.error("获取token失败", e);
            throw new OwnerException("登录失败，请重试!");
        }
        DomElement elToken = ((HtmlPage) page).getElementById("sq_token");
        logger.info("#####################################token:" + elToken);
        if (null == elToken || StringUtils.isEmpty(elToken.getAttribute(VALUE))) {
            logger.error("获取token失败");
            throw new OwnerException("登录失败，请重试!");
        }
        token = elToken.getAttribute(VALUE);
    }

    /**
     * 查询专利
     *
     * @throws Exception
     */
    public PatentUnionModel patentSearch(RsPatentEntity patent, Integer msUserId) throws Exception {
        Date now = new Date();
        List<NameValuePair> params;
        params = new ArrayList<>();
        params.add(new NameValuePair("select-key:shenqingh", patent.getPatentNo().replaceAll("ZL", "").replaceAll("\\.", "")));
        params.add(new NameValuePair("token", token));
        PatentUnionModel patentUnionModel = new PatentUnionModel();
        HtmlPage infoPage = (HtmlPage) getPage(URL_QUERY_INFO,
                HttpMethod.GET, PatentConfig.BASE_HEADER, params);
        setPatent(patent, infoPage, msUserId, patentUnionModel, now);
        params.add(new NameValuePair("select-key:gonggaobj", "0"));
        HtmlPage costPage = (HtmlPage) getPage(URL_QUERY_COST,
                HttpMethod.GET, PatentConfig.BASE_HEADER, params);
        setIsCostList(patent, costPage, patentUnionModel);
        setCostList(patent, costPage, patentUnionModel);
        return patentUnionModel;
    }

    /**
     * 设置专利信息，申请人
     *
     * @param currentPatent
     * @param infoPage
     * @param msUserId
     * @param patentUnionModel
     */
    private void setPatent(RsPatentEntity currentPatent, HtmlPage infoPage, Integer msUserId, PatentUnionModel patentUnionModel, Date now) {
        String tempSubmission;
        List<DomElement> tables = infoPage.getElementsByTagName("table");
        List<RsPatentApplyEntity> patentApplyList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tables)) {
            // 专利名称位置：第二table第一行最后一格(第二格)
            String tempPatentNo = StringUtils.trimAllWhitespace(tables.get(1).getElementsByTagName("td").get(1).getTextContent());
            if (!StringUtils.isEmpty(tempPatentNo)) {
                currentPatent.setPatentName(tempPatentNo);
                currentPatent.setApplyDateTime(DateUtil.parse(infoPage.getElementByName(EL_APPLY_DATE).getAttribute(TITLE), DateUtil.DEFAULT_DATE_FORMAT));
                tempSubmission = infoPage.getElementByName(EL_CASE_SUBMISSION_DATE).getAttribute(TITLE);
                currentPatent.setCaseSubmissionDate(StringUtils.isEmpty(tempSubmission) ? null : DateUtil.parse(tempSubmission, DateUtil.DEFAULT_DATE_FORMAT));
                currentPatent.setCaseStatus(infoPage.getElementByName(EL_CASE_STATUS).getAttribute(TITLE));
                currentPatent.setMainTypeNo(infoPage.getElementByName(EL_MAIN_TYPE_NO).getAttribute(TITLE));
                currentPatent.setInventor(infoPage.getElementByName(EL_INVENTOR).getAttribute(TITLE));
                currentPatent.setMsLastUpdatorId(msUserId);
                currentPatent.setLastUpdateTime(now);
                List<DomElement> companies = infoPage.getElementsByName(EL_COMPANY_NAME);
                List<DomElement> addresses = infoPage.getElementsByName(EL_COMPANY_ADDRESS);
                if (!CollectionUtils.isEmpty(companies)) {
                    for (int i = 0; i < companies.size(); i++) {
                        RsPatentApplyEntity apply = new RsPatentApplyEntity(
                                currentPatent.getPatentNo(),
                                companies.get(i).getAttribute(TITLE),
                                addresses.get(i).getAttribute(TITLE), msUserId, now);
                        patentApplyList.add(apply);
                    }
                }
            }
        }
        patentUnionModel.setPatent(currentPatent);
        patentUnionModel.setPatentApplyList(patentApplyList);
    }

    /**
     * 设置未缴费用
     *
     * @param currentPatent
     * @param costPage
     * @param patentUnionModel
     */
    private void setIsCostList(RsPatentEntity currentPatent, HtmlPage costPage, PatentUnionModel patentUnionModel) {
        List<DomElement> isCostTypes = costPage.getElementsByName(EL_IS_COST_TYPE);
        List<DomElement> isAmounts = costPage.getElementsByName(EL_IS_AMOUNT);
        List<DomElement> isLimitDates = costPage.getElementsByName(EL_IS_LIMIT_DATE);
        List<DomElement> isStatuses = costPage.getElementsByName(EL_IS_STATUS);
        List<RsPatentCostEntity> patentIsCostList = new ArrayList<>(isCostTypes.size());
        String tempLimit;
        String tempCost;
        String isStatus;
        for (int i = 0; i < isCostTypes.size(); i++) {
            tempLimit = isLimitDates.get(i).getAttribute(TITLE);
            tempCost = isAmounts.get(i).getAttribute(TITLE);
            isStatus = isStatuses.get(i).getAttribute(TITLE);
            patentIsCostList.add(new RsPatentCostEntity(currentPatent.getPatentNo(), isCostTypes.get(i).getAttribute(TITLE),
                    StringUtils.isEmpty(tempLimit) ? null : DateUtil.parse(tempLimit, DateUtil.DEFAULT_DATE_FORMAT),
                    BigDecimal.valueOf(StringUtils.isEmpty(tempCost) ? 0 : Double.valueOf(tempCost)),
                    false, null, null, isStatus, ""
            ));
        }
        patentUnionModel.setIsPatentCostList(patentIsCostList);
    }

    /**
     * 设置已缴费用
     *
     * @param currentPatent
     * @param costPage
     * @param patentUnionModel
     */
    private void setCostList(RsPatentEntity currentPatent, HtmlPage costPage, PatentUnionModel patentUnionModel) {
        List<DomElement> costTypes = costPage.getElementsByName(EL_COST_TYPE);
        List<DomElement> amounts = costPage.getElementsByName(EL_AMOUNT);
        List<DomElement> payDates = costPage.getElementsByName(EL_PAY_DATE);
        List<DomElement> receipts = costPage.getElementsByName(EL_RECEIPT_NO);
        List<DomElement> payers = costPage.getElementsByName(EL_PAYER);
        String tempPayDate;
        String tempCost;
        List<RsPatentCostEntity> patentCostList = new ArrayList<>(costTypes.size());
        for (int i = 0; i < costTypes.size(); i++) {
            tempPayDate = payDates.get(i).getAttribute(TITLE);
            tempCost = amounts.get(i).getAttribute(TITLE);
            patentCostList.add(new RsPatentCostEntity(currentPatent.getPatentNo(), costTypes.get(i).getAttribute(TITLE), null,
                    BigDecimal.valueOf(StringUtils.isEmpty(tempCost) ? 0 : Double.parseDouble(tempCost)), true,
                    StringUtils.isEmpty(tempPayDate) ? null : DateUtil.parse(tempPayDate, DateUtil.DEFAULT_DATE_FORMAT),
                    receipts.get(i).getAttribute(TITLE), EL_STATUS_DEFAULT, payers.get(i).getAttribute(TITLE)));
        }
        patentUnionModel.setPatentCostList(patentCostList);
    }

    public void clearCookies() {
        // 清除cookie
        webClient.getCookieManager().clearCookies();
    }

}
