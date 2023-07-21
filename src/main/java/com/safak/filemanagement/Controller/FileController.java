package com.safak.filemanagement.Controller;

import com.safak.filemanagement.Exception.CustomExceptions.FileExtensionException;
import com.safak.filemanagement.Payload.FileDto;
import com.safak.filemanagement.Security.JwtTokenProvider;
import com.safak.filemanagement.Service.Impl.FileServiceImpl;
import com.safak.filemanagement.Utils.FileExtentions;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private final FileServiceImpl fileServiceImpl;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public FileController(FileServiceImpl fileServiceImpl, JwtTokenProvider jwtTokenProvider) {
        this.fileServiceImpl = fileServiceImpl;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Operation(summary = "Save new file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File Recieved Succesfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/uploadFile")
    public ResponseEntity<FileDto> hanleFileUpload(@RequestParam("file") MultipartFile file){
        FileDto newFileDto = fileServiceImpl.saveNewFile(file);;

        return new ResponseEntity<>(newFileDto, HttpStatus.OK);
    }

    @Operation(summary = "Get all file info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All File Info Recieved Succesfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })

    @GetMapping("/getAllFileInfo")
    public ResponseEntity<List<FileDto>> getAllFileInfo(){

        List<FileDto> allFileInfo = fileServiceImpl.getAllFiles();
        return allFileInfo.size() > 0 ? new ResponseEntity<>(allFileInfo,HttpStatus.OK) : new ResponseEntity<>(allFileInfo,HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get file Info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File Info Recieved Succesfully"),
            @ApiResponse(responseCode = "204", description = "File Content Not Exist"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })

    @GetMapping("/getFileInfo/{id}")
    public ResponseEntity<FileDto> getFileInfo(@PathVariable String id){

        FileDto fileInfo = fileServiceImpl.getSingleFile(id);
        return new ResponseEntity<>(fileInfo,HttpStatus.OK);
    }

    @Operation(summary = "Delete file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File Deleted"),
            @ApiResponse(responseCode = "204", description = "File Content Not Found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> delete(@PathVariable String filename) {

        try {
            boolean existed = fileServiceImpl.deleteFile(filename);

            if (existed)
                return ResponseEntity.status(HttpStatus.OK).body("Delete the file successfully: " + filename);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The file does not exist!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete the file: " + filename + ". Error: " + e.getMessage());
        }
    }


    @GetMapping(value = "/getfile/{filename}")
    public @ResponseBody ResponseEntity<byte[]> getImageWithMediaType(@PathVariable String filename) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = FileExtentions.getContentType(filename);
        headers.setContentType(mediaType);

        byte[] fileContent = fileServiceImpl.getFile(filename);

        return fileContent == null ?  new ResponseEntity<byte[]>(null, headers, HttpStatus.BAD_REQUEST): new ResponseEntity<byte[]>(fileContent, headers, HttpStatus.OK);
    }


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> maxUploadSizeExceeded(MaxUploadSizeExceededException e) {
        return ResponseEntity.badRequest().body("File Too Large");
    }

    @ExceptionHandler(FileExtensionException.class)
    public ResponseEntity<String> fileExtensionException(FileExtensionException e) {
        return ResponseEntity.badRequest().body("File extension is not correct");
    }
}
