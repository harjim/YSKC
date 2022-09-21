package com.yskc.rs.service.impl;

import com.yskc.rs.dao.DocFileTemplateDao;
import com.yskc.rs.entity.DocFileTemplateEntity;
import com.yskc.rs.service.DocFileTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by hck
 * on 2020/4/21 10:53
 */
@Service
public class DocFileTemplateServiceImpl implements DocFileTemplateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocFileTemplateServiceImpl.class);
    @Autowired
    private DocFileTemplateDao docFileTemplateDao;

    @Override
    public DocFileTemplateEntity queryDocFileTemplate(Integer id) {
        return docFileTemplateDao.selectById(id);
    }
}
