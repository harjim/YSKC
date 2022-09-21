package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.CustomerDao;
import com.yskc.ms.dao.ms.PatentApplicationDao;
import com.yskc.ms.dao.ms.PatentPlanDao;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.patent.PatentDemandModel;
import com.yskc.ms.models.patent.PatentMemberModel;
import com.yskc.ms.models.patent.QueryPatentApplicationModel;
import com.yskc.ms.models.patentPlan.PatentPlanModel;
import com.yskc.ms.models.patentPlan.PatentPlanStatModel;
import com.yskc.ms.models.patentPlan.QueryPatentPlanModel;
import com.yskc.ms.models.project.BatchStaffModel;
import com.yskc.ms.service.ms.PatentApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2022-02-09 09:25
 **/
@Service
public class PatentApplicationServiceImpl implements PatentApplicationService {

    @Autowired
    private PatentApplicationDao applicationDao;
    @Autowired
    private PatentPlanDao patentPlanDao;
    @Autowired
    private CustomerDao customerDao;

    @Override
    public PageModel<List<PatentDemandModel>> getList(QueryPatentApplicationModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        List<PatentDemandModel> demandList = applicationDao.getList(page,query,dataPerm);
        if (CollectionUtils.isEmpty(demandList)) {
            return null;
        }
        List<Integer> demandIds = new ArrayList<>();
        Map<Integer, String> groupMap = new HashMap<>();
        demandList.forEach(item->{
            demandIds.add(item.getId());
            if (item.getGroupId()!=null && item.getGroupId()>0) {
                groupMap.putIfAbsent(item.getGroupId(), "");
            }
        });
        // 根据需求Id取出当前需求的成员
        List<PatentMemberModel> members = applicationDao.getMemberList(demandIds);
        // demandId_type为KEY
        Map<String,PatentMemberModel> memberMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(members)){
            for (PatentMemberModel m:
                 members) {
                String mKey = String.format("%d_%d",m.getDemandId(),m.getmType());
                memberMap.put(mKey,m);
            }
        }
        // 根据集团Id取出集团的名称
        if (!CollectionUtils.isEmpty(groupMap)) {
            List<MiniModel> groups = customerDao.getGroupList(groupMap.keySet());
            groups.forEach(item -> groupMap.put(item.getId(), item.getTitle()));
        }
        // 统计当前需求实际申请数
        List<PatentPlanStatModel> statList = patentPlanDao.getPatentStatList(demandIds);
        Map<Integer,PatentPlanStatModel> statMap = new HashMap<>();
        for (PatentPlanStatModel stat:
             statList) {
            statMap.put(stat.getDemandId(),stat);
        }
        // 对列表中记录的业务员,工程师,集团名称进行赋值
        for (PatentDemandModel patentDemandModel : demandList) {
            String tKey = String.format("%d_%d", patentDemandModel.getId(), MemberTypeEnum.Tech.getId());
            String oKey = String.format("%d_%d", patentDemandModel.getId(), MemberTypeEnum.Saler.getId());
            if (memberMap.get(tKey)!=null){
                patentDemandModel.setTechName(memberMap.get(tKey).getMemberName());
            }
            if (memberMap.get(oKey)!=null){
                patentDemandModel.setOwnerName(memberMap.get(oKey).getMemberName());
                patentDemandModel.setOwnerId(memberMap.get(oKey).getMemberId());
            }
            if (patentDemandModel.getGroupId()!=null && groupMap.get(patentDemandModel.getGroupId())!=null) {
                patentDemandModel.setGroupName(groupMap.get(patentDemandModel.getGroupId()));
            }
            PatentPlanStatModel stat = statMap.get(patentDemandModel.getId());
            if (stat!=null){
                patentDemandModel.setNoOfPlan(stat.getNoOfplan());
            }
        }

        return PageUtils.build(page,demandList);
    }

    @Override
    public boolean upload(String type, int id, String path) {
        String pathStr = applicationDao.getPath(type,id);
        StringJoiner sj = new StringJoiner(",");
        if(pathStr!=null&&!pathStr.equals("")){
            sj.add(pathStr);
        }
        sj.add(path);
        boolean isUpload = applicationDao.updatePath(type,id,sj.toString())>0;
        return isUpload;
    }

    @Override
    public boolean del(String type, int id, String path) {
        String pathStr = applicationDao.getPath(type,id);
        String[] paths = pathStr.split(",");
        StringJoiner sj = new StringJoiner(",");
        for(String str:paths){
            if(!str.equals(path)){
                sj.add(str);
            }
        }
        if(sj.length()==0){
            return applicationDao.updatePath(type,id,null)>0;
        }
        boolean update = applicationDao.updatePath(type,id,sj.toString())>0;
        return update;
    }

    @Override
    public boolean setEngineer(BatchStaffModel model) {
        List<Integer> ids = model.getProjectIds();
        List<Integer> members = model.getStaffIds();
        List<PatentMemberModel> list = new ArrayList<>();
        if(!ids.isEmpty()&&!members.isEmpty()){
            for(Integer id : ids){
                for(Integer member : members){
                    PatentMemberModel model1 = new PatentMemberModel();
                    model1.setId(id);
                    model1.setMemberId(member);
                    model1.setmType(1);
                    list.add(model1);
                }
            }
        }
        if(model.getAdd()==false){
            applicationDao.delMember(ids,1);
        }else {
            List<PatentMemberModel> l = applicationDao.selectMember();
            list.removeAll(l);
        }
        return applicationDao.setMember(list)>0;
    }

    @Override
    public boolean setOwner(BatchStaffModel model) {
        List<Integer> ids = model.getProjectIds();
        List<Integer> members = model.getStaffIds();
        List<PatentMemberModel> list = new ArrayList<>();
        if(!ids.isEmpty()&&!members.isEmpty()){
            for(Integer id : ids){
                for(Integer member : members){
                    PatentMemberModel model1 = new PatentMemberModel();
                    model1.setId(id);
                    model1.setMemberId(member);
                    model1.setmType(3);
                    list.add(model1);
                }
            }
        }
        if(model.getAdd()==false){
            applicationDao.delMember(ids,3);
        }else {
            List<PatentMemberModel> l = applicationDao.selectMember();
            list.removeAll(l);
        }
        return applicationDao.setMember(list)>0;
    }

    @Override
    public PageModel<List<PatentPlanModel>> getPlanList(QueryPatentPlanModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("pp.createTime");
            page.setDescs(desc);
        }
        List<PatentPlanModel> models = patentPlanDao.getPlanList(page, query);
        if (!CollectionUtils.isEmpty(models)) {
            for (PatentPlanModel item : models) {
                if(Objects.equals(item.getStatus(),2)){
                    item.setNodeNumber(null);
                }
            }
        }
        return PageUtils.build(page, models);
    }
}