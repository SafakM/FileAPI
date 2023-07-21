package com.safak.filemanagement;

import com.safak.filemanagement.Entity.RoleEntity;
import com.safak.filemanagement.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FilemanagementApplication {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(FilemanagementApplication.class, args);
	}


	@Bean
	CommandLineRunner initDatabase(RoleRepository roleRepository) {

		return args -> {

			RoleEntity adminRole=new RoleEntity();
			adminRole.setRolename("ROLE_ADMIN");
			roleRepository.save(adminRole);

			RoleEntity userRole= new RoleEntity();
			userRole.setRolename("ROLE_USER");
			roleRepository.save(userRole);

		};
	}

}
