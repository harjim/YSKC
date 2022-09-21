package com.yskc.ms.service.impl.rs;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.InnovationMemberDao;
import com.yskc.ms.dao.ms.RsProjectMasterDao;
import com.yskc.ms.dao.ms.RsProjectSummaryDao;
import com.yskc.ms.dao.rs.CompanyGroupDao;
import com.yskc.ms.dao.rs.RsProjectDao;
import com.yskc.ms.entity.ms.RsProjectMaster;
import com.yskc.ms.entity.rs.models.ProjectListModel;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.customer.FindCustomerModel;
import com.yskc.ms.models.innovationproject.InnovationMemberSelectModel;
import com.yskc.ms.models.project.QueryProjectDetailModel;
import com.yskc.ms.models.project.RsProjectMasterModel;
import com.yskc.ms.models.project.RsProjectSummaryExportModel;
import com.yskc.ms.models.project.RsProjectSummaryModel;
import com.yskc.ms.service.rs.ProjectDetailSummaryService;
import com.yskc.ms.utils.YsExcelUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: hck
 * @DateTime: 2021/7/24 10:49
 * @Description:
 */
@Service
public class ProjectDetailSummaryServiceImpl implements ProjectDetailSummaryService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RsProjectSummaryDao rsProjectSummaryDao;
    @Autowired
    private RsProjectMasterDao rsProjectMasterDao;
    @Autowired
    private InnovationMemberDao innovationMemberDao;
    @Autowired
    private RsProjectDao rsProjectDao;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private CompanyGroupDao companyGroupDao;

    @Override
    public PageResult getList(QueryProjectDetailModel query, DataPermModel perm) {
        Pagination pagination = query.getPagination();
        List<String> ascs = pagination.getAscs();
        List<String> descs = pagination.getDescs();
        List<RsProjectSummaryModel> list = rsProjectSummaryDao.getList(pagination, query, perm,
                CollectionUtils.isEmpty(ascs)?null:ascs, CollectionUtils.isEmpty(descs)?null:descs);
        if (!CollectionUtils.isEmpty(list)) {
            List<RsProjectSummaryModel> techMasters = innovationMemberDao.getMasters(list, MemberTypeEnum.Tech.getId());
            Set<Integer> set = new HashSet<>();
            list.forEach(item->{
                set.add(item.getCompanyId());
            });
            List<FindCustomerModel> groupModels = companyGroupDao.getGroupByCompanyId(set);
            Map<Integer, FindCustomerModel> groupMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(groupModels)) {
                groupModels.forEach(item->{
                    groupMap.put(item.getId(), item);
                });
            }
            List<RsProjectSummaryModel> financeMasters = innovationMemberDao.getMasters(list, MemberTypeEnum.FINANCE.getId());
            List<ProjectListModel> workshopSections = rsProjectDao.getWorkshopSections(list);
            Map<Integer, ProjectListModel> workshopMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(workshopSections)) {
                workshopSections.forEach(item -> workshopMap.put(item.getId(), item));
            }
            Map<String, String> techMap = new HashMap<>();
            Map<String, String> financeMap = new HashMap<>();
            String format = "{0}-{1}";
            if (!CollectionUtils.isEmpty(techMasters)) {
                for (RsProjectSummaryModel master : techMasters) {
                    techMap.put(MessageFormat.format(format, master.getYear(), master.getCompanyId()), master.getRealName());
                }

            }
            if (!CollectionUtils.isEmpty(financeMasters)) {
                for (RsProjectSummaryModel master : financeMasters) {
                    financeMap.put(MessageFormat.format(format, master.getYear(), master.getCompanyId()), master.getRealName());
                }

            }
            for (RsProjectSummaryModel model : list) {
                model.setRealName(techMap.get(MessageFormat.format(format, model.getYear(), model.getCompanyId())));
                model.setFinanceName(financeMap.get(MessageFormat.format(format, model.getYear(), model.getCompanyId())));
                model.setGroupName(null == groupMap.get(model.getCompanyId())?"":groupMap.get(model.getCompanyId()).getCompanyName());
                ProjectListModel rsProject = workshopMap.get(model.getRsProjectId());
                if (null != rsProject) {
                    model.setWorkshop(rsProject.getWorkshop());
                    model.setDeptName(rsProject.getDeptName());
                    model.setFullname(rsProject.getFullname());
                    model.setDeptId(rsProject.getDeptId());
                    model.setProcessSection(rsProject.getProcessSection());
                    model.setProductLine(rsProject.getProductLine());
                    model.setBeginDate(rsProject.getBeginDate());
                    model.setEndDate(rsProject.getEndDate());
                }
            }
        }
        return PageUtils.buildPageResult(pagination, list, rsProjectSummaryDao.getTotal(query, perm));
    }

    @Override
    public Boolean saveMaster(RsProjectMasterModel model) {
        RsProjectMaster master = rsProjectMasterDao.getMaster(model);
        Date date = new Date();
        if (master != null) {
            master.setUserId(model.getUserId());
            master.setCreateTime(date);
            return rsProjectMasterDao.updateById(master) > 0;
        } else {
            master = new RsProjectMaster();
            BeanUtils.copyProperties(model, master);
            master.setCreateTime(date);
            return rsProjectMasterDao.insert(master) > 0;
        }
    }

    @Override
    public List<InnovationMemberSelectModel> getMemberList(Integer companyId, Integer year, Integer mType) {
        return innovationMemberDao.getMemberListByCompany(companyId, year, mType);
    }

    @Override
    public void exportPlan(QueryProjectDetailModel query, UserInfo info, OutputStream out, DataPermModel perm) throws OwnerException {
        String templatePath = msConfig.getUploadConfig().getResourcePath() + Constant.TEMPLATE_DIR + "立项管理表模板.xlsx";
        if (StrUtil.isEmpty(templatePath)) {
            throw new OwnerException("导出失败，无法获取模板。");
        }
        List<RsProjectSummaryModel> list = rsProjectSummaryDao.getExportList(query, perm);
        if (!CollectionUtils.isEmpty(list)) {
            List<RsProjectSummaryModel> techMasters = innovationMemberDao.getMasters(list, MemberTypeEnum.Tech.getId());
            List<RsProjectSummaryModel> financeMasters = innovationMemberDao.getMasters(list, MemberTypeEnum.FINANCE.getId());
            Set<Integer> set = new HashSet<>();
            list.forEach(item->{
                set.add(item.getCompanyId());
            });
            List<FindCustomerModel> groupModels = companyGroupDao.getGroupByCompanyId(set);
            Map<Integer, FindCustomerModel> groupMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(groupModels)) {
                groupModels.forEach(item->{
                    groupMap.put(item.getId(), item);
                });
            }
            List<ProjectListModel> workshopSections = rsProjectDao.getWorkshopSections(list);
            Map<Integer, ProjectListModel> workshopMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(workshopSections)) {
                workshopSections.forEach(item -> workshopMap.put(item.getId(), item));
            }
            Map<String, String> techMap = new HashMap<>();
            Map<String, String> financeMap = new HashMap<>();
            String format = "{0}-{1}";
            if (!CollectionUtils.isEmpty(techMasters)) {
                for (RsProjectSummaryModel master : techMasters) {
                    techMap.put(MessageFormat.format(format, master.getYear(), master.getCompanyId()), master.getRealName());
                }

            }
            if (!CollectionUtils.isEmpty(financeMasters)) {
                for (RsProjectSummaryModel master : financeMasters) {
                    financeMap.put(MessageFormat.format(format, master.getYear(), master.getCompanyId()), master.getRealName());
                }

            }
            List<RsProjectSummaryExportModel> exportList = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format1 = "{0}/{1}";
            try {
                for (RsProjectSummaryModel model : list) {
                    model.setRealName(techMap.get(MessageFormat.format(format, model.getYear(), model.getCompanyId())));
                    model.setFinanceName(financeMap.get(MessageFormat.format(format, model.getYear(), model.getCompanyId())));
                    ProjectListModel rsProject = workshopMap.get(model.getRsProjectId());
                    if(null != rsProject){
                        model.setWorkshop(rsProject.getWorkshop());
                        model.setDeptName(rsProject.getDeptName());
                        String fullName;
                        if (rsProject.getDeptId()==null){
                            List<String> fullNames = new ArrayList<>();
                            fullNames.add(rsProject.getDeptName());
                            fullNames.add(rsProject.getWorkshop());
                            fullNames.add(rsProject.getProductLine());
                            fullNames.add(rsProject.getProcessSection());
                            fullNames.removeAll(Collections.singleton(null));
                            fullNames.removeAll(Collections.singleton(""));
                            fullName = String.join("/", fullNames);
                        }else {
                            fullName = rsProject.getFullname();
                        }
                        model.setFullname(fullName);
                        model.setProductLine(rsProject.getProductLine());
                        model.setProcessSection(rsProject.getProcessSection());
                        model.setBeginDate(rsProject.getBeginDate());
                        model.setEndDate(rsProject.getEndDate());
                    }
                    RsProjectSummaryExportModel exportModel = new RsProjectSummaryExportModel();
                    BeanUtils.copyProperties(model,exportModel);
                    exportModel.setGroupName(null == groupMap.get(model.getCompanyId())?"":groupMap.get(model.getCompanyId()).getCompanyName());
                    exportModel.setBeginAndEnd((null ==model.getBeginDate()?"":dateFormat.format(model.getBeginDate()))+"~"+ (null == model.getEndDate()?"":dateFormat.format(model.getEndDate())));
                    exportModel.setMemberCnt(null == model.getMemberCnt()?"-":model.getMemberCnt()+"");
                    exportModel.setEquipmentCnt(null == model.getEquipmentCnt()?"-":model.getEquipmentCnt()+"");
                    exportModel.setWorkshop(MessageFormat.format(format1,StringUtils.isEmpty(model.getWorkshop())?"-":model.getWorkshop(),
                            StringUtils.isEmpty(model.getProcessSection())?"-":model.getProcessSection()));
                    exportModel.setPatentCnt(null == model.getPatentCnt()?"-":model.getPatentCnt()+"");

                    if (!model.getHasReport()) {
                        exportModel.setHasReport("无");
                    }else{
                        exportModel.setHasReport(getStatusMap().get(model.getReportStatus()));
                    }
                    if (!model.getHasNewReport()) {
                        exportModel.setHasNewReport("无");
                    }else{
                        exportModel.setHasNewReport(getStatusMap().get(model.getNewReportStatus()));
                    }

                    exportModel.setDocCnt((null == model.getDocTotal()?"-":model.getDocTotal()+"")+"/"+
                            (null == model.getDocSubmitCnt()?"-":model.getDocSubmitCnt()+"")+"/"+
                            (null == model.getDocDoneCnt()?"-":model.getDocDoneCnt()+""));
                    exportModel.setBackupDataTotalCnt(null == model.getBackupDataTotalCnt()?"-":model.getBackupDataTotalCnt()+"");
                    exportList.add(exportModel);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("list", exportList);

            RsProjectSummaryModel total = rsProjectSummaryDao.getTotal(query, perm);
            RsProjectSummaryExportModel totalModel = new RsProjectSummaryExportModel();
            totalModel.setDocTotal((null == total.getDocTotal()?"-":total.getDocTotal()+"")+"/"+
                    (null == total.getDocSubmitCnt()?"-":total.getDocSubmitCnt()+"")+"/"+
                    (null == total.getDocDoneCnt()?"-":total.getDocDoneCnt()+""));
            totalModel.setMemberCnt(null == total.getMemberCnt()?"-": total.getMemberCnt()+"");
            totalModel.setEquipmentCnt(null ==total.getEquipmentCnt()?"-": total.getEquipmentCnt()+"");
            totalModel.setPatentCnt(null == total.getPatentCnt()?"-":total.getPatentCnt()+"");
//            totalModel.setDocTotal(null == total.getDocTotal()?"":total.getDocTotal()+"");
            totalModel.setBackupDataTotalCnt(null == total.getBackupDataTotalCnt()?"-": total.getBackupDataTotalCnt()+"");
            dataMap.put("total", totalModel);
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
        } else {
            throw new OwnerException("导出失败，数据为空。");
        }
    }

    public Map<Integer, String> getStatusMap() {
        Map<Integer, String> map = new HashMap<>(9);
        map.put(0, "进行中");
        map.put(1, "通过");
        map.put(2, "驳回");
        map.put(3, "激活");
        map.put(4, "提交");
        map.put(5, "未提交");
        map.put(6, "审核失败");
        map.put(7, "提交失败");
        map.put(999, "等待中");
        return map;
    }
}
