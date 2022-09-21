package com.yskc.rs.models.tech;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by hck
 * on 2020/8/14 17:51
 * description:
 */
public class TechUploadFileModel implements Serializable {

    private Integer id;

    private MultipartFile[] multipartFile;

    private String dir;

    private TechAttachmentModel techAttachmentModel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MultipartFile[] getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile[] multipartFile) {
        this.multipartFile = multipartFile;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public TechAttachmentModel getTechAttachmentModel() {
        return techAttachmentModel;
    }

    public void setTechAttachmentModel(TechAttachmentModel techAttachmentModel) {
        this.techAttachmentModel = techAttachmentModel;
    }
}
