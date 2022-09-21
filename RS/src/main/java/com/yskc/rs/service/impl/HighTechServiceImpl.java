package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.BigDecimalUtils;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.SysDictionaryDao;
import com.yskc.rs.dao.highTech.*;
import com.yskc.rs.entity.SysDictionaryEntity;
import com.yskc.rs.entity.hightech.*;
import com.yskc.rs.enums.HighTechFileTypeEnum;
import com.yskc.rs.models.SysDictionaryModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.*;
import com.yskc.rs.models.project.ProjectInfoModel;
import com.yskc.rs.service.HighTechService;
import com.yskc.rs.utils.TransactionUtils;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/5/28 8:17
 * @Description:
 */
@Service
public class HighTechServiceImpl implements HighTechService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HighTechDao highTechDao;
    @Autowired
    private HighTechDetailDao highTechDetailDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private HighTechProjectDao highTechProjectDao;
    @Autowired
    private HighTechFileDao highTechFileDao;
    @Autowired
    private HighTechIncomeDao highTechIncomeDao;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;

    @Override
    public String getDescription(Integer companyId, String hcode) {
        String description = "";
        List<String> list = highTechDao.getDescription(companyId,hcode);
        if(list.size()>0){
            description = list.get(0);
        }
        return description;
    }

    @Override
    public List<HighTechModel> getList(Integer companyId, QueryHighTechModel query) {
//        Pagination page = query.getPagination();
//        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
//            List<String> ascs = new ArrayList<>();
//            ascs.add("hcode");
//            page.setAscs(ascs);
//        }
//        return PageUtils.build(page, highTechDao.getList(page, companyId, query));
        List<HighTechModel> models = highTechDao.getList(companyId, query);
        BigDecimal totalAmount = highTechIncomeDao.getCountData(companyId, query.getYear());
        if (totalAmount != null && totalAmount.compareTo(BigDecimal.ZERO) != 0) {
            totalAmount = totalAmount.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP);
        }
        Date begin = DateUtil.getYearFirstDay(query.getYear());
        Date end = DateUtil.getYearLastDay(query.getYear());
        List<IncomeDataModel> dataModels = highTechIncomeDao.countDataByYear(companyId, begin, end);
        Map<Integer, BigDecimal> incomeMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(dataModels)) {
            incomeMap = dataModels.stream().collect(Collectors.toMap(e -> e.getHighTechId(), e -> e.getTotal().divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP)));
        }
        if (!CollectionUtils.isEmpty(models)) {
            for (HighTechModel model : models) {
                model.setTotalAmount(totalAmount);
                if (incomeMap.containsKey(model.getId())) {
                    model.setTotal(incomeMap.get(model.getId()));
                    if (totalAmount != null && totalAmount.compareTo(BigDecimal.ZERO) != 0) {
                        model.setProportion(incomeMap.get(model.getId()).multiply(new BigDecimal(100)).divide(totalAmount, 2, BigDecimal.ROUND_HALF_UP));
                    }
                }
            }
        }
        return models;
    }

    @Override
    public boolean save(UserInfo userInfo, HighTechModel model) throws OwnerException {
        if (model == null) {
            return true;
        }
        Date date = new Date();
        if (model.getId() != null) {
            verifyCodeName(userInfo.getCompanyId(), model.getHcode(), model.getHname(), model.getId());
            HighTechEntity highTech = highTechDao.selectById(model.getId());
            if(!model.getCodeNum().equals(highTech.getCodeNum())){
                model.setHcode(MessageFormat.format("{0,number,#}PS{1}",highTech.getStartYear(),model.getCodeNum()>9?model.getCodeNum():"0"+model.getCodeNum()));
            }
            BeanUtils.copyProperties(model, highTech);
            highTech.setLastUpdatorId(userInfo.getUserId());
            highTech.setLastUpdateTime(date);
            highTech.setMsLastUpdatorId(userInfo.getMsUserId());
            highTech.setEndYear(model.getStartYear() + 2);
            return highTechDao.updateById(highTech) > 0;
        } else {
            verifyCodeName(userInfo.getCompanyId(), model.getHcode(), model.getHname(), null);
            HighTechEntity highTech = HighTechEntity.build(date, model, userInfo);
            return highTechDao.insert(highTech) > 0;
        }
    }

    @Override
    public Integer getMaxCodeNum(Integer companyId, Integer year) {
        Integer maxCodeNum = highTechDao.getMaxCode(companyId, year);
        return maxCodeNum;
    }

    @Override
    public HighTechInfoModel getTechInfo(Integer companyId, Integer highTechId, Integer year) {
        HighTechEntity highTech = highTechDao.selectById(highTechId);
        HighTechInfoModel model = new HighTechInfoModel();
        BigDecimal totalAmount = highTechIncomeDao.getCountData(companyId, year);
        if(totalAmount != null){
            totalAmount=totalAmount.divide(Constant.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP);
        }
        if (highTech != null) {
            model.setTecIndustry(highTech.getTecIndustry());
            model.setHcode(highTech.getHcode());
            model.setHname(highTech.getHname());
        }
        model.setTotalAmount(totalAmount);
        Date begin = DateUtil.getYearFirstDay(year);
        Date end = DateUtil.getYearLastDay(year);
        Map<Object, BigDecimal> dataMap = new HashMap<>();
        List<Map<String, Object>> incomeDataMaps = highTechIncomeDao.getDataByMonth(highTechId, begin, end, companyId);
        Map<Integer, BigDecimal> dMap = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(incomeDataMaps)) {
            for (Map<String, Object> map : incomeDataMaps) {
                Date bookDate = cn.hutool.core.date.DateUtil.parseDate(map.get("bookDate").toString());
                Integer month = cn.hutool.core.date.DateUtil.month(bookDate) + 1;
                BigDecimal sumData = (BigDecimal) map.get("total");
                total = total.add(sumData);
                dMap.put(month, sumData.divide(Constant.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP));
            }
        }
        dataMap.putAll(dMap);
        dataMap.put("total", total.divide(Constant.TEN_THOUSAND, 2, BigDecimal.ROUND_HALF_UP));
        dataMap.put("proportion",totalAmount!=null && totalAmount.compareTo(BigDecimal.ZERO) !=0 ? total.divide(new BigDecimal(100)).divide(totalAmount, 2, BigDecimal.ROUND_HALF_UP):null);
        model.setIncomeMap(dataMap);
        HighTechDetailModel detail = highTechDetailDao.getTechDetail(companyId, highTechId);
        model.setDetailModel(detail);
        List<ProjectInfoModel> projects = highTechProjectDao.getByTech(highTechId, companyId);
        model.setProjects(CollectionUtils.isEmpty(projects) ? new ArrayList<>() : projects);
        List<HighTechFileModel> files = highTechFileDao.getByTech(highTechId, companyId);
        Map<Integer, List<HighTechFileModel>> fileMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(files)) {
            for (HighTechFileModel fileModel : files) {
                if (!fileMap.containsKey(fileModel.getType())) {
                    List<HighTechFileModel> models = new ArrayList<>();
                    fileMap.put(fileModel.getType(), models);
                }
                fileMap.get(fileModel.getType()).add(fileModel);
            }
        }
        model.setFileMap(fileMap);
        return model;
    }

    @Override
    public Map<String, Object> getTechDataMap(Integer companyId, Integer highTechId, Integer year) {
        Map<String, Object> resultMap = new HashMap<>();
        HighTechEntity highTech = highTechDao.selectById(highTechId);
        BigDecimal totalAmount = highTechIncomeDao.getCountData(companyId, year);
        if(totalAmount!=null){
            totalAmount=totalAmount.divide(Constant.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP);
        }
        resultMap.put("totalAmount",totalAmount);
        if (highTech != null) {
            List<SysDictionaryModel> sysDictionaryEntityList = sysDictionaryDao.getDictionaryByType(2);
            Map<String,String> industryMap=sysDictionaryEntityList.stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
            String techIndustry=highTech.getTecIndustry();
            List<String> strs=Arrays.asList(techIndustry.split(","));
            String resultStr="";
            for (String str:strs){
                String val=industryMap.get(str);
                if(StringUtils.isEmpty(resultStr)){
                    resultStr=val;
                }else {
                    resultStr = resultStr + "-" + val;
                }
            }
            resultMap.put("tecIndustry",resultStr );
            resultMap.put("hcode", highTech.getHcode());
            resultMap.put("hname", highTech.getHname());
        }
        Date begin = DateUtil.getYearFirstDay(year);
        Date end = DateUtil.getYearLastDay(year);
        List<Map<String, Object>> incomeDataMaps = highTechIncomeDao.getDataByMonth(highTechId, begin, end, companyId);
        Map<Integer, BigDecimal> dMap = new HashMap<>();
        BigDecimal total = BigDecimal.ZERO;
        List<IncomeDataModel> incomeDataModels = new ArrayList<>();
        if (!CollectionUtils.isEmpty(incomeDataMaps)) {
            for (Map<String, Object> map : incomeDataMaps) {
                Date bookDate = cn.hutool.core.date.DateUtil.parseDate(map.get("bookDate").toString());
                Integer month = cn.hutool.core.date.DateUtil.month(bookDate);
                BigDecimal sumData = (BigDecimal) map.get("total");
                dMap.put(month, sumData);
                total = total.add(sumData);
            }
        }
        for (int i = 1; i <= 12; i++) {
            IncomeDataModel incomeDataModel = new IncomeDataModel();
            incomeDataModel.setMonth(i + "月");
            if (dMap.containsKey(i)) {
                incomeDataModel.setIncomeStr(dMap.get(i).divide(Constant.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP).toString());
            }else {
                incomeDataModel.setIncomeStr("-");
            }
            incomeDataModels.add(incomeDataModel);
        }
        resultMap.put("incomes", incomeDataModels);
        resultMap.put("total", total.divide(Constant.TEN_THOUSAND,2,BigDecimal.ROUND_HALF_UP));
//        BigDecimal allTotal=highTechIncomeDao.getCountData(highTechId,companyId);
//        dataMap.put("allTotal",allTotal);
        resultMap.put("proportion", totalAmount!=null && totalAmount.compareTo(BigDecimal.ZERO) !=0 ? total.divide(new BigDecimal(100)).divide(totalAmount, 2, BigDecimal.ROUND_HALF_UP) : null);
        HighTechDetailModel detail = highTechDetailDao.getTechDetail(companyId, highTechId);
        resultMap.put("detailModel", detail);
        List<ProjectInfoModel> projects = highTechProjectDao.getByTech(highTechId, companyId);
        resultMap.put("projects", CollectionUtils.isEmpty(projects) ? new ArrayList<>() : projects);
        List<HighTechFileModel> files = highTechFileDao.getByTech(highTechId, companyId);
        List<Map<String, Object>> fileMaps = new ArrayList<>();
        if (!CollectionUtils.isEmpty(files)) {
            Map<Integer, String> fileNameMap = new LinkedHashMap<>();
            for (HighTechFileModel fileModel : files) {
                if (!fileNameMap.containsKey(fileModel.getType())) {
                    fileNameMap.put(fileModel.getType(), fileModel.getFileName());
                } else {
                    String fileName = fileNameMap.get(fileModel.getType()) + "," + fileModel.getFileName();
                    fileNameMap.put(fileModel.getType(), fileName);
                }
            }
            Map<Integer,String> enumMap= HighTechFileTypeEnum.getDataMap();
            for (Integer key : fileNameMap.keySet()) {
                Map<String, Object> fMap = new LinkedHashMap<>();
                fMap.put("type", enumMap.get(key));
                fMap.put("fileName", fileNameMap.get(key));
                fileMaps.add(fMap);
            }
        }
        resultMap.put("fileList", fileMaps);
        return resultMap;
    }

    @Override
    public List<HighTechFileModel> getUploadFiles(Integer highTechId, Integer companyId) {
        return highTechFileDao.getByTech(highTechId, companyId);
    }


    @Override
    public Integer saveDetail(UserInfo userInfo, HighTechInfoModel model) {
        Date date = new Date();
        HighTechDetailEntity entity = null;
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            //更新详情
            if (model.getDetailModel() != null) {
                if (model.getDetailModel().getId() != null) {
                    entity = HighTechDetailEntity.build(model.getDetailModel(), userInfo, date, model.getId(), true);
                    highTechDetailDao.updateById(entity);
                } else {
                    entity = HighTechDetailEntity.build(model.getDetailModel(), userInfo, date, model.getId(), false);
                    highTechDetailDao.insert(entity);
                }
            }
            //更新关联项目
            if (!CollectionUtils.isEmpty(model.getProjects())) {
                //  List<HighTechProjectEntity> highProjects = highTechProjectDao.getBindProjects(model.getId(), userInfo.getCompanyId());
                List<Integer> projectIds = model.getProjects().stream().map(e -> e.getId()).collect(Collectors.toList());
                List<HighTechProjectEntity> insertList = new ArrayList<>();
                highTechProjectDao.deleteByTech(Arrays.asList(model.getId()), userInfo.getCompanyId());
                for (Integer projectId : projectIds) {
                    HighTechProjectEntity highTechProject = new HighTechProjectEntity(projectId, model.getId(), userInfo, date);
                    insertList.add(highTechProject);
                }
                if (!CollectionUtils.isEmpty(insertList)) {
                    highTechProjectDao.inertList(insertList);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (
                Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("saveDetail", ex);
        }
        return entity != null ? entity.getId() : -1;
    }

    @Override
    public List<ProjectInfoModel> getProjects(Integer companyId, Integer highTechId) {
        HighTechEntity highTech = highTechDao.selectById(highTechId);
        List<ProjectInfoModel> projects = projectDao.getByTechYear(highTech.getStartYear(), companyId);
        return projects;
    }

    @Override
    public void uploadFile(HighTechFileEntity fileEntity) {
        highTechFileDao.insert(fileEntity);
    }

    @Override
    public boolean delTechFile(HighTechFileModel model) {
        return highTechFileDao.deleteById(model.getId()) > 0;
    }

    @Override
    public List<HighTechSelectModel> getHighTechSelect(Integer year, Integer companyId) {
        if (null == year || year <= 0) {
            return new ArrayList<>();
        }
        return highTechDao.getHighTechSelect(year, companyId);
    }

    @Override
    public Boolean verifyCodeAndName(Integer companyId, String hname, String hcode, Integer highTechId) throws OwnerException {
        verifyCodeName(companyId, hcode, hname, highTechId);
        return true;
    }

    @Override
    public Boolean delHighTech(List<HighTechModel> models, Integer companyId) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            return true;
        }
        List<Integer> highTechIds = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<HighTechIncomeEntity> incomes = highTechIncomeDao.getByTechs(highTechIds, companyId);
        if (!CollectionUtils.isEmpty(incomes)) {
            throw new OwnerException("不能删除已关联高品收入的项目！");
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            highTechFileDao.delByTech(highTechIds, companyId);
            highTechProjectDao.deleteByTech(highTechIds, companyId);
            highTechDetailDao.delByTech(highTechIds, companyId);
            highTechDao.deleteBatchIds(highTechIds);
            TransactionUtils.commit(transactionStatus);
        } catch (
                Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("saveDetail", ex);
        }
        return true;
    }


    /**
     * 验证高新技术代码和技术名称唯一
     *
     * @param companyId
     * @param hcode
     * @param hname
     */
    private void verifyCodeName(Integer companyId, String hcode, String hname, Integer existId) throws OwnerException {
        if (!StringUtils.isEmpty(hcode)) {
            HighTechEntity highTechEntity = highTechDao.verifyCodeName(hcode, companyId, null, existId);
            if (highTechEntity != null) {
                throw new OwnerException("已存在高新技术代码：" + highTechEntity.getHcode() + ",不能重复！");
            }
        }
        if (!StringUtils.isEmpty(hname)) {
            HighTechEntity highTechEntity = highTechDao.verifyCodeName(null, companyId, hname, existId);
            if (highTechEntity != null) {
                throw new OwnerException("已存在高新技术名称：" + highTechEntity.getHname() + ",不能重复！");
            }
        }

    }
}
