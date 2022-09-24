package com.yskc.ms.service.impl.rs;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProjectDao;
import com.yskc.ms.dao.rs.BeianDao;
import com.yskc.ms.dao.rs.SysDictionaryDao;
import com.yskc.ms.entity.rs.models.SysDictionaryEntityModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.tech.BeianExportModel;
import com.yskc.ms.models.tech.BeianInfoModel;
import com.yskc.ms.models.tech.QueryBeianModel;
import com.yskc.ms.service.rs.BeianService;
import com.yskc.ms.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: hck
 * @DateTime: 2021/3/27 8:51
 * @Description:
 */
@Service
public class BeianServiceImpl implements BeianService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BeianDao beianDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private SysDictionaryDao sysDictionaryDao;

    @Override
    public PageModel<List<BeianExportModel>> getList(QueryBeianModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("p.lastUpdateTime");
            page.setDescs(descs);
        }
        List<BeianExportModel> beians = projectDao.getBeianProjects(page, dataPerm, query);
        List<BeianExportModel> result = new ArrayList<>();
        List<Integer> companyIds = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        Map<Integer,List<String>> keyMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(beians)) {
            List<Integer> projectIds = beians.stream().map(e ->{
                List<String> split = Arrays.asList(e.getAddressCode().split(","));
                keys.addAll(split);
                keyMap.put(e.getProjectId(),split);
                return e.getProjectId();
            }).collect(Collectors.toList());
            List<BeianExportModel> beianModels = beianDao.getList(projectIds);
            Map<Integer, List<BeianExportModel>> dataMap = new HashMap<>();
            for (BeianExportModel model : beianModels) {
                if (!dataMap.containsKey(model.getProjectId())) {
                    dataMap.put(model.getProjectId(), new ArrayList<>());
                }
                dataMap.get(model.getProjectId()).add(model);
            }
            List<SysDictionaryEntityModel> addressList = sysDictionaryDao.getAddress(keys, 1);
            Map<String, String> addressMap = addressList.stream().collect(Collectors.toMap(a -> a.getKey(), a -> a.getValue()));
            for (BeianExportModel beian : beians) {
                StringBuilder address = new StringBuilder();
                List<String> list = keyMap.get(beian.getProjectId());
                for (String key : list) {
                    address.append(addressMap.get(key)==null?"":addressMap.get(key));
                }
                if (!dataMap.containsKey(beian.getProjectId())) {
                    beian.setAddress(address.toString());
                    result.add(beian);
                } else {
                    List<BeianExportModel> infoModels = dataMap.get(beian.getProjectId());
                    for (BeianExportModel exportModel : infoModels) {
                        exportModel.setId(beian.getId());
                        exportModel.setCompanyName(beian.getCompanyName());
                        exportModel.setProductName(beian.getProductName());
                        exportModel.setCompanyId(beian.getCompanyId());
                        exportModel.setCustomerId(beian.getCustomerId());
                        exportModel.setDeptName(beian.getDeptName());
                        exportModel.setAddress(address.toString());
                        exportModel.setCompanyLevel(beian.getCompanyLevel());
                        if (exportModel.getBeginDate()!=null&&exportModel.getEndDate()!=null){
                            exportModel.setDate(DateUtil.format(exportModel.getBeginDate(),"yyyy-MM-dd")+"~"+
                                    DateUtil.format(exportModel.getEndDate(),"yyyy-MM-dd"));
                        }
                        exportModel.setChange(exportModel.getChangedCnt()>0?"是":"否");
                        exportModel.setTaxAmountDetail(exportModel.getDetail(exportModel.getTaxAmount()));
                        exportModel.setAmountDetail(exportModel.getDetail(exportModel.getAmount()));
                        result.add(exportModel);
                    }
                }
            }
            companyIds = result.stream().map(e -> e.getCompanyId()).distinct().collect(Collectors.toList());
        }
        return PageUtils.buildPageResult(page, result, null, countData(companyIds));
    }

    @Override
    public void getExport(QueryBeianModel query, DataPermModel dataPerm, OutputStream out, String path) throws OwnerException {
        List<BeianExportModel> models = projectDao.getBeianExport(dataPerm, query);
        List<BeianExportModel> result = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        Map<Integer,List<String>> keyMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(models)){
            Map<Integer, BeianExportModel> map = models.stream().collect(Collectors.toMap(a -> a.getProjectId(), a -> {
                List<String> split = Arrays.asList(a.getAddressCode().split(","));
                keys.addAll(split);
                keyMap.put(a.getProjectId(),split);
                return a;
            }));
            List<BeianExportModel> export = beianDao.getExport(map.keySet());
            Map<Integer,List<BeianExportModel>> beianMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(export)){
                beianMap = export.stream().collect(Collectors.groupingBy(BeianInfoModel::getProjectId));
            }
            List<SysDictionaryEntityModel> addressList = sysDictionaryDao.getAddress(keys, 1);
            Map<String, String> addressMap = addressList.stream().collect(Collectors.toMap(a -> a.getKey(), a -> a.getValue()));
            Integer num = 0;
            for (Integer projectId : map.keySet()) {
                BeianExportModel model = map.get(projectId);
                StringBuilder address = new StringBuilder();
                List<String> list = keyMap.get(projectId);
                for (String key : list) {
                    address.append(addressMap.get(key)==null?"":addressMap.get(key));
                }

                if (!beianMap.containsKey(projectId)){
                    model.setAddress(address.toString());
                    num++;
                    model.setNum(num);
                    result.add(model);
                }else {
                    List<BeianExportModel> modelList = beianMap.get(projectId);
                    for (BeianExportModel exportModel : modelList) {
                        exportModel.setId(model.getId());
                        exportModel.setCompanyName(model.getCompanyName());
                        exportModel.setProductName(model.getProductName());
                        exportModel.setCompanyId(model.getCompanyId());
                        exportModel.setCustomerId(model.getCustomerId());
                        exportModel.setDeptName(model.getDeptName());
                        exportModel.setAddress(address.toString());
                        exportModel.setCompanyLevel(model.getCompanyLevel());
                        if (exportModel.getBeginDate()!=null&&exportModel.getEndDate()!=null){
                            exportModel.setDate(DateUtil.format(exportModel.getBeginDate(),"yyyy-MM-dd")+"~"+
                                    DateUtil.format(exportModel.getEndDate(),"yyyy-MM-dd"));
                        }
                        exportModel.setChange(exportModel.getChangedCnt()>0?"是":"否");
                        exportModel.setTaxAmountDetail(exportModel.getDetail(exportModel.getTaxAmount()));
                        exportModel.setAmountDetail(exportModel.getDetail(exportModel.getAmount()));
                        num++;
                        exportModel.setNum(num);
                        result.add(exportModel);
                    }
                }
            }
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("result", result);
            YsExcelUtils.generalReport(dataMap, path, (workbook) -> {
                if (workbook != null) {
                    try {
                        workbook.write(out);
                        workbook.close();
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            });
        }



    }

    public Map<String, Object> countData(List<Integer> companyIds) {
        List<BeianInfoModel> models = beianDao.getData(companyIds);
        Integer beianNum = 0;
        Integer complateNum = 0;
        BigDecimal totalBudget = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        if (!CollectionUtils.isEmpty(models)) {
            beianNum = models.size();
            for (BeianInfoModel model : models) {
                if (model.getCompleteRate() != null && model.getCompleteRate().compareTo(new BigDecimal(80)) > 0) {
                    complateNum += 1;
                }
                totalBudget = totalBudget.add(getTotal(model.getEquipment(), model.getInitWorkCapital(), model.getConstruction()));
                totalAmount = totalAmount.add(getTotal(model.getEquipmentCost(), model.getConstructionCost(), model.getInitWorkCapitalCost()));
            }
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("beianNum", beianNum);
        dataMap.put("complateNum", complateNum);
        dataMap.put("totalBudget", totalBudget);
        dataMap.put("totalAmount", totalAmount);
        return dataMap;
    }

    private BigDecimal getTotal(BigDecimal pra, BigDecimal prb, BigDecimal prc) {
        if (pra == null) {
            pra = BigDecimal.ZERO;
        }
        if (prb == null) {
            prb = BigDecimal.ZERO;
        }
        if (prc == null) {
            prc = BigDecimal.ZERO;
        }
        return pra.add(prb).add(prc);
    }
}
