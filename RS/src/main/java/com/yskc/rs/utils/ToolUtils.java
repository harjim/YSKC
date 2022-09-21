package com.yskc.rs.utils;

import cn.hutool.core.util.StrUtil;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.entity.SummaryEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.entity.tech.BaseEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.company.CompanyHolidayModel;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 用户帮助类
 *
 * @author huronghua
 */
public class ToolUtils {
    private static Pattern datePattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-4]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

    /**
     * @param datetime
     * @return
     */
    public static boolean checkDate(String datetime) {
        return datePattern.matcher(datetime).matches();
    }

    /**
     * 获取凭证号
     *
     * @param rdIndex
     * @param dayTime
     * @param costEnum
     * @return
     */
    public static String getParentVoucherNo(Integer rdIndex, Date dayTime, CostEnum costEnum) {
        return MessageFormat.format("RD{0}-{1}{2}",
                CommonUtils.patchLeftPosition(String.valueOf(rdIndex), 2, "0"),
                costEnum.getParent(),
                DateUtil.getDateTime(dayTime, "yyyyMMdd"));
    }

    public static String getVoucherNo(Integer rdIndex, Date dayTime, CostEnum costEnum) {
        return MessageFormat.format("RD{0}-{1}{2}",
                CommonUtils.patchLeftPosition(String.valueOf(rdIndex), 2, "0"),
                String.valueOf(costEnum.getType()),
                DateUtil.getDateTime(dayTime, "yyyyMMdd"));
    }

    /**
     * 生成工单号
     *
     * @param companyId
     * @param rdIndex
     * @param dayTime
     * @param costEnum
     * @return
     */
    public static String getWorkNo(Integer companyId, Integer rdIndex, Date dayTime, CostEnum costEnum) {
        return MessageFormat.format("{1}{2}{0}{3}", CommonUtils.patchLeftPosition(String.valueOf(companyId), 4, "0"),
                costEnum.getParent(),
                CommonUtils.patchLeftPosition(String.valueOf(rdIndex), 2, "0"),
                DateUtil.getDateTime(dayTime, "yyyyMMdd"));
    }

    /**
     * @param now
     * @param month
     * @param projectId
     * @param rdType
     * @param rdFunds
     * @param userInfo
     * @return
     */
    public static SummaryEntity build(Date now, Date month, Integer projectId, Integer rdType, BigDecimal rdFunds, UserInfo userInfo) {
        SummaryEntity summaryEntity = new SummaryEntity();
        summaryEntity.setLastUpdateTime(now);
        summaryEntity.setUpdatorId(userInfo.getId());
        summaryEntity.setCreateTime(now);
        summaryEntity.setCreatorId(userInfo.getUserId());
        summaryEntity.setMonth(month);
        summaryEntity.setAccountNumber("");
        summaryEntity.setProjectId(projectId);
        summaryEntity.setRdType(rdType);
        summaryEntity.setRdFunds(rdFunds);
        summaryEntity.setMsCreatorId(userInfo.getMsUserId());
        summaryEntity.setMsLastUpdatorId(userInfo.getMsUserId());
        summaryEntity.setLastUpdatorId(userInfo.getUserId());
        return summaryEntity;
    }

    /**
     * 获取组织id
     *
     * @param org
     * @return
     */
    public static Integer getOrgId(Map<String, Integer> orgMap, String org) {
        if (StringUtils.isEmpty(org)) {
            return null;
        }
        StringBuilder builder = new StringBuilder(org);
        if (!StrUtil.startWith(org, Constant.PATH_SEPARATOR)) {
            builder.insert(0, Constant.PATH_SEPARATOR);
        }
        if (StrUtil.endWith(org, Constant.PATH_SEPARATOR)) {
            builder.delete(builder.length() - 1, builder.length());
        }
        return orgMap.get(builder.toString());
    }

    public static Integer getParentType(int rdType, boolean old) {
        int parent;
        if (old) {
            parent = rdType / 100;
            if (parent == CostEnum.REPAIR.getParent()) {
                parent = CostEnum.INSPECTION.getParent();
            }
        } else {
            parent = rdType / 10000;
        }
        return parent;

    }

    /**
     * map list 数据,当不存在key的时候,会根据key put一个list,随后添加当前的 V数据;否则直接添加 V数据
     *
     * @param map
     * @param k
     * @param v
     * @param <K>
     * @param <V>
     */
    public static <K, V> void putAndAdd(Map<K, List<V>> map, K k, V v) {
        if (!map.containsKey(k)) {
            map.put(k, new ArrayList<>());
        }
        map.get(k).add(v);
    }

    public static <K, V> void putAndAddSet(Map<K, Set<V>> map, K k, V v) {
        if (!map.containsKey(k)) {
            map.put(k, new HashSet<>());
        }
        map.get(k).add(v);
    }


    public static <K, V> void putAndAddLinkedSet(Map<K, Set<V>> map, K k, V v) {
        if (!map.containsKey(k)) {
            map.put(k, new LinkedHashSet<>());
        }
        map.get(k).add(v);
    }

    /**
     * 获取节假日map，若不存在节假日，默认取周末
     *
     * @param holiday
     * @param skipHoliday
     * @param month
     * @return
     */
    public static Map<Integer, Boolean> getHolidayMap(CompanyHolidayModel holiday, Boolean skipHoliday, Date month) {
        Map<Integer, Boolean> holidayMap = new LinkedHashMap<>();
        if (skipHoliday) {
            return holidayMap;
        }
        if (null != holiday && !StringUtils.isEmpty(holiday.getHolidays())) {
            String[] holidays = holiday.getHolidays().split(",");
            for (String d : holidays) {
                holidayMap.put(Integer.valueOf(d), true);
            }
        } else {
            List<Integer> weekEnds = DateUtil.weekEndList(month);
            weekEnds.forEach(d -> holidayMap.put(d, true));
        }
        return holidayMap;
    }

    public static String getProjectPlace(ProjectEntity projectEntity) {

        //2021/6/15 验证报告地点数据来源项目的部门/车间/产线/工艺段
        List<String> strs = new ArrayList<>();
        if (!StringUtils.isEmpty(projectEntity.getDeptName()) && !Objects.equals(projectEntity.getDeptName(), Constant.PATH_SEPARATOR)) {
            strs.add(projectEntity.getDeptName());
        }
        if (!StringUtils.isEmpty(projectEntity.getWorkshop()) && !Objects.equals(projectEntity.getWorkshop(), Constant.PATH_SEPARATOR)) {
            strs.add(projectEntity.getWorkshop());
        }
        if (!StringUtils.isEmpty(projectEntity.getProductLine()) && !Objects.equals(projectEntity.getProductLine(), Constant.PATH_SEPARATOR)) {
            strs.add(projectEntity.getProductLine());
        }
        if (!StringUtils.isEmpty(projectEntity.getProcessSection()) && !Objects.equals(projectEntity.getProcessSection(), Constant.PATH_SEPARATOR)) {
            strs.add(projectEntity.getProcessSection());
        }
        return String.join(Constant.PATH_SEPARATOR, strs);
    }

    /**
     * 装载薪资费用明细map
     *
     * @param detail
     * @param resultMap
     * @param rdRatio
     * @param multiplyRatio
     * @return
     */
    public static Map<String, BigDecimal> loadDetailMap(String detail, Map<String, BigDecimal> resultMap, BigDecimal rdRatio, boolean multiplyRatio) {
        if (StringUtils.isEmpty(detail)) {
            return resultMap;
        }
        Map<String, Object> detailMap = JsonUtils.jsonToPojo(detail, Map.class);
        if (null == resultMap) {
            resultMap = new LinkedHashMap<>();
        }
        for (String col : detailMap.keySet()) {
            BigDecimal fee = new BigDecimal(detailMap.get(col).toString());
            resultMap.put(col, resultMap.getOrDefault(col, BigDecimal.ZERO).add(multiplyRatio ? fee.multiply(rdRatio).setScale(2, BigDecimal.ROUND_HALF_UP) : fee));
        }
        return resultMap;
    }


    /**
     * 实体创建
     *
     * @param entity
     * @param userId
     * @param now
     */
    public static void entityCreate(BaseEntity entity, Integer userId, Integer msUserId, Date now) {
        entity.setCreatorId(userId);
        entity.setCreateTime(now);
        entity.setMsCreatorId(msUserId);
        entityUpdate(entity, userId, msUserId,now);
    }

    /**
     * 实体更新
     *
     * @param entity
     * @param userId
     * @param now
     */
    public static void entityUpdate(BaseEntity entity, Integer userId, Integer msUserId, Date now) {
        entity.setLastUpdatorId(userId);
        entity.setLastUpdateTime(now);
        entity.setMsLastUpdatorId(msUserId);
    }

}
