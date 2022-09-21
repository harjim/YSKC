package com.yskc.rs.models.projectDocFile;

import com.yskc.common.utils.CommonUtils;
import com.yskc.rs.config.Constant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.projectDocFile
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-13 10:36
 * @Description: 项目完成情况分析
 */
public class ProjectAnalysisModel {

    /**
     * index 对应值： 0:项目周期，1:费用使用情况，2:试验试制次数，3:会议次数，4:试制量
     * 5:参与人数，6:使用仪器设备数，7:项目主要内容，8:项目创新点，9:项目主要技术指标
     * 10:项目经济指标，11:多层级研发管理，12:研发成果管理，13:知识产权管理，14:查新
     */
    private Integer index;

    private Object expectation;

    private Object actuality;

    private Date month;

    private BigDecimal expectationTotal;
    private BigDecimal actualityTotal;
    private int count;

    public ProjectAnalysisModel() {
    }

    public ProjectAnalysisModel(Integer index, Object expectation, Object actuality) {
        this.index = index;
        this.expectation = expectation;
        this.actuality = actuality;
        this.expectationTotal = this.actualityTotal = BigDecimal.ZERO;
        this.count = 0;
    }

    public ProjectAnalysisModel(Integer index, Object expectation, Object actuality, Date month) {
        this(index, expectation, actuality);
        this.month = month;
    }

    /**
     * 以index 加载数据
     *
     * @param list
     * @return
     */
    public static Map<String, Object> loadIndexMap(List<ProjectAnalysisModel> list) {
        // 每条数据分别取对应的 expectation，actuality，合计总数为 list.size * 2
        Map<String, Object> result = new HashMap<>(list.size() * 2);
        String expectation = "expectation";
        String actuality = "actuality";
        list.forEach(item -> {
            result.put(expectation + item.getIndex(), item.getExpectation());
            result.put(actuality + item.getIndex(), item.getActuality());
        });
        return result;
    }


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Object getExpectation() {
        return expectation;
    }

    public void setExpectation(Object expectation) {
        this.expectation = expectation;
    }

    public Object getActuality() {
        return actuality;
    }

    public void setActuality(Object actuality) {
        this.actuality = CommonUtils.strSpecialTransfer(actuality, false);
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public void addActuality(Boolean onlyActuality) {
        count++;
        this.actuality = count + "";
        if (onlyActuality == null || !onlyActuality) {
            int div = ((int) (count / 1.5));
            // 预计值最小为1
            this.expectation = (div > 0 ? div : 1) + "";
        }
    }

    public void addNumber(BigDecimal planYield, BigDecimal rdYield) {
        expectationTotal = expectationTotal.add(planYield);
        actualityTotal = actualityTotal.add(rdYield);
        this.expectation = expectationTotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        this.actuality = actualityTotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public void setCount(Integer attachmentCnt) {
        this.count = attachmentCnt - 1;
        addActuality(false);
    }


    public void reloadTotal() {
        this.expectation = expectationTotal != null ? expectationTotal.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "（万元）" : "";
        // 实际归集费用/10000
        this.actuality = actualityTotal != null ? actualityTotal.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP).toString() + "（万元）" : "";
    }
}
