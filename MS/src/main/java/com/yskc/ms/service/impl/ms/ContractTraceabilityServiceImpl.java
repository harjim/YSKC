package com.yskc.ms.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.ContractTraceabilityDao;
import com.yskc.ms.dao.ms.CustomerDao;
import com.yskc.ms.entity.ms.ContractTraceability;
import com.yskc.ms.entity.ms.models.ContractTraceabilityModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.QueryContractTraceabilityModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.ms.ContractTraceabilityService;
import com.yskc.ms.utils.ToolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: wangxing
 * @CreateTime: 2020-03-09 10:41
 * @Description: ContractTraceabilityServiceImpl
 */
@Service
public class ContractTraceabilityServiceImpl implements ContractTraceabilityService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ContractTraceabilityDao traceabilityDao;
    @Autowired
    private MsConfig msConfig;
    @Autowired
    private CustomerDao customerDao;

    @Override
    public PageModel<List<ContractTraceabilityModel>> queryConTraceabilityList(QueryContractTraceabilityModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("ct.createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, traceabilityDao.queryConTraceability(page, query, dataPerm));
    }

    @Override
    public boolean addConTraceability(UserInfo info, ContractTraceability ct) throws OwnerException {
        if (StringUtils.isEmpty(ct.getThecover())) {
            ct.setThecover("");
        }
        Date now = new Date();
        Integer userId = info.getId();
        ToolUtil.entityCreate(ct,userId,now);
        ct.setQrcode(generateQrCode(ct.getContractNumber()));
        return traceabilityDao.insert(ct) > 0;
    }

    @Override
    public boolean updateConTraceability(UserInfo info, ContractTraceabilityModel model) {
        ContractTraceability traceability = new ContractTraceability();
        BeanUtil.copyProperties(model, traceability);
        traceability.setLastUpdatorId(info.getId());
        traceability.setLastUpdateTime(new Date());
        return traceabilityDao.updateById(traceability) > 0;
    }

    @Override
    public boolean checkContractNo(String contractNo) {
        return traceabilityDao.countByContractNo(contractNo) <= 0;
    }

    @Override
    public Boolean delete(Integer id) {
        return traceabilityDao.deleteById(id) > 0;
    }

    @Override
    public String generateTraceability(UserInfo info, Integer customerId, String contractNo)throws OwnerException {
        if(!checkContractNo(contractNo)){
            return null;
        }
       String companyName =  customerDao.getCompanyName(customerId);
       ContractTraceability ct =  ContractTraceability.build(contractNo,customerId,companyName,"");
       addConTraceability(info,ct);
        return ct.getQrcode();
    }


    private String generateQrCode(String contractNo)throws OwnerException{
        try {
            String viewUrl = msConfig.getExpertConfig().getDomainName() + "/contract/detail.html?" + contractNo;
            BufferedImage img = QrCodeUtil.generate(viewUrl, 120, 120);
            String qrImages = "/qrImages/";
            String fileName = contractNo.replaceAll(Constant.PATH_SEPARATOR, Constant.HYPHEN)+ ".png";
            String thecoverPath = msConfig.getUploadConfig().getImagePath() + qrImages;
            File expertDir = new File(thecoverPath);
            if (!expertDir.exists()) {
                expertDir.mkdirs();
            }
            String qrPath = thecoverPath + fileName;
            ImageIO.write(img, "png", new File(qrPath));
            img.flush();
            return qrImages + fileName;
        } catch (Exception e) {
            String msg = MessageFormat.format("合同编号:{0},生成二维码错误。",contractNo);
            logger.error(msg, e);
            throw new OwnerException(msg);
        }
    }

}
