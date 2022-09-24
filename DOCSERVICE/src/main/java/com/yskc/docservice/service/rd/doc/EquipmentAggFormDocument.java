package com.yskc.docservice.service.rd.doc;

import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.JsonUtils;
import com.yskc.docservice.dao.rs.project.ProjectDocFileDao;
import com.yskc.docservice.dao.rs.project.ProjectRdEquipmentDao;
import com.yskc.docservice.entity.rs.project.ProjectDocFileEntity;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.rdequipment.RdEquipmentResultModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Created by hck
 * on 2020/6/15 17:47
 * description:设备使用记录导出
 */
@Component("EquipmentAggForm_Doc")
@Scope("prototype")
public class EquipmentAggFormDocument extends RDDocument {

    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;
    @Autowired
    private ProjectDocFileDao projectDocFileDao;

    @Override
    protected Map getDocMap(){
        ProjectEntity projectInfo = this.dataFactory.getProjectInfo();
        Date m = DateUtil.getDateByString((String)getJsonMap().get("equipmentMonth") + "-01 00:00:00");
        if (m != null){
            Integer year= cn.hutool.core.date.DateUtil.year(m);
            // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
            List<RdEquipmentResultModel>  equipmentList= projectRdEquipmentDao.queryEquipmentList(projectInfo.getCompanyId(), projectInfo.getId(), m,year);
            if (!CollectionUtils.isEmpty(equipmentList)) {
                int seq = 0;
                for (RdEquipmentResultModel rde : equipmentList) {
                    seq += 1;
                    rde.setNum(seq);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("equipmentList",equipmentList);
                return map;
            }
        }
        return null;
    }

    private Date getDocMonth() {
        if (this.docFile.getMonth() != null) {
            return this.docFile.getMonth();
        } else if (this.getJsonMap() != null) {
            String dStr = (String) this.getJsonMap().get("equipmentMonth");
            if (StringUtils.hasLength(dStr)) {
                return DateUtil.getDateByString(dStr + "-01 00:00:00");
            }
        }
        return null;
    }

    @Override
    protected Map getDocMap(ProjectEntity project, Integer currentYear) {
        Map<String, Object> map = new HashMap<>();
        List<RdEquipmentResultModel> equipmentList=new ArrayList<>();
        ProjectDocFileEntity docFile=projectDocFileDao.selectById(this.docFile.getId());
        if(StringUtils.isEmpty(this.docFile.getData()) && docFile.getMonth()!=null){
            equipmentList=getData(project,docFile.getMonth());
            map.put("employeeMonth", cn.hutool.core.date.DateUtil.format(docFile.getMonth(),"yyyy年MM月"));
        }else if (!StringUtils.isEmpty(this.docFile.getData())) {
            map= JsonUtils.jsonToPojo(this.docFile.getData(), Map.class);
            String dateStr = (String) map.get("equipmentMonth");
            if(!StringUtils.isEmpty(dateStr)) {
                Date date = DateUtil.getDateByString(dateStr + "-01 00:00:00");
                equipmentList=getData(project,date);
                map.put("equipmentMonth", dateStr.replace("-", "年")+"月份");
            }else if(docFile.getMonth()!=null){
                equipmentList=getData(project,docFile.getMonth());
                map.put("employeeMonth", cn.hutool.core.date.DateUtil.format(docFile.getMonth(),"yyyy年MM月"));
            }
        }
        map.put("equipmentList",equipmentList);
        return map;
    }

    private List<RdEquipmentResultModel> getData(ProjectEntity projectEntity, Date month){
        Integer year= cn.hutool.core.date.DateUtil.year(month);
        // 2021年8月10日16:56:28 姚娟 更改为只查询归集的设备
        List<RdEquipmentResultModel>  equipmentList= projectRdEquipmentDao.queryEquipmentList(projectEntity.getCompanyId(), projectEntity.getId(), month,year);
        if (!CollectionUtils.isEmpty(equipmentList)) {
            int seq = 0;
            for (RdEquipmentResultModel rde : equipmentList) {
                seq += 1;
                rde.setNum(seq);
            }
        }
       return equipmentList;
    }
}
