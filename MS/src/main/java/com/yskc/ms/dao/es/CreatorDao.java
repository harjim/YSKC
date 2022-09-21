package com.yskc.ms.dao.es;

import com.yskc.ms.models.newexpert.creator.CreatorModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreatorDao {
    List<CreatorModel> getCreator();
}
