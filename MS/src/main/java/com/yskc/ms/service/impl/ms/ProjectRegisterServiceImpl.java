package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.ProjectMemberDao;
import com.yskc.ms.dao.ms.ProjectRegisterDao;
import com.yskc.ms.entity.ms.ProjectRegisterEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.project.ProjectMembersModel;
import com.yskc.ms.models.projectRegister.ProjectRegisterDataModel;
import com.yskc.ms.models.projectRegister.ProjectRegisterModel;
import com.yskc.ms.models.projectRegister.QueryRegisterModel;
import com.yskc.ms.models.projectRegister.UploadRegisterFileModel;
import com.yskc.ms.service.ms.ProjectRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/8/6 12:00
 * description:
 */
@Service
public class ProjectRegisterServiceImpl implements ProjectRegisterService {

    @Autowired
    private ProjectRegisterDao projectRegisterDao;
    @Autowired
    private ProjectMemberDao projectMemberDao;

    @Override
    public PageModel<List<ProjectRegisterDataModel>> getData(QueryRegisterModel model, DataPermModel dataPerm) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pj.lastUpdateTime");
            page.setDescs(desc);
        }
        List<ProjectRegisterDataModel> dataModels=projectRegisterDao.getData(model,page,dataPerm);
        if (!CollectionUtils.isEmpty(dataModels)) {
            List<Integer> projectIds = dataModels.stream().map(ProjectRegisterDataModel::getId).collect(Collectors.toList());
            List<ProjectRegisterModel> registerList = projectRegisterDao.getByProjectIds(projectIds);
            //项目备案列表map
            Map<Integer, List<ProjectRegisterModel>> registerMap = new HashMap<>();
            //项目最新创建备案map
            Map<Integer, ProjectRegisterModel> lastCreateMap = new HashMap<>();
            for (ProjectRegisterModel registerModel : registerList) {
                if (!registerMap.containsKey(registerModel.getProjectId())) {
                    registerMap.put(registerModel.getProjectId(), new ArrayList<>());
                }
                registerMap.get(registerModel.getProjectId()).add(registerModel);

                if (!lastCreateMap.containsKey(registerModel.getProjectId())) {
                    lastCreateMap.put(registerModel.getProjectId(), registerModel);
                } else {
                    //获取项目最新开始时间的备案
                    Long beginDate = lastCreateMap.get(registerModel.getProjectId()).getBeginDate().getTime();
                    if (registerModel.getBeginDate().getTime() > beginDate) {
                        lastCreateMap.put(registerModel.getProjectId(), registerModel);
                    }
                }
            }
            Map<Integer, List<ProjectMembersModel>> memberMap = new HashMap<>();
            //获取项目技术人员列表
            List<ProjectMembersModel> members = projectMemberDao.getByProjectIds(projectIds);
            if (!CollectionUtils.isEmpty(members)) {
                for (ProjectMembersModel member : members) {
                    if (!memberMap.containsKey(member.getProjectId())) {
                        memberMap.put(member.getProjectId(), new ArrayList<>());
                    }
                    memberMap.get(member.getProjectId()).add(member);
                }
            }
            List<ProjectRegisterModel> registers=new ArrayList();
            for (ProjectRegisterDataModel dataModel : dataModels) {
                //统计单个项目的备案数
                if (registerMap.containsKey(dataModel.getId())) {
                    List<ProjectRegisterModel> registerModels = registerMap.get(dataModel.getId());
                    dataModel.setProjectRegisters(registers);
                    dataModel.setRegisterNum(registerModels.size());
                }
                //项目技术人员
                if (memberMap.containsKey(dataModel.getId())) {
                    dataModel.setTechs(memberMap.get(dataModel.getId()));
                }
                //备案金额
                if (lastCreateMap.containsKey(dataModel.getId())) {
                    dataModel.setAmount(lastCreateMap.get(dataModel.getId()).getAmount());
                }
            }
        }
        return PageUtils.build(page,dataModels);
    }

    @Override
    public List<ProjectRegisterModel> getList(Integer projectId) {
        List<Integer> projectIds=Arrays.asList(projectId);
        List<ProjectRegisterModel> registerList = projectRegisterDao.getByProjectIds(projectIds);
        return registerList;
    }

    @Override
    public Boolean addProjectRegister(UserInfo userInfo, ProjectRegisterModel model) {
        Date date=new Date();
        ProjectRegisterEntity entity=new ProjectRegisterEntity();
        BeanUtils.copyProperties(model,entity);
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setCreatorId(userInfo.getId());
        entity.setLastUpdatorId(userInfo.getId());
        return projectRegisterDao.insert(entity)>0;
    }

    @Override
    public Boolean delProjectRegister(UserInfo userInfo, ProjectRegisterModel model) throws OwnerException {
        ProjectRegisterEntity entity=projectRegisterDao.selectById(model.getId());
        if(entity!=null){
            return  projectRegisterDao.deleteById(model.getId())>0;
        }
        throw new OwnerException("备案不存在或已删除，删除失败");
    }

    @Override
    public Boolean updateProjectRegister(UserInfo userInfo, ProjectRegisterModel model) throws OwnerException {
        ProjectRegisterEntity entity=projectRegisterDao.selectById(model.getId());
        if(entity!=null) {
            BeanUtils.copyProperties(model, entity);
            entity.setLastUpdatorId(userInfo.getId());
            entity.setLastUpdateTime(new Date());
            return projectRegisterDao.updateById(entity) > 0;
        }
        throw new OwnerException("编辑失败，请联系管理员");
    }

    @Override
    public Boolean uploadRegisterFile(UserInfo userInfo, UploadRegisterFileModel model) throws OwnerException{
        ProjectRegisterEntity registerEntity=projectRegisterDao.selectById(model.getId());
        if(CollectionUtils.isEmpty(model.getFilePaths())){
            return true;
        }
        String path= StringUtils.join(model.getFilePaths(),",");
        if(registerEntity!=null){
            if(model.getSign()==0) {
                registerEntity.setFilePath(path);
            }else {
                registerEntity.setRegisterFile(path);
            }
            registerEntity.setLastUpdateTime(new Date());
            registerEntity.setLastUpdatorId(userInfo.getId());
            return projectRegisterDao.updateById(registerEntity)>0;
        }
        throw new OwnerException("备案记录不存在或已删除，上传失败");
    }

    @Override
    public Boolean downloadFile(Integer id, String filePath,Integer sign) throws OwnerException {
        ProjectRegisterEntity entity=projectRegisterDao.selectById(id);
        if(entity!=null){
            String path;
            if(sign==1){
                path=entity.getFilePath();
           }else {
                path=entity.getRegisterFile();
           }
           List<String> filePaths=Arrays.asList(path.split(","));
            for (String file:filePaths) {
                if(file.equals(filePath)){
                    return true;
                }
            }
           throw new OwnerException("未找到该附件，无法下载");
        }
        throw new OwnerException("备案记录不存在或已删除,下载失败");
    }
}
