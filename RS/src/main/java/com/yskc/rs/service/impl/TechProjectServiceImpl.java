package com.yskc.rs.service.impl;

import cn.afterturn.easypoi.entity.ImageEntity;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.JsonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.tech.*;
import com.yskc.rs.entity.tech.DeclarationEntity;
import com.yskc.rs.entity.tech.TechProjectBasicEntity;
import com.yskc.rs.entity.tech.TechProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.tech.DeclarationInfo;
import com.yskc.rs.models.tech.DeclarationItem;
import com.yskc.rs.models.tech.QueryTechProjectModel;
import com.yskc.rs.models.tech.TechProjectModel;
import com.yskc.rs.models.tech.cost.MinProjectCostModel;
import com.yskc.rs.service.TechProjectCostService;
import com.yskc.rs.service.TechProjectService;
import com.yskc.rs.utils.YsWordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-09-23 11:09
 * @Description: 技改项目impl层
 */
@Service
public class TechProjectServiceImpl implements TechProjectService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private TechProjectDao techProjectDao;
    @Autowired
    private TechProjectBasicDao techProjectBasicDao;
    @Autowired
    private TechDeclarationDao techDeclarationDao;
    @Autowired
    private TechProjectCostService techProjectCostService;
    @Autowired
    private RsConfig rsConfig;
    @Autowired
    private ProductStageDao productStageDao;
    @Autowired
    private ProductStageListDao productStageListDao;


    @Override
    public PageModel<List<TechProjectModel>> getTechProjects(Integer companyId, QueryTechProjectModel query) {
        Pagination page = new Pagination(query.getPageNo(), query.getPageSize());
        return PageUtils.build(page, techProjectDao.getTechProjects(page, companyId, query));
    }

    /**
     * 获取公司项目年份
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Integer> getYears(Integer companyId) throws OwnerException {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("companyId", companyId);
        List<TechProjectEntity> techProjectEntities = techProjectDao.selectByMap(columnMap);
        List<Integer> yearList = techProjectEntities.stream().map(TechProjectEntity::getPyear).sorted(Comparator.comparing(a -> a)).collect(Collectors.toList());
        HashSet hashSet = new HashSet(yearList);
        yearList.clear();
        yearList.addAll(hashSet);
        return yearList;
    }

    /**
     * 获取公司技改项目
     *
     * @param companyId
     * @param year
     * @return
     * @throws OwnerException
     */
    @Override
    public List<TechProjectEntity> getTechProjectsByYear(Integer companyId, Integer year) throws OwnerException {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("companyId", companyId);
        columnMap.put("pyear", year);
        return techProjectDao.selectByMap(columnMap);
    }

    /**
     * 获取公司技改项目基本信息
     *
     * @param companyId
     * @param projectId
     * @return
     * @throws OwnerException
     */
    @Override
    public TechProjectBasicEntity getTechProjectBasic(Integer companyId, Integer projectId) throws OwnerException {
        Map<String, Object> columnMap = new HashMap<>(1);
        columnMap.put("companyId", companyId);
        columnMap.put("projectId", projectId);
        List<TechProjectBasicEntity> techProjectBasicEntities = techProjectBasicDao.selectByMap(columnMap);
        if (techProjectBasicEntities.size() > 0) {
            return techProjectBasicEntities.get(0);
        }
        return null;
    }


    /**
     * 保存技改项目基本信息
     *
     * @param userInfo
     * @param techProjectBasicEntity
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean saveProjectBasic(UserInfo userInfo, TechProjectBasicEntity techProjectBasicEntity) throws OwnerException {
        try {
            if (techProjectBasicEntity.getId() != null) {
                TechProjectBasicEntity projectBasicEntity = techProjectBasicDao.selectById(techProjectBasicEntity.getId());
                projectBasicEntity.setLastUpdateTime(new Date());
                projectBasicEntity.setLastUpdatorId(userInfo.getId());
                projectBasicEntity.setConssRuctionType(techProjectBasicEntity.getConssRuctionType());
                projectBasicEntity.setIndustryCode(techProjectBasicEntity.getIndustryCode());
                projectBasicEntity.setMainContents(techProjectBasicEntity.getMainContents());
                projectBasicEntity.setNotInvolveRemark(techProjectBasicEntity.getNotInvolveRemark());
                projectBasicEntity.setRecordNumber(techProjectBasicEntity.getRecordNumber());
                projectBasicEntity.setProjectType(techProjectBasicEntity.getProjectType());
                projectBasicEntity.setTechnicalField(techProjectBasicEntity.getTechnicalField());
                techProjectBasicDao.updateById(projectBasicEntity);
            } else {
                techProjectBasicEntity.setCompanyId(userInfo.getCompanyId());
                techProjectBasicEntity.setCreateTime(new Date());
                techProjectBasicEntity.setCreatorId(userInfo.getId());
                techProjectBasicEntity.setLastUpdateTime(new Date());
                techProjectBasicEntity.setLastUpdatorId(userInfo.getId());
                techProjectBasicDao.insert(techProjectBasicEntity);
            }
        } catch (Exception ex) {
            logger.error("saveProjectBasic", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    @Override
    public boolean add(UserInfo userInfo, TechProjectModel techProjectModel) {
        Date now = new Date();
        TechProjectEntity techProjectEntity = new TechProjectEntity();
        BeanUtil.copyProperties(techProjectModel, techProjectEntity);
        techProjectEntity.setCompanyId(userInfo.getCompanyId());
        techProjectEntity.setCreatorId(userInfo.getId());
        techProjectEntity.setCreateTime(now);
        techProjectEntity.setLastUpdatorId(userInfo.getId());
        techProjectEntity.setLastUpdateTime(now);
        return techProjectDao.insert(techProjectEntity) > 0;
    }

    @Override
    public boolean update(UserInfo userInfo, TechProjectModel model) {
        TechProjectEntity techProjectEntity =techProjectDao.selectById(model.getId());
        techProjectEntity.setAidType(model.getAidType());
        techProjectEntity.setApplyDate(model.getApplyDate());
        techProjectEntity.setBeginDate(model.getBeginDate());
        techProjectEntity.setEndDate(model.getEndDate());
        techProjectEntity.setLinkName(model.getLinkName());
        techProjectEntity.setLinkTel(model.getLinkTel());
        techProjectEntity.setMasterName(model.getMasterName());
        techProjectEntity.setMasterTel(model.getMasterTel());
        techProjectEntity.setPyear(model.getPyear());
        techProjectEntity.setRemark(model.getRemark());
        techProjectEntity.setLastUpdateTime(new Date());
        techProjectEntity.setLastUpdatorId(userInfo.getId());
        return techProjectDao.updateById(techProjectEntity) > 0;
    }

    @Override
    public boolean del(Integer id) {
        return techProjectDao.deleteById(id) > 0;
    }

    @Override
    public boolean dels(List<TechProjectEntity> techProjectEntityList) {
        List<Integer> ids = new ArrayList<>(techProjectEntityList.size());
        techProjectEntityList.forEach(techProject -> {
            ids.add(techProject.getId());
        });
        return techProjectDao.deleteBatchIds(ids) > 0;
    }

    /**
     * 根据标题获取技改申请报告
     *
     * @param projectId
     * @param key
     * @param info
     * @return
     * @throws OwnerException
     */
    @Override
    public List<DeclarationInfo> getDeclarationList(Integer projectId, String key, UserInfo info) throws OwnerException {
        List<DeclarationInfo> tempList = getDeclarationList(info.getCompanyId(), projectId);
        List<DeclarationInfo> declarationEntities = getDeclarationList(tempList, key);
        return declarationEntities;
    }

    @Override
    public boolean saveDeclarationInfoList(UserInfo info, List<DeclarationInfo> declarationInfos) throws OwnerException {
        List<DeclarationEntity> declarationEntities = new ArrayList<>();
        declarationInfos.stream().forEach(item -> {
            List<DeclarationItem> declarationItems = new ArrayList<>();
            if (item.getTextItem() != null) {
                declarationItems.add(item.getTextItem());
            }
            if (item.getTableItem() != null) {
                declarationItems.add(item.getTableItem());
            }
            if (item.getImageItem() != null) {
                declarationItems.add(item.getImageItem());
            }
            item.setContent(JsonUtils.objectToJson(declarationItems));
            item.setLastUpdateTime(new Date());
            item.setLastUpdatorId(info.getId());
            DeclarationEntity declarationEntity = new DeclarationEntity();
            BeanUtils.copyProperties(item, declarationEntity);
            declarationEntities.add(declarationEntity);
        });
        techDeclarationDao.updateList(declarationEntities);
        return true;
    }

    @Override
    public void exportDeclarationInfo(UserInfo info, Integer projectId, OutputStream out) throws Exception {
        List<DeclarationInfo> tempList = getDeclarationList(info.getCompanyId(), projectId);
        TechProjectEntity techProjectEntity = techProjectDao.selectById(projectId);
        Map<String, Object> costMap = techProjectCostService.getProjectCost(info.getCompanyId(), projectId);
        String basicPath = rsConfig.getUploadConfig().getBasicPath().substring(0, rsConfig.getUploadConfig().getBasicPath().length() - 7);
        Map<String, Object> datas = getExportDeclarationDatas(tempList, basicPath);
        datas.putAll(getProjectCost(costMap));
        datas.put("cname", info.getCompanyName());
        datas.put("cLogo", !StringUtils.isEmpty(info.getCompanyLogoPath()) && FileUtil.exist(rsConfig.getUploadConfig().getBasicPath() + info.getCompanyLogoPath()) ?
                new ImageEntity(rsConfig.getUploadConfig().getBasicPath() + info.getCompanyLogoPath(), 120, 45) : "");
        datas.put("p", techProjectEntity);
        YsWordUtils.generalWordReport(datas, rsConfig.getUploadConfig().getResourcePath()+ Constant.TEMPLATE_DIR + "技改项目申请报告模板.docx", (workBook) -> {
            try {
                workBook.write(out);
                out.close();
            } catch (IOException e) {
                logger.info(e.getMessage(), e);
            }
        });

    }

    private Map<String, Object> getProjectCost(Map<String, Object> costMap) {
        Map<String, Object> result = new HashMap<>();
        BigDecimal[] costArr = (BigDecimal[]) costMap.get("costArr");
        List<MinProjectCostModel> costList = (ArrayList<MinProjectCostModel>) costMap.get("datas");
        List<Map<String, Object>> typeTotalList = new ArrayList<>();
        BigDecimal typeTotal = BigDecimal.ZERO;
        BigDecimal totalCost = BigDecimal.ZERO;
        for (int i = 0; i < 3; i++) {
            Map<String, Object> node = new HashMap<>();
            node.put("num", i + 1);
            node.put("amount", costArr[i]);
            typeTotal = typeTotal.add(costArr[i]);
            switch (i) {
                case 0:
                    node.put("type", "设备");
                    break;
                case 1:
                    node.put("type", "建设费");
                case 2:
                    node.put("type", "铺底流动资金");
                    break;
                default:
                    break;
            }
            typeTotalList.add(node);
        }
        Map<String, Object> node = new HashMap<>();
        node.put("amount", typeTotal);
        node.put("type", "合计");
        typeTotalList.add(node);
        result.put("2_1_5_data", typeTotalList);

        for (int i = 0; i < costList.size(); i++) {
            totalCost = totalCost.add(costList.get(0).getAuditAmount());
        }
        MinProjectCostModel lastRow = new MinProjectCostModel();
        lastRow.setAuditAmount(totalCost);
        lastRow.setCname("合计");
        costList.add(lastRow);
        result.put("2_2_1_data", costList);
        return result;
    }

    private Map<String, Object> getExportDeclarationDatas(List<DeclarationInfo> tempList, String basicPath) {
        Map<String, Object> datas = new HashMap<>();
        String defaultStr = "";
        String tableKey = "datas";
        String imgKey = "imgPath";
        String contentStr = "content";
        String datasImgKey = "_image";
        String datasTableKey = "_data";
        ArrayList<Object> defaultArr = new ArrayList<>();
        tempList.stream().forEach(item -> {
            String key = item.getKey();
            String dataKey = key.replaceAll("\\.", "_");
            if (!StringUtils.isEmpty(item.getContent())) {
                @SuppressWarnings("all")
                List<Map<String, Object>> contentList = JsonUtils.jsonToPojo(item.getContent(), List.class);
                for (Map<String, Object> content : contentList) {
                    if ("1.8".equals(item.getKey())) {
                        if (!StringUtils.isEmpty(content.get("columns"))) {
                            List<Map<String, Object>> years = (List<Map<String, Object>>) content.get("columns");
                            datas.put(dataKey + "_year1", years.get(1) != null ? years.get(1).get("title") : "");
                            datas.put(dataKey + "_year2", years.get(2) != null ? years.get(2).get("title") : "");
                            datas.put(dataKey + "_year3", years.get(3) != null ? years.get(3).get("title") : "");
                        }
                    }
                    if (StringUtils.isEmpty(content.get("type"))) {
                        if (StringUtils.isEmpty(datas.get(dataKey + datasTableKey))) {
                            datas.put(dataKey + datasTableKey, defaultArr);
                            continue;
                        }
                        if (StringUtils.isEmpty(datas.get(dataKey + datasImgKey))) {
                            datas.put(dataKey + datasImgKey, defaultStr);
                            continue;
                        }
                        datas.put(dataKey, defaultArr);
                    } else {
                        switch (content.get("type").toString().trim()) {
                            case "2":
                                if (!StringUtils.isEmpty(content.get(tableKey))) {
                                    datas.put(dataKey + datasTableKey, content.get(tableKey));
                                    break;
                                }
                                datas.put(dataKey + datasTableKey, defaultArr);
                                break;
                            case "3":
                                if (!StringUtils.isEmpty(content.get(imgKey))) {
                                    String[] imgArr = ((String) content.get(imgKey)).split(",");
                                    List<ImageEntity> images = new ArrayList<>(imgArr.length);
                                    for (String img : imgArr) {
                                        images.add(new ImageEntity(basicPath + img, 320, 180));
                                    }
                                    datas.put(dataKey + datasImgKey, images);
                                } else {
                                    datas.put(dataKey + datasImgKey, defaultStr);
                                }
                                break;

                            default:
                                datas.put(dataKey, StringUtils.isEmpty(content.get(contentStr)) ? defaultStr : (String) content.get(contentStr));
                                break;
                        }
                    }
                }
                return;
            }
            datas.put(dataKey, defaultStr);
            datas.put(dataKey + datasTableKey, defaultArr);
            datas.put(dataKey + datasImgKey, defaultStr);
        });
        String lastTow = "4_1_2_5_data";
        String lastOne = "4_1_2_6_data";
        if (StringUtils.isEmpty(datas.get(lastTow))) {
            datas.put(lastTow, defaultArr);
        }
        if (StringUtils.isEmpty(datas.get(lastOne))) {
            datas.put(lastOne, defaultArr);
        }
        return datas;
    }

    private List<DeclarationInfo> getDeclarationList(List<DeclarationInfo> entities, String key) {
        List<DeclarationInfo> declarationEntityList = new ArrayList<>();
        List<DeclarationInfo> tempList = entities.stream().filter(a -> a.getKey().equals(key)).collect(Collectors.toList());
        if (tempList.size() == 0) {
            return declarationEntityList;
        }
        DeclarationInfo declarationEntity = tempList.get(0);
        for (DeclarationInfo entity : entities) {
            if (entity.getParentKey().equals(declarationEntity.getKey())) {
                declarationEntityList.add(entity);
                declarationEntityList.addAll(getDeclarationList(entities, entity.getKey()));
            }
        }
        return declarationEntityList;
    }

    /**
     * 获取项目申请报告信息
     *
     * @param companyId
     * @param projectId
     * @return
     */
    private List<DeclarationInfo> getDeclarationList(Integer companyId, Integer projectId) {
        List<DeclarationInfo> declarationInfoList = techDeclarationDao.getDeclarationInfoList(companyId, projectId);
        if (declarationInfoList.size() == 0) {
            declarationInfoList = techDeclarationDao.getDeclarationInfoList(0, 0);
            List<DeclarationEntity> declarationEntities = new ArrayList<>();
            declarationInfoList = declarationInfoList.stream().map(item -> {
                item.setId(null);
                item.setCompanyId(companyId);
                item.setProjectId(projectId);
                item.setCreateTime(new Date());
                item.setLastUpdateTime(new Date());
                DeclarationEntity declarationEntity = new DeclarationEntity();
                BeanUtils.copyProperties(item, declarationEntity);
                declarationEntities.add(declarationEntity);
                return item;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(declarationEntities)) {
                techDeclarationDao.addBatch(declarationEntities);
            }
        }
        return declarationInfoList;
    }

}
