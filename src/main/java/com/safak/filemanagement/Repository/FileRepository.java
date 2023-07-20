package com.safak.filemanagement.Repository;

import com.safak.filemanagement.Entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity,String> {
    FileEntity findByFileName(String name);
}
