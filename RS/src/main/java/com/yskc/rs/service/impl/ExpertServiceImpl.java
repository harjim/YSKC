package com.yskc.rs.service.impl;

import com.yskc.rs.dao.ExpertDao;
import com.yskc.rs.entity.Expert;
import com.yskc.rs.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * 菜单服务
 *
 * @author huronghua
 */
@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    private ExpertDao expertDao;

    @Override
    public Expert getExpertByUuid(String expertUuid) {
        Expert expert = expertDao.getExpertByUuid(expertUuid);
        String idNumber = expert.getIdNumber();
        if (expert != null && !StringUtils.isEmpty(idNumber) && idNumber.length() == 18) {
            expert.setIdNumber(idNumber.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*"));
        }
        return expert;
    }


}
