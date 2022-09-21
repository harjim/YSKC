package com.yskc.ms.models;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models
 * @Author: zhangdingfu
 * @CreateTime: 2021-09-10 10:01
 * @Description: 批量压缩model
 */
public class BatchZipModel {


    /**
     * 压缩文件的名称
     */
    private String fileName;

    /**
     * 压缩后的文件路径，文件名
     */
    private List<String> zipFiles;

    /**
     * 要压缩的流
     */
    private List<InputStream> inputStreams;

    public BatchZipModel(String fileName) {
        this.fileName = fileName+".zip";
        zipFiles = new ArrayList<>();
        inputStreams = new ArrayList<>();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getZipFiles() {
        return zipFiles;
    }

    private void setZipFiles(List<String> zipFiles) {
        this.zipFiles = zipFiles;
    }

    public List<InputStream> getInputStreams() {
        return inputStreams;
    }

    private void setInputStreams(List<InputStream> inputStreams) {
        this.inputStreams = inputStreams;
    }

    public void add(String path, InputStream in) {
        this.zipFiles.add(path);
        this.inputStreams.add(in);
    }

}
