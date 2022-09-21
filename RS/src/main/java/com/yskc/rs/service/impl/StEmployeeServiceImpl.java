package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.dao.EmployeeDao;
import com.yskc.rs.dao.StEmployeeDao;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.StEmployeeEntity;
import com.yskc.rs.enums.EduLevelEnum;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ImportEmployeeExcel;
import com.yskc.rs.models.excel.StEmployeeExcel;
import com.yskc.rs.models.stEmployee.QueryStEmployeeModel;
import com.yskc.rs.models.stEmployee.StEmployeeExportModel;
import com.yskc.rs.models.stEmployee.StEmployeeModel;
import com.yskc.rs.service.StEmployeeService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: rs
 * @description: 科技管理人员
 * @author: wjy
 * @create: 2022-08-08 10:38
 **/
@Service
public class StEmployeeServiceImpl implements StEmployeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private StEmployeeDao stEmployeeDao;
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public PageModel<List<StEmployeeModel>> getList(QueryStEmployeeModel query, UserInfo info) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("e.enumber");
            page.setAscs(ascs);
        }

        return PageUtils.buildPageResult(page, stEmployeeDao.getList(page,query, info.getCompanyId()));
    }

    @Override
    public Boolean addStEmployee(StEmployeeModel model, UserInfo info) {
        List<String> enumbers = model.getEnumbers();
        if (!CollectionUtils.isEmpty(enumbers)){
            List<String> existNumber = stEmployeeDao.getByenumbers(info.getCompanyId(), model.getYear(), enumbers);
            Map<String, Boolean> existMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(existNumber)) {
                existNumber.forEach(enumber -> existMap.put(enumber, true));
            }
            List<StEmployeeEntity> insertList = new ArrayList<>();
            for (String enumber : enumbers) {
                if (!existMap.containsKey(enumber)) {
                    StEmployeeEntity entity = new StEmployeeEntity();
                    entity.setEnumber(enumber);
                    entity.setCompanyId(info.getCompanyId());
                    entity.setYear(model.getYear());
                    ToolUtils.entityCreate(entity,info.getUserId(),info.getMsUserId(),new Date());
                    insertList.add(entity);
                }
            }

            if (!CollectionUtils.isEmpty(insertList)){
                stEmployeeDao.addStEmployees(insertList);
            }
        }

        return true;
    }

    @Override
    public Boolean delStEmployee(BatchDeleteModel deleteModel) {
        if (CollectionUtils.isEmpty(deleteModel.getIds())){
            return true;
        }
        return stEmployeeDao.deleteBatchIds(deleteModel.getIds())>0;
    }

    @Override
    public void exportStEmployee(Integer companyId, QueryStEmployeeModel query, OutputStream out, String path) {
        List<StEmployeeExportModel> result = stEmployeeDao.getExport(companyId, query);
        for (StEmployeeExportModel model : result) {
            if (model.getEduLevel()!=null){
                model.setEduLevelStr(EduLevelEnum.getEduLevelEnum(model.getEduLevel()).getEdu());
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

    @Override
    public String importStEmployee(UserInfo info, List<StEmployeeExcel> excels, Integer year) throws OwnerException {
        if (CollectionUtils.isEmpty(excels)) {
            throw new OwnerException("文件为空,请添加数据后再导入");
        }
        List<String> enumberList = excels.stream().filter(a -> !StringUtils.isEmpty(a.getEnumber())).map(StEmployeeExcel::getEnumber).collect(Collectors.toList());
        List<String> existEnumbers = stEmployeeDao.getByenumbers(info.getCompanyId(), year, enumberList);
        Map<String, Boolean> existMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(existEnumbers)) {
            existEnumbers.forEach(enumber -> existMap.put(enumber, true));
        }
        List<EmployeeEntity> employeeModelList = employeeDao.getByNumbers(info.getCompanyId(), enumberList);
        Map<String, EmployeeEntity> existEmployeeMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(employeeModelList)) {
            employeeModelList.forEach(item -> existEmployeeMap.put(item.getEnumber(), item));
        }
        List<StEmployeeEntity> addEntities = new ArrayList<>();
        List<StEmployeeEntity> updateEntities = new ArrayList<>();

        Date date = new Date();
        int max = excels.size();
        for (int i = 0; i < max; i++) {
            StEmployeeExcel excel = excels.get(i);
            String enumber = excel.getEnumber();
            String ename = excel.getEname();
            if (!existEmployeeMap.containsKey(enumber)){
                throw new OwnerException("第"+(i+2)+"行人员数据不在人员花名册中，请重新输入！");
            }else if (!existEmployeeMap.get(enumber).getEname().equals(ename)){
                throw new OwnerException("第"+(i+2)+"行人员姓名错误，请重新输入！");
            }
            if (!existMap.containsKey(enumber)){
                StEmployeeEntity entity = new StEmployeeEntity();
                entity.setYear(year);
                entity.setCompanyId(info.getCompanyId());
                entity.setEnumber(enumber);
                ToolUtils.entityCreate(entity,info.getUserId(),info.getMsUserId(),date);
                addEntities.add(entity);
            }
        }
        if (!CollectionUtils.isEmpty(addEntities)){
            stEmployeeDao.addStEmployees(addEntities);
        }
        return "";
    }


}
