package com.safak.filemanagement.Entity;

import com.safak.filemanagement.BaseClasses.AbstarctEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "files")
public class FileEntity extends AbstarctEntity {
    private String fileName;

    private String filePath;

    private String fileExtension;

    private Long fileSize;

    public FileEntity() {
    }

    public FileEntity(String fileName, String filePath, String fileExtension, Long fileSize) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileExtension='" + fileExtension + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }
}
