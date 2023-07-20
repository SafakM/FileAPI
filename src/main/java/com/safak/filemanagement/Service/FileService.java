package com.safak.filemanagement.Service;

import com.safak.filemanagement.Payload.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    FileDto saveNewFile(MultipartFile file);
    List<FileDto> getAllFiles();
    FileDto getSingleFile(String Id);
    Boolean deleteFile(String name);
    byte[] getFile(String name);
}
