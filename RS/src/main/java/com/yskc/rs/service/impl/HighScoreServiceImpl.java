package com.yskc.rs.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.*;
import com.yskc.rs.dao.company.CompanyDao;
import com.yskc.rs.dao.highTech.HighFinanceScoreDao;
import com.yskc.rs.dao.highTech.HighTechDao;
import com.yskc.rs.dao.highTech.HighTechIncomeDao;
import com.yskc.rs.dao.project.AchievementDao;
import com.yskc.rs.entity.HighTechScoreEntity;
import com.yskc.rs.entity.hightech.HighFinanceScoreEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.highscore.*;
import com.yskc.rs.models.hightech.HighTechSelectModel;
import com.yskc.rs.service.HighScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2021-10-25 08:31
 * @Description: 高新评分评价业务实现层
 */
@Service
public class HighScoreServiceImpl implements HighScoreService {

    @Autowired
    private HighTechScoreDao highTechScoreDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private SummaryDao summaryDao;
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private HighTechDao highTechDao;
    @Autowired
    private HighTechIncomeDao highTechIncomeDao;
    @Autowired
    private PatentDetailDao patentDetailDao;
    @Autowired
    private HighFinanceScoreDao highFinanceScoreDao;
    @Autowired
    private SysDocumentDao sysDocumentDao;
    @Autowired
    private AchievementDao achievementDao;

    @Override
    public HighTechScoreModel getTechScore(Integer year, Integer companyId) {
        HighTechScoreModel result = highTechScoreDao.getTechScore(companyId, year);
        Date currentYearFirstDay = DateUtil.getYearFirstDay(year);
        int before3Year = year - 3;
        if (result == null) {
            result = new HighTechScoreModel(year);
            loadPatentScore(result, companyId, year);
            // 机构建设事项/成果 相关
            loadBuildResultScore(result, companyId, before3Year, year);
        }
        List<String> tecIndustries = patentDetailDao.getTecIndustries(companyId, year);
        result.setTecIndustries(tecIndustries);
        result.setPatent(!CollectionUtils.isEmpty(tecIndustries));

        // 前三年rd数 + 归集费
        Date beforeYearFirstDay = DateUtil.getYearFirstDay(before3Year);
        HighTechScoreModel rdCntAndRdFunds = summaryDao.getRdCntAndRdFunds(beforeYearFirstDay, currentYearFirstDay, companyId);
        if (rdCntAndRdFunds != null) {
            result.setRdCnt(rdCntAndRdFunds.getRdCnt());
            BigDecimal rdFunds = rdCntAndRdFunds.getRdFunds();
            result.setRdFunds(rdFunds == null ? null : rdFunds.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
        }
        Integer prevYear = year - 1;
        // 高品相关
        List<HighTechSelectModel> highTechSelects = highTechDao.getHighTechSelect(prevYear, companyId);
        if (!CollectionUtils.isEmpty(highTechSelects)) {
            result.setHighTechCnt(highTechSelects.size());
            result.setHighTechCodes(highTechSelects.stream().map(HighTechSelectModel::getHcode).collect(Collectors.joining(",")));
        }
        BigDecimal income = highTechIncomeDao.getCountData(companyId, prevYear);
        result.setIncome(income == null ? null : income.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));

        // 注册时间比对
        Date registerTime = companyDao.getRegisterTime(companyId);
        result.setRegister(registerTime != null && cn.hutool.core.date.DateUtil.betweenDay(registerTime, currentYearFirstDay, true) >= 365);

        // 研发人员对比
        Integer employeeAmount = reportDao.getEmployeeAmount(year, companyId);
        if (employeeAmount != null) {
            Integer enumberCnt = rdEmployeeDao.countYearEnumber(companyId, year);
            if (enumberCnt != null && enumberCnt > 0 && employeeAmount > 0) {
                result.setRdRatio(NumberUtil.round((double) enumberCnt / employeeAmount, 4).doubleValue());
            }
        }
        return result;
    }


    @Override
    public Boolean saveTechScore(HighTechScoreEntity highScore, UserInfo userInfo) throws OwnerException {
        highScore.setCompanyId(userInfo.getCompanyId());
        Integer id = highTechScoreDao.getId(highScore.getCompanyId(), highScore.getYear());
        if (id != null) {
            highScore.setId(id);
            highScore.update(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
            return highTechScoreDao.updateById(highScore) > 0;
        } else {
            highScore.create(userInfo.getUserId(), userInfo.getMsUserId(), new Date());
            return highTechScoreDao.insert(highScore) > 0;
        }
    }

    @Override
    public HighFinanceScoreModel getFinanceScore(Integer year, Integer companyId) {
        HighFinanceScoreModel model = highFinanceScoreDao.getFinanceScore(companyId, year);
        return null == model ? new HighFinanceScoreModel() : model;
    }

    @Override
    public Boolean saveFinanceScore(HighFinanceScoreEntity financeScore, UserInfo info) {
        financeScore.setCompanyId(info.getCompanyId());
        Integer id = highFinanceScoreDao.getId(financeScore.getCompanyId(), financeScore.getYear());
        if (null != id) {
            financeScore.setId(id);
            financeScore.update(info.getUserId(), info.getMsUserId(), new Date());
            return highFinanceScoreDao.updateById(financeScore) > 0;
        } else {
            financeScore.create(info.getUserId(), info.getMsUserId(), new Date());
            return highFinanceScoreDao.insert(financeScore) > 0;
        }
    }

    @Override
    public HighTotalScoreModel getTotalScore(Integer year, Integer companyId) {
        HighTechScoreModel highTechScore = getTechScore(year, companyId);
        HighFinanceScoreModel financeScore = getFinanceScore(year, companyId);
        return HighTotalScoreModel.build(highTechScore, financeScore);
    }

    /**
     * 研究开发组织管理水平
     * 机构建设事项相关
     * 成果相关得分
     *
     * @param result
     * @param companyId
     * @param before3Year
     * @param year
     */
    private void loadBuildResultScore(HighTechScoreModel result, Integer companyId, Integer before3Year, Integer year) {
        int endYear = year - 1;
        List<ChildBuildTypeModel> childBuildTypes = sysDocumentDao.getChildBuildTypes(companyId, before3Year, endYear);
        if (!CollectionUtils.isEmpty(childBuildTypes)) {
            childBuildTypes.forEach(item -> {
                Integer childType = item.getChildType();
                int score = 3;
                // type <= 1 时，总分为6分，每个buildType占3分，type>1时，总分为4，每个buildType占2分
                if (childType > 1) {
                    score = 2;
                }
                switch (childType) {
                    case 0:
                        result.addGeneralLedger(score);
                        break;
                    case 1:
                        result.addCooperation(score);
                        break;
                    case 2:
                        result.addExcitation(score);
                        break;
                    case 3:
                        result.addFoster(score);
                        break;
                    default:
                        break;
                }
            });
        }
        List<Integer> childTypes = achievementDao.getChildTypes(companyId, before3Year, endYear);
        if (!CollectionUtils.isEmpty(childTypes)) {
            result.setAddScienceResult(childTypes.size());
            childTypes.forEach(type -> {
                switch (type) {
                    case 4:
                        result.setTestFile(true);
                        break;
                    case 5:
                        result.setAdvancedFile(true);
                        break;
                    case 6:
                        result.setLeadFile(true);
                        break;
                    case 7:
                        result.setExportFile(true);
                        break;
                    case 8:
                        result.setExportWestFile(true);
                        break;
                    default:
                        break;
                }
            });
        }
        Integer achievementCnt = achievementDao.countAchievement(companyId, before3Year, endYear);
        if (achievementCnt != null && achievementCnt > 0) {
            int avgCnt = achievementCnt / 3;
            if (avgCnt > 0) {
                result.setScienceResult(avgCnt * 6 - 5);
            }
        }
    }


    private void loadPatentScore(HighTechScoreModel result, Integer companyId, Integer year) {
        List<HighTechPatentModel> patentList = patentDetailDao.getHighTechPatents(companyId, year);
        if (!CollectionUtils.isEmpty(patentList)) {
            int totalClaim = 0;
            HighTechPatentModel typeOne = HighTechPatentModel.buildDefault();
            HighTechPatentModel typeTow = HighTechPatentModel.buildDefault();
            int acquirementMode = 1;
            for (HighTechPatentModel patent : patentList) {
                int type = patent.getType();
                totalClaim += patent.getClaimNum();
                // type不为1类，则为2类
                if (type == 1) {
                    acquirementMode = 0;
                    typeOne.sum(patent);
                } else {
                    typeTow.sum(patent);
                }
            }
            // 平均权利数
            int avgClaim = totalClaim / patentList.size();
            int advanced = 0;
            int effect = 0;
            int patentAmount = 0;
            if (typeOne.getIssueDate() != null) {
                int patentCnt = typeOne.getPatentCnt();
                int issueYear = cn.hutool.core.date.DateUtil.year(typeOne.getIssueDate());
                int subYear = year - issueYear;
                int usedCnt = typeOne.getUsedCnt() + 1;
                // 1类专利，3年内授权
                if (subYear <= 3) {
                    advanced = 8;
                    // 1类专利8年内授权，
                } else if (subYear <= 8 && usedCnt <= 2) {
                    advanced = 6;
                } else if (subYear <= 15 && usedCnt <= 3) {
                    advanced = 4;
                }
                if (subYear <= 5) {
                    effect = 7;
                    if (avgClaim >= 6) {
                        effect += 1;
                    }
                } else if (subYear <= 8) {
                    effect = 5;
                    if (avgClaim >= 4) {
                        effect += 1;
                    }
                } else if (subYear < 15) {
                    effect = 3;
                    if (avgClaim >= 3) {
                        effect += 1;
                    }
                } else {
                    effect = 1;
                    if (avgClaim >= 1) {
                        effect += 1;
                    }
                }
                if (patentCnt >= 1) {
                    patentAmount = 8;
                }
            }
            if (typeTow.getIssueDate() != null) {
                int patentCnt = typeTow.getPatentCnt();
                int issueYear = cn.hutool.core.date.DateUtil.year(typeTow.getIssueDate());
                int subYear = year - issueYear;
                if (advanced <= 4) {
                    if (patentCnt >= 5 && subYear <= 3) {
                        advanced = 6;
                    } else if (patentCnt >= 3 && subYear > 3) {
                        advanced = 4;
                    } else {
                        advanced = 2;
                    }
                }
                if (effect == 0) {
                    if (subYear <= 3) {
                        effect = 7;
                        if (avgClaim >= 6) {
                            effect += 1;
                        }
                    } else if (subYear <= 5) {
                        effect = 5;
                        if (avgClaim >= 4) {
                            effect += 1;
                        }
                    } else if (subYear <= 8) {
                        effect = 3;
                        if (avgClaim >= 3) {
                            effect += 1;
                        }
                    } else {
                        effect = 1;
                        if (avgClaim >= 1) {
                            effect += 1;
                        }
                    }
                }
                if (patentAmount == 0) {
                    patentAmount = Math.min(patentCnt, 6);
                }
            }
            result.setAdvanced(advanced);
            result.setEffect(effect);
            result.setPatentAmount(patentAmount);
            result.setAcquirementMode(acquirementMode);
            // 存在自主，则为6分，否则为3分
            result.setAcquirement(acquirementMode == 0 ? 6 : 3);
        }
    }


}