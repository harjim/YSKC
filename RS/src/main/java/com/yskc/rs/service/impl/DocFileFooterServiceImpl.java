package com.yskc.rs.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.config.RsConfig;
import com.yskc.rs.dao.DocFileFooterDao;
import com.yskc.rs.dao.EmployeeDao;
import com.yskc.rs.entity.DocFileFooterEntity;
import com.yskc.rs.entity.tech.BeianEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.docFile.QueryDocFileFooterModel;
import com.yskc.rs.models.docFile.DocFileFooterModel;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.tech.TechEquipmentModel;
import com.yskc.rs.service.DocFileFooterService;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.OutputStream;
import java.util.*;

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
    private RsConfig rsConfig;

    @Override
    public PageModel<List<DocFileFooterModel>> getList(QueryDocFileFooterModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pdf.createTime");
            page.setDescs(desc);
        }
        List<String> rdTitles = model.getRdTitles();
        List<DocFileFooterModel> list = docFileFooterDao.getList(page,model,CollectionUtils.isEmpty(rdTitles)?null:rdTitles);
        if (!CollectionUtils.isEmpty(list)){
            List<EmployeeModel> employeeList = employeeDao.getEmployeeList(model.getCompanyId());
            if (!CollectionUtils.isEmpty(employeeList)){
                Map<String,String> map = new HashMap<>();
                employeeList.forEach(item->{
                    map.put(item.getEnumber(),item.getEname());
                });
                for (DocFileFooterModel footerModel : list) {
                    footerModel.setApproval(map.get(footerModel.getApprovalEnumber()));
                    footerModel.setAuditor(map.get(footerModel.getAuditEnumber()));
                    footerModel.setToCompile(map.get(footerModel.getToCompileEnumber()));
                }
            }
        }
        return PageUtils.build(page,list);
    }

    @Override
    public Boolean addDocFileFooter(DocFileFooterModel model, UserInfo info) {
        DocFileFooterEntity entity = new DocFileFooterEntity(model.getProjectId(), info.getCompanyId(), model.getAuditEnumber(),
                model.getToCompileEnumber(), model.getApprovalEnumber(), info.getMsUserId(), info.getId(), new Date(), model.getYear());
        return docFileFooterDao.insertOrUpdate(entity)>0;
    }

    @Override
    public Boolean editDocFileFooter(DocFileFooterModel model, UserInfo info) {
        DocFileFooterEntity entity = new DocFileFooterEntity();
        entity.setId(model.getId());
        entity.setApprovalEnumber(model.getApprovalEnumber());
        entity.setToCompileEnumber(model.getToCompileEnumber());
        entity.setAuditEnumber(model.getAuditEnumber());
        entity.setLastUpdateTime(new Date());
        entity.setLastUpdatorId(info.getId());
        entity.setMsLastUpdatorId(info.getMsUserId());
        return docFileFooterDao.updateById(entity)>0;
    }

    @Override
    public void exportEquipment(QueryDocFileFooterModel model, UserInfo info, OutputStream out) throws OwnerException{
        String templatePath = rsConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "项目签名汇总表.xls";
        if (StrUtil.isEmpty(templatePath)) {
            throw new OwnerException("导出失败，无法获取模板。");
        }
        List<String> rdTitles = model.getRdTitles();
        List<DocFileFooterModel> list = docFileFooterDao.getList(model,CollectionUtils.isEmpty(rdTitles)?null:rdTitles);
        if (!CollectionUtils.isEmpty(list)){
            List<EmployeeModel> employeeList = employeeDao.getEmployeeList(model.getCompanyId());
            if (!CollectionUtils.isEmpty(employeeList)){
                Map<String,String> map = new HashMap<>();
                employeeList.forEach(item->{
                    map.put(item.getEnumber(),item.getEname());
                });
                for (DocFileFooterModel footerModel : list) {
                    footerModel.setApproval(map.get(footerModel.getApprovalEnumber()));
                    footerModel.setAuditor(map.get(footerModel.getAuditEnumber()));
                    footerModel.setToCompile(map.get(footerModel.getToCompileEnumber()));
                }
            }
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("list", list);
        dataMap.put("companyName", info.getCompanyName());
        dataMap.put("year",model.getYear());
        YsExcelUtils.generalReport(dataMap, templatePath, (workbook) -> {
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
