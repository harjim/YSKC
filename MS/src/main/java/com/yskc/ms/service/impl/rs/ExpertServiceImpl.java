package com.yskc.ms.service.impl.rs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.CommonUtils;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.expert.QueryExpertModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.dao.rs.ExpertDao;
import com.yskc.ms.entity.rs.Expert;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.expert.ExpertModel;
import com.yskc.ms.service.rs.ExpertService;
import cn.hutool.core.bean.BeanUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;


/**
 * 菜单服务
 *
 * @author huronghua
 */
@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    private ExpertDao expertDao;
    @Autowired
    private MsConfig msConfig;
    private File photoUrlFile;


    @Override
    public String getMaxExpertNumber() {
        String modelStr = expertDao.getMaxExpertNumber();
        return CommonUtils.getNewYskcNum(modelStr);
    }

    @Override
    public PageModel<List<ExpertModel>> queryExpert(QueryExpertModel query, DataPermModel perm, Date beighDate, Date endDate) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("expertNumber");
            page.setDescs(desc);
        }
        if (!StringUtils.isEmpty(query.getIndustryCode())) {
            query.setIndustryCode(query.getIndustryCode().trim());
        }
        List<Expert> experts = expertDao.queryExpert(page,query,perm,beighDate, endDate);
        List<ExpertModel> expertModels = new ArrayList<>();
        for (int i = 0; i < experts.size(); i++) {
            Expert expert = experts.get(i);
            ExpertModel expertModel = new ExpertModel();
            expertModel.setId(expert.getId());
            expertModel.setExpertNumber(expert.getExpertNumber());
            expertModel.setRealName(expert.getRealName());
            expertModel.setPhotoUrl(MessageFormat.format("{0}/images/expert/{1}",msConfig.getExpertConfig().getDomainName(),expert.getPhotoUrl()));
            expertModel.setBirthday(expert.getBirthday());
            expertModel.setLevel(expert.getLevel());
            expertModel.setValidDate(expert.getValidDate());
            expertModel.setIssueDate(expert.getIssueDate());
            expertModel.setIdNumber(expert.getIdNumber());
            expertModel.setGender(expert.getGender());
            expertModel.setIndustryCode(expert.getIndustryCode());
            expertModel.setPhone(expert.getPhone());
            expertModel.setEmail(expert.getEmail());
            expertModel.setPolicitalStatus(expert.getPolicitalStatus());
            expertModel.setEduLevel(expert.getEduLevel());
            expertModel.setAddress(expert.getAddress());
            expertModel.setPostcode(expert.getPostcode());
            expertModel.setWorkHistory(expert.getWorkHistory());
            expertModel.setPartHistory(expert.getPartHistory());
            expertModel.setHonour(expert.getHonour());
            expertModel.setStatus(expert.getStatus());
            expertModel.setRemark(expert.getRemark());
            expertModel.setUuid(expert.getUuid());
            expertModel.setQrcode(expert.getQrcode());
            expertModels.add(expertModel);
            expertModel.setViewUrl(MessageFormat.format("{0}/expert/detail.html?{1}",msConfig.getExpertConfig().getDomainName(),expert.uuid));
        }
        return PageUtils.build(page, expertModels);
    }

    @Override
    public boolean addExpert(UserInfo info, ExpertModel model) {
        try {
            Expert expert = new Expert();
            BeanUtil.copyProperties(model, expert);

//            String[] industryCodeArr = model.getIndustryCode();
//            String icode = "";
//            for (int i = 0; i < industryCodeArr.length; i++) {
//                if (i == industryCodeArr.length - 1) {
//                    icode += industryCodeArr[i];
//                } else {
//                    icode += industryCodeArr[i] + ",";
//                }
//            }
            String uuid = IdUtil.randomUUID();
            expert.setUuid(uuid);
            expert.setCreatorId(info.getId());
            expert.setCreateTime(new Date());
            expert.setLastUpdatorId(info.getId());
            expert.setLastUpdateTime(new Date());
            expert.setStatus(0);
            String viewUrl = MessageFormat.format("{0}/expert/detail.html?{1}",msConfig.getExpertConfig().getDomainName(),uuid);
            BufferedImage img = QrCodeUtil.generate(viewUrl, 300, 300);
            String fileName = uuid + ".png";
            String expertPath = msConfig.getExpertConfig().getExpertPath();
            File expertDir = new File(expertPath);
            if (!expertDir.exists()) {
                expertDir.mkdirs();
            }
            String filePath = expertPath + "/" + fileName;
            ImageIO.write(img, "png", new File(filePath));
            img.flush();
            expert.setQrcode(fileName);
            return expertDao.insert(expert) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateExpertStatus(UserInfo info, ExpertModel model) {
        Expert expert = new Expert();
        expert.setRemark(model.getRemark());
        expert.setStatus(model.getStatus());
        expert.setId(model.getId());
        expert.setLastUpdatorId(info.getId());
        expert.setLastUpdateTime(new Date());
        return expertDao.updateStatus(expert) > 0;
    }

    @Override
    public boolean delExpert(ExpertModel model) {
        return expertDao.deleteById(model.getId()) > 0;
    }

    @Override
    public boolean updateExpert(UserInfo info, ExpertModel model) {
        Expert expert = new Expert();
        BeanUtil.copyProperties(model, expert);
//        String[] industryCodeArr = model.getIndustryCode();
//        String icode = "";
//        for (int i = 0; i < industryCodeArr.length; i++) {
//            if (i == industryCodeArr.length - 1) {
//                icode += industryCodeArr[i];
//            } else {
//                icode += industryCodeArr[i] + ",";
//            }
//        }
        expert.setRemark(model.getRemark());
        expert.setStatus(model.getStatus());
//        expert.setIndustryCode(icode);
        expert.setId(model.getId());
        expert.setLastUpdatorId(info.getId());
        expert.setLastUpdateTime(new Date());
        return expertDao.updateById(expert) > 0;
    }

    @Override
    public Expert getExpertByUuid(String expertUuid) {
        return expertDao.getExpertByUuid(expertUuid);
    }

    @Override
    public Expert getExpertById(int id) {
        Expert expert = expertDao.selectById(id);
//       if(!StringUtils.isEmpty(expert)){
//
//       }
        return expert;
    }
}
