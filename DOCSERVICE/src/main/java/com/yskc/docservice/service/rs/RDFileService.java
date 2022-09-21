package com.yskc.docservice.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.models.DocParam;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.service.rd.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.OutputStream;

public interface RDFileService {

    String preview(DocParam docParam) throws OwnerException;

    void export(DocParam docParam, OutputStream outputStream) throws Exception;
}
