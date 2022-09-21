package com.yskc.docservice.service.impl.rs;

import com.yskc.common.exception.OwnerException;

import com.yskc.docservice.dao.rs.EmployeeDao;
import com.yskc.docservice.dao.rs.StEmployeeDao;
import com.yskc.docservice.entity.rs.EmployeeEntity;
import com.yskc.docservice.entity.rs.StEmployeeEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.StEmployeeExcel;
import com.yskc.docservice.service.rs.StEmployeeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    public String importStEmployee(RsUserInfo info, List<StEmployeeExcel> excels, Integer year) throws OwnerException {
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
            if (!existEmployeeMap.containsKey(enumber)){
                throw new OwnerException("第"+(i+2)+"行人员数据不在人员花名册中，请重新输入！");
            }
            if (!excel.getEname().equals(existEmployeeMap.get(enumber).getEname())){
                throw new OwnerException("第"+(i+2)+"行员工工号与姓名不符，请重新输入！");
            }
            if (!existMap.containsKey(enumber)){
                StEmployeeEntity entity = new StEmployeeEntity();
                entity.setYear(year);
                entity.setCompanyId(info.getCompanyId());
                entity.setEnumber(enumber);

                entity.setCreateTime(date);
                entity.setCreatorId(info.getUserId());
                entity.setMsCreatorId(info.getMsUserId());
                entity.setLastUpdateTime(date);
                entity.setLastUpdatorId(info.getUserId());
                entity.setMsLastUpdatorId(info.getMsUserId());
                addEntities.add(entity);
            }
        }
        if (!CollectionUtils.isEmpty(addEntities)){
            stEmployeeDao.addStEmployees(addEntities);
        }
        return "";
    }


}
