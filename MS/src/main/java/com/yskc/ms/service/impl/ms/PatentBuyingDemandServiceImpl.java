package com.yskc.ms.service.impl.ms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.CustomerDao;
import com.yskc.ms.dao.ms.PatentBuyingDemandDao;
import com.yskc.ms.dao.ms.PatentBuyingListDao;
import com.yskc.ms.dao.ms.PatentSeaDao;
import com.yskc.ms.dao.rs.RsPatentDao;
import com.yskc.ms.entity.ms.PatentBuyingDemand;
import com.yskc.ms.entity.ms.PatentBuyingList;
import com.yskc.ms.entity.ms.PatentSeaEntity;
import com.yskc.ms.entity.rs.RsPatentEntity;
import com.yskc.ms.enums.PatentMainTypeEnum;
import com.yskc.ms.models.BatchZipModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.*;
import com.yskc.ms.models.patentPlan.SetPatentEngineerModel;
import com.yskc.ms.models.patentbuying.PatentCanBuyModel;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.service.ms.PatentBuyingDemandService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.TransactionUtils;
import com.yskc.ms.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/7/8 15:27
 * @Description:
 */
@Service
public class PatentBuyingDemandServiceImpl implements PatentBuyingDemandService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PatentBuyingDemandDao patentBuyingDemandDao;
    @Autowired
    private PatentSeaDao patentSeaDao;
    @Autowired
    private PatentBuyingListDao patentBuyingListDao;
    @Autowired
    private RsPatentDao rsPatentDao;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private CustomerDao customerDao;

    @Override

    public PageModel<List<PatentBuyingDemandModel>> getList(QueryDemandModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pbd.createTime");
            page.setDescs(desc);
        }
        List<PatentBuyingDemandModel> list = patentBuyingDemandDao.getList(page, query, dataPerm);
        List<Integer> groupIds = list.stream().filter(a -> a.getGroupId() > 0).map(a -> a.getGroupId()).distinct().collect(Collectors.toList());
        Map<Integer, String> groupMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(groupIds)) {
            List<MiniModel> groups = customerDao.getGroupList(groupIds);
            groups.forEach(item -> groupMap.put(item.getId(), item.getTitle()));
        }
        if (!CollectionUtils.isEmpty(groupMap)) {
            for (PatentBuyingDemandModel model : list) {
                model.setGroupName(groupMap.get(model.getGroupId()));
            }
        }
        return PageUtils.build(page, list);
    }

    @Override
    public Integer saveDemand(PatentBuyingDemandModel model, UserInfo userInfo) throws OwnerException {
        Integer patentNum = 0;
        if (null != model.getAppearanceDesignNum()) {
            patentNum += model.getAppearanceDesignNum();
        }
        if (null != model.getInventionNum()) {
            patentNum += model.getInventionNum();
        }
        if (null != model.getUtilityNum()) {
            patentNum += model.getUtilityNum();
        }
        if (patentNum < 1) {
            throw new OwnerException("至少需要一种专利类型有需求个数，保存失败！");
        }
        if (null == model.getYear()) {
            throw new OwnerException("年份不能为空，保存失败！");
        }
        if (null == model.getType()) {
            throw new OwnerException("需求类型不能为空，保存失败！");
        }
        if (patentBuyingDemandDao.countRepeat(model.getCustomerId(), model.getYear(), model.getType(), model.getId()) > 0) {
            throw new OwnerException(MessageFormat.format("当前年【{0,number,#}】已存在相同需求。", model.getYear()));
        }
        Date date = new Date();
        PatentBuyingDemand demand;
        if (model.getId() != null) {
            demand = patentBuyingDemandDao.selectById(model.getId());
            if (null == demand) {
                throw new OwnerException("专利需求已删除或不存在，保存失败！");
            }
            if (demand.getStatus() != 0) {
                throw new OwnerException("已提交或确认的专利购买需求不能修改！");
            }
            BeanUtils.copyProperties(model, demand);
            demand.update(userInfo.getId(), date);
            patentBuyingDemandDao.updateById(demand);
        } else {
            demand = new PatentBuyingDemand();
            BeanUtils.copyProperties(model, demand);
            demand.create(userInfo.getId(), new Date());
            patentBuyingDemandDao.insert(demand);
        }
        return demand.getId();
    }

    @Override
    public Boolean submitDemand(PatentBuyingDemandModel model, UserInfo userInfo) throws OwnerException {
        PatentBuyingDemand demand = patentBuyingDemandDao.selectById(model.getId());
        if (null == demand) {
            throw new OwnerException("专利需求已删除或不存在，操作失败！");
        }
        if (demand.getStatus() != model.getStatus() - 1) {
            throw new OwnerException(MessageFormat.format("专利需求已{0}，不能重复操作！", model.getStatus() == 1 ? "提交" : "确认"));
        }
        demand.setStatus(model.getStatus());
        demand.update(userInfo.getId(), new Date());
        demand.setSubmitTime(new Date());
        return patentBuyingDemandDao.updateById(demand) > 0;
    }

    @Override
    public Boolean savePatentSea(PatentSeaModel model, UserInfo userInfo) throws OwnerException {
        if (null == model) {
            throw new OwnerException("数据异常，请联系管理员！");
        }
        if (model.getId() != null) {
            PatentSeaEntity patentSea = patentSeaDao.selectById(model.getId());
            if (null == patentSea) {
                throw new OwnerException("该记录已删除或不存在，更新失败！");
            }
            BeanUtils.copyProperties(model, patentSea);
            patentSea.update(userInfo.getId(), new Date());
            return patentSeaDao.updateById(patentSea) > 0;
        } else {
            limitPatentNo(model);
            PatentSeaEntity patentSea = new PatentSeaEntity();
            BeanUtils.copyProperties(model, patentSea);
            patentSea.create(userInfo.getId(), new Date());
            return patentSeaDao.insert(patentSea) > 0;
        }
    }

    @Override
    public PageModel<List<PatentSeaModel>> getPatentSea(QueryPatentModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, patentSeaDao.getList(page, query, dataPerm));
    }

    @Override
    public Boolean relatedDemand(List<PatentSeaModel> models, UserInfo userInfo) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            throw new OwnerException("请选择符合条件的专利购买需求！");
        }
        List<Integer> ids = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        Integer demandId = models.get(0).getDemandId();
        PatentBuyingDemand buyingDemand = patentBuyingDemandDao.selectById(demandId);
        Date date = new Date();
        if (null == buyingDemand) {
            throw new OwnerException("专利购买需求已删除或不存在！");
        }
        List<PatentBuyingList> buyingList = new ArrayList<>();
        for (Integer id : ids) {
            PatentBuyingList patentBuyingList = new PatentBuyingList().build(buyingDemand.getCustomerId(), demandId, id, userInfo.getId(), date);
            buyingList.add(patentBuyingList);
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            patentSeaDao.relatedDemands(ids, demandId, userInfo.getId(), date);
            if (!CollectionUtils.isEmpty(buyingList)) {
                patentBuyingListDao.insertList(buyingList);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("保存失败");
        }
        return true;
    }

    @Override
    public Boolean delPatentSea(List<Integer> ids, UserInfo userInfo) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("请选择要删除的数据！");
        }
        List<PatentSeaEntity> patentSeas = patentSeaDao.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(patentSeas)) {
            throw new OwnerException("所选数据已删除或不存在，删除失败！");
        }
        List<Integer> delIds = new ArrayList<>();
        for (PatentSeaEntity entity : patentSeas) {
            if (entity.getDemandId() != null) {
                throw new OwnerException(MessageFormat.format("专利名称：{0}，专利号：{1}已关联购买需求，不能删除！", entity.getPatentName(), entity.getPatentNo()));
            }
            delIds.add(entity.getId());
        }
        return patentSeaDao.deleteBatchIds(delIds) > 0;
    }

    @Override
    public PageModel<List<PatentBuyingModel>> getBuyingList(QueryPatentModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pbl.status");
            desc.add("pbl.createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, patentBuyingListDao.getList(page, query, dataPerm));
    }


    @Override
    public Boolean buyPatent(PatentBuyingModel model, UserInfo userInfo) throws OwnerException {
        if (null == model || null == model.getId()) {
            throw new OwnerException("请选择要购买的专利！");
        }
        PatentBuyingList buyingList = patentBuyingListDao.selectById(model.getId());
        if (null == buyingList) {
            throw new OwnerException("要购买的专利不存在或已删除！");
        }
        Date date = new Date();
        if (buyingList.getStatus() != model.getStatus() - 1) {
            throw new OwnerException("操作异常，请联系管理员！");
        }
        buyingList.update(userInfo.getId(), date);
        buyingList.setStatus(model.getStatus());
        buyingList.setFilePath(StringUtils.isEmpty(model.getFilePath()) ? "" : model.getFilePath());
        //购买状态添加专利
        if (model.getStatus() == 2) {
            PatentSeaModel patentSea = patentSeaDao.getPatentSea(buyingList.getPatentSeaId());
            RsPatentEntity patent = new RsPatentEntity();
            patent.setPatentNo(patentSea.getPatentNo());
            patent.setPatentName(patentSea.getPatentName());
            String mainType;
            if (patentSea.getMainType() == 1) {
                mainType = "发明专利";
            } else if (patentSea.getMainType() == 2) {
                mainType = "实用新型";
            } else {
                mainType = "外观设计";
            }
            patent.setMainType(mainType);
            patent.setInventor(patentSea.getInventor() != null ? patentSea.getInventor() : "");
            patent.setApplyDateTime(patentSea.getApplyDateTime());
            if (null == patentSea.getCompanyId()) {
                throw new OwnerException("该客户没有企业信息，操作失败！");
            }
            patent.setCompanyId(patentSea.getCompanyId());
            patent.create(-1, userInfo.getId(), date);
            patent.setSource(1);
            rsPatentDao.insert(patent);
        }
        return patentBuyingListDao.updateById(buyingList) > 0;

    }

    @Override
    public Boolean completePurchase(PatentBuyingModel model, UserInfo userInfo) throws OwnerException {
        Integer demandId = model.getDemandId();
        Date date = new Date();
        PatentBuyingDemand demand = patentBuyingDemandDao.selectById(demandId);
        demand.update(userInfo.getId(), date);
        demand.setStatus(3);
        List<PatentSeaEntity> patents = patentSeaDao.getByDemand(demandId);
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            patentBuyingDemandDao.updateById(demand);
            if (!CollectionUtils.isEmpty(patents)) {
                List<Integer> ids = patents.stream().map(e -> e.getId()).collect(Collectors.toList());
                patentSeaDao.relatedDemands(ids, null, userInfo.getId(), date);
                patentBuyingListDao.deleteBySea(ids, demandId);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("操作失败");
        }
        kafkaQueueService.sendPatentDemand(Constant.TOPIC_PATENT_DEMAND_DONE,demandId);
        return true;
    }

    @Override
    public Boolean limitPatentNo(PatentSeaModel model) throws OwnerException {
        List<PatentSeaEntity> sea = patentSeaDao.limitPatentNo(model);
        if (!CollectionUtils.isEmpty(sea)) {
            throw new OwnerException("专利号已存在");
        }
        RsPatentEntity rsPatentEntity = rsPatentDao.queryPatentByNo(model.getPatentNo());
        if (rsPatentEntity != null) {
            throw new OwnerException("专利号已存在");
        }
        return true;
    }

    @Override
    public Boolean delDemand(List<PatentBuyingDemandModel> models, UserInfo userInfo) throws OwnerException {
        if (CollectionUtils.isEmpty(models)) {
            throw new OwnerException("请选择要删除的专利购买需求！");
        }
        List<Integer> ids = models.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<PatentBuyingDemand> demands = patentBuyingDemandDao.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(demands)) {
            throw new OwnerException("要删除的专利购买需求不存在或已删除，删除失败！");
        }
        for (PatentBuyingDemand demand : demands) {
            if (demand.getStatus() > 0) {
                throw new OwnerException("已提交的专利购买需求不能删除！");
            }
        }
        return patentBuyingDemandDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public Boolean buyBack(List<Integer> ids, UserInfo userInfo) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("请选择数据后再执行操作！");
        }
        List<PatentBuyingList> buyingList = patentBuyingListDao.selectBatchIds(ids);
        if (CollectionUtils.isEmpty(buyingList)) {
            throw new OwnerException("购买记录不存在或已删除，操作失败！");
        }
        Date date = new Date();
        List<Integer> dels = new ArrayList<>();
        List<Integer> updateSeas = new ArrayList<>();
        List<Integer> updates = new ArrayList<>();
        for (PatentBuyingList buying : buyingList) {
            if (buying.getStatus() == 0) {
                dels.add(buying.getId());
                updateSeas.add(buying.getPatentSeaId());
            } else if (buying.getStatus() == 1) {
                updates.add(buying.getId());
            } else {
                throw new OwnerException("已购买的专利不能进行该操作！");
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            if (!CollectionUtils.isEmpty(updates)) {
                patentBuyingListDao.updateStatus(updates, 0, userInfo.getId(), date);
            }
            if (!CollectionUtils.isEmpty(dels)) {
                patentBuyingListDao.deleteBatchIds(dels);
                patentSeaDao.relatedDemands(updateSeas, null, userInfo.getId(), date);
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("操作失败");
        }
        return true;
    }

    @Override
    public PageModel<List<PatentBuyingDemandModel>> getBuyDemandList(QueryDemandModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pbd.createTime");
            page.setDescs(desc);
        }
        List<PatentBuyingDemandModel> list = patentBuyingDemandDao.getBuyDemandList(page, query, dataPerm);
        if (!CollectionUtils.isEmpty(list)) {
            List<Integer> demandIds = list.stream().map(PatentBuyingDemandModel::getId).collect(Collectors.toList());
            List<PatentBuyingDemandModel> buyCntList = patentBuyingListDao.getBuyCnt(demandIds,
                    PatentMainTypeEnum.INVENTOR.getMainType(), PatentMainTypeEnum.MODEL_UTILITY.getMainType(),
                    PatentMainTypeEnum.APPEARANCE_DESIGN.getMainType());
            if (!CollectionUtils.isEmpty(buyCntList)) {
                Map<Integer, PatentBuyingDemandModel> buyCntMap = buyCntList.stream().collect(Collectors.toMap(
                        PatentBuyingDemandModel::getId, b -> b, (o, n) -> n));
                list.forEach(item -> item.setCnt(buyCntMap.get(item.getId())));
            }
        }
        return PageUtils.build(page, list);
    }

    @Override
    public BatchZipModel getExportStream(QueryPatentModel query) throws OwnerException, FileNotFoundException {
        PatentBuyingDemandModel demandInfo = patentBuyingDemandDao.getDemandCompanyYear(query.getDemandId());
        if (null == demandInfo) {
            throw new OwnerException("导出失败，未获取到当前专利需求，请稍后重试。");
        }
        List<PatentCanBuyModel> exportList = patentBuyingListDao.getExportList(query);
        if (CollectionUtils.isEmpty(exportList)) {
            throw new OwnerException("导出失败，不存在可购专利，请先添加可购专利。");
        }
        String title = MessageFormat.format("{0}-{1,number,#}可购专利列表", demandInfo.getCompanyName(), demandInfo.getYear());
        BatchZipModel result = new BatchZipModel(title);
        String curFilePath;
        String baseDir = msConfig.getUploadConfig().getDocPath();
        for (int i = 0; i < exportList.size(); i++) {
            PatentCanBuyModel curModel = exportList.get(i);
            curModel.setSeq(i + 1);
            curFilePath = curModel.getPatentSeaFile();
            if (StringUtils.hasLength(curFilePath)) {
                String[] paths = curFilePath.split(",");
                Map<String, Integer> fileNameMap = new LinkedHashMap<>();
                for (String path : paths) {
                    path = baseDir + path;
                    if (FileUtil.exist(path)) {
                        String fileName = path.substring(path.lastIndexOf(Constant.PATH_SEPARATOR) + 14);
                        // 处理重复文件名，原文件名 + (个数) + .后缀
                        if (fileNameMap.containsKey(fileName)) {
                            Integer count = fileNameMap.get(fileName) + 1;
                            fileNameMap.put(fileName, count);
                            int index = fileName.indexOf(".");
                            if (index < 0) {
                                index = fileName.length();
                            }
                            fileName = MessageFormat.format("{0}({1,number,#}){2}", fileName.substring(0, index), count, fileName.substring(index));
                        }
                        fileNameMap.put(fileName, 0);
                        result.add(curModel.getPatentNo() + Constant.PATH_SEPARATOR + fileName, new FileInputStream(path));
                    }
                }
                curModel.setFiles(String.join(",", fileNameMap.keySet()));
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("title", title);
        data.put("list", exportList);
        ByteArrayOutputStream byteOs = new ByteArrayOutputStream();
        YsExcelUtils.generalReport(data, msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "可购专利模板.xlsx", workbook -> {
            workbook.setSheetName(0, title);
            try {
                workbook.write(byteOs);
                workbook.close();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });
        InputStream firstIn = new ByteArrayInputStream(byteOs.toByteArray());
        IoUtil.close(byteOs);
        result.add(title + ".xlsx", firstIn);
        return result;
    }

    @Override
    public Boolean updateSellFile(PatentBuyingModel model, UserInfo userInfo) {
        return patentBuyingListDao.updateSellFile(model.getId(), model.getSellFile(), userInfo.getId(), new Date()) > 0;
    }

    @Override
    public Boolean setEngineer(SetPatentEngineerModel setEngineer, Integer userId) {
        return patentBuyingDemandDao.updateEngineer(setEngineer.getIds(), setEngineer.getEngineerId(), userId, new Date()) > 0;
    }

}
