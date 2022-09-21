package com.yskc.rs.service;

import com.yskc.rs.entity.DocFileTemplateEntity;

/**
 * Created by hck
 * on 2020/4/21 10:52
 */
public interface DocFileTemplateService {
    /**
     * 根据id查询模板版本
     * @param id
     * @return
     */
    DocFileTemplateEntity queryDocFileTemplate(Integer id);
}
