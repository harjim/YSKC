package com.yskc.ms.service.impl.es;

import com.yskc.ms.dao.es.CreatorDao;
import com.yskc.ms.models.newexpert.creator.CreatorModel;
import com.yskc.ms.service.es.CreatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2021-12-10 09:16
 **/
@Service
public class CreatorServiceImpl implements CreatorService {
    @Autowired
    private CreatorDao creatorDao;

    @Override
    public List<CreatorModel> getCreator() {
        return creatorDao.getCreator();
    }
}
