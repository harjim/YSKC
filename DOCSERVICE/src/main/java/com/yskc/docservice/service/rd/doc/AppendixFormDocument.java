package com.yskc.docservice.service.rd.doc;

import com.aspose.pdf.devices.JpegDevice;
import com.aspose.pdf.devices.Resolution;
import com.yskc.docservice.dao.rs.project.DocFileAttachmentDao;
import com.yskc.docservice.entity.rs.project.ProjectEntity;
import com.yskc.docservice.models.rs.docfile.AttachmentModel;
import com.yskc.docservice.service.rd.RDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2021/2/18 15:20
 * description:记录及附件
 */
@Component("AppendixForm_Doc")
@Scope("prototype")
public class AppendixFormDocument extends RDDocument {
    @Autowired
    private DocFileAttachmentDao docFileAttachmentDao;

    @Override
    protected Map getDocMap() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("imgUrls", this.getAttachments());
        return map;
    }

    @Override
    public List<String> getAttachments() throws IOException {
        ProjectEntity projectInfo = this.dataFactory.getProjectInfo();
        List<AttachmentModel> models = docFileAttachmentDao.getByDocFile(projectInfo.getId(), this.docFile.getId());
        if (!CollectionUtils.isEmpty(models)) {
            List<String> imgUrls = new ArrayList<>();
            for (AttachmentModel model : models) {
                String fPath = model.getFilePath();
                String realPath = fPath.replace("/static/", ftlPath);
                if (fPath.endsWith(".pdf")) {
                    File pdfFile = new File(realPath);
                    if (pdfFile.exists()) {
                        File file = new File(pdfFile.getAbsolutePath().replace(".pdf", ""), "1.jpg");
                        if (!file.exists()) {
                            file.getParentFile().mkdir();
                            com.aspose.pdf.Document doc1 = new com.aspose.pdf.Document(realPath);
                            Resolution resolution = new Resolution(96);
                            JpegDevice jpegDevice = new JpegDevice(resolution, 100);
                            for (int index = 1; index <= doc1.getPages().size(); index++) {
                                // 输出路径
                                file = new File(file.getParentFile().getAbsolutePath(), index + ".jpg");
                                if (!file.getParentFile().exists()) {
                                    file.getParentFile().mkdir();
                                }
                                FileOutputStream fileOs = new FileOutputStream(file);
                                jpegDevice.process(doc1.getPages().get_Item(index), fileOs);
                                fileOs.close();
                                imgUrls.add(file.getAbsolutePath());
                            }
                        } else {
                            File[] files = file.getParentFile().listFiles();
                            for (int i = 1; i <= files.length; i++) {
                                imgUrls.add(file.getParentFile().getAbsolutePath() + "/" + i + ".jpg");
                            }
                        }

                    }
                } else {
                    imgUrls.add(realPath);
                }
            }
            return imgUrls;
        }
        return null;
    }
}
