package com.safak.filemanagement;

import com.safak.filemanagement.Entity.RoleEntity;
import com.safak.filemanagement.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilemanagementApplication {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(FilemanagementApplication.class, args);
	}



}
