package com.safak.filemanagement.Service.Impl;

import com.safak.filemanagement.Controller.FileController;
import com.safak.filemanagement.Entity.FileEntity;
import com.safak.filemanagement.Exception.CustomExceptions.FileExtensionException;
import com.safak.filemanagement.Exception.CustomExceptions.FileLocationException;
import com.safak.filemanagement.Exception.CustomExceptions.NotFoundException;
import com.safak.filemanagement.Payload.FileDto;
import com.safak.filemanagement.Repository.FileRepository;
import com.safak.filemanagement.Service.FileService;
import com.safak.filemanagement.Utils.FileExtentions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author safakkizilirmak
 * @version 1.0
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private  FileRepository fileRepository;
    @Autowired
    private  ModelMapper modelMapper;

    private  Path fileLocationPath;

    /**
     *
     * @param filePath for file path
     * @param fileRepository database operation
     * @param modelMapper dto-model and model-dto transformation
     */

    public FileServiceImpl(FileRepository fileRepository, ModelMapper modelMapper,@Value("${file.path}") String filePath) {
        this.fileRepository = fileRepository;
        this.modelMapper = modelMapper;
        this.fileLocationPath = Paths.get(filePath).toAbsolutePath().normalize();;

        try {
            Files.createDirectories(this.fileLocationPath);
        } catch (Exception ex) {
            throw new FileLocationException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * @param file create new file contents
     * @return FileDto
     */
    @Override
    public FileDto saveNewFile(MultipartFile file) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String ext = FilenameUtils.getExtension(fileName);

        if(!FileExtentions.isExtension(ext))
            throw new FileExtensionException("File extension is not correct ");

        Path targetLocation = this.fileLocationPath.resolve(fileName);
        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            FileEntity newFile = new FileEntity(fileName, this.fileLocationPath.toString(),ext,file.getSize());
            return this.modelMapper.map(fileRepository.save(newFile), FileDto.class);

        } catch (Exception e) {
            throw new FileLocationException("Could not store fileName : " + fileName);
        }
    }

    /**
     * @return all file contents
     */
    @Override
    public List<FileDto> getAllFiles() {

        List<FileEntity> files = fileRepository.findAll();
        List<FileDto> dtos = files
                .stream()
                .map(file -> this.modelMapper.map(file, FileDto.class))
                .collect(Collectors.toList());
        return dtos;
    }

    /**
     * @param Id file id
     * @return one file
     */
    @Override
    public FileDto getSingleFile(String Id) {
        FileEntity file = fileRepository.findById(Id).orElseThrow(() -> new NotFoundException("File not exist"));
        return this.modelMapper.map(file, FileDto.class);
    }


    /**
     * @param name deleted filename
     * @return if delete true or false
     */
    @Override
    public Boolean deleteFile(String name) {

        try {
            FileEntity deletedFile = fileRepository.findByFileName(name);
            deletedFile.setActive(false);
            fileRepository.save(deletedFile);
            Path file = fileLocationPath.resolve(name);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    /**
     * @param name filename
     * @return filename path
     */
    @Override
    public byte[] getFile(String name) {

        try {
            String path = fileLocationPath.resolve(name).toString();
            java.io.File file = new java.io.File(path);
            byte[] bytes = FileUtils.readFileToByteArray(file);
            return bytes;
        } catch (IOException e) {
            LOGGER.error("File Not Found => " + name);
            return null;
        }
    }
}
