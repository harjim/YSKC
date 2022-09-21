package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.util.StrUtil;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.docservice.dao.rs.DocFileFooterDao;
import com.yskc.docservice.dao.rs.EmployeeDao;
import com.yskc.docservice.dao.rs.project.ProjectDao;
import com.yskc.docservice.entity.rs.DocFileFooterEntity;
import com.yskc.docservice.entity.rs.EmployeeEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.SignatureExcel;
import com.yskc.docservice.service.rs.DocFileFooterService;
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
 * @DateTime: 2022/3/8 8:23
 * @Description:
 * @author: hsx
 */
@Service
public class DocFileFooterServiceImpl implements DocFileFooterService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DocFileFooterDao docFileFooterDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ProjectDao projectDao;

    @Override
    public String importSignature(RsUserInfo info, List<SignatureExcel> excels, Integer year) throws OwnerException {
        if (CollectionUtils.isEmpty(excels)) {
            throw new OwnerException("文件为空,请添加数据后再导入");
        }
        Date now = new Date();
        Integer companyId = info.getCompanyId();
        Date firstDay = DateUtil.getYearFirstDay(year);
        List<String> enames = new ArrayList<>();
        List<DocFileFooterEntity> entities = new ArrayList<>();
        Map<String, SignatureExcel> excelMap = excels.stream().collect(Collectors.toMap(a -> a.getRdTitle(), a -> {
            enames.add(a.getToCompile());
            enames.add(a.getApproval());
            enames.add(a.getAuditor());
            return a;
        }));
        List<ProjectEntity> projects = projectDao.getByRdTitles(excelMap.keySet(), companyId);
        ProjectEntity cycle = new ProjectEntity();
        Map<String, Integer> projectMap = projects.stream().collect(Collectors.toMap(a -> a.getRdTitle(), a -> {
            if (a.getBeginYear() > year || a.getEndYear() < year) {
                cycle.setRdTitle(a.getRdTitle());
            }
            return a.getId();
        }));
        if (cycle.getRdTitle()!=null){
            throw new OwnerException("RD【"+cycle.getRdTitle()+"】不存在【"+year+"】年，请检查！");
        }


        List<EmployeeEntity> enumbers = employeeDao.getEmployeeByEnames(companyId, enames, firstDay);
        Map<String, String> map = enumbers.stream().collect(Collectors.toMap(a -> a.getEname(), a -> a.getEnumber(), (o, n) -> o));
        int max = excels.size();
        for (int i = 0; i < max; i++) {
            SignatureExcel excel = excels.get(i);
            if (!projectMap.containsKey(excel.getRdTitle())){
                throw new OwnerException("第"+(i+2)+"行，RD【"+excel.getRdTitle()+"】不存在，请检查！");
            }
            if (!StringUtils.hasLength(excel.getApproval())&&!StringUtils.hasLength(excel.getAuditor())
                &&!StringUtils.hasLength(excel.getToCompile())){
                throw new OwnerException("第"+(i+2)+"行，人员不可都为空，请检查！");
            }
            if (StringUtils.hasLength(excel.getApproval()) &&!map.containsKey(excel.getApproval())){
                throw new OwnerException("第"+(i+2)+"行，人员【"+excel.getApproval()+"】不存在，请检查！");
            }
            if (StringUtils.hasLength(excel.getAuditor()) &&!map.containsKey(excel.getAuditor())){
                throw new OwnerException("第"+(i+2)+"行，人员【"+excel.getAuditor()+"】不存在，请检查！");
            }
            if (StringUtils.hasLength(excel.getToCompile()) &&!map.containsKey(excel.getToCompile())){
                throw new OwnerException("第"+(i+2)+"行，人员【"+excel.getToCompile()+"】不存在，请检查！");
            }

            DocFileFooterEntity entity = new DocFileFooterEntity(projectMap.get(excel.getRdTitle()),companyId,
                    map.get(excel.getAuditor()), map.get(excel.getToCompile()),map.get(excel.getApproval()),
                    info.getMsUserId(),info.getId(),now,year);
            entities.add(entity);
        }
        if (!CollectionUtils.isEmpty(entities)){
            docFileFooterDao.insertList(entities);
        }

        return "";
    }
}
