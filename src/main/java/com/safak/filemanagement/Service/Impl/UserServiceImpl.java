package com.safak.filemanagement.Service.Impl;

import com.safak.filemanagement.Entity.RoleEntity;
import com.safak.filemanagement.Entity.UserEntity;
import com.safak.filemanagement.Payload.RegisterDto;
import com.safak.filemanagement.Repository.RoleRepository;
import com.safak.filemanagement.Repository.UserRepository;
import com.safak.filemanagement.Service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public String createUser(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            System.out.println("Username is already taken or exist");
            return "Username is already taken or exist";
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            System.out.println("Email is already taken or exist");
            return "Email is already taken or exist";
        }

        UserEntity user=new UserEntity();
        user.setName(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());

        Set<RoleEntity> roles=new HashSet<>();

        RoleEntity userRole=roleRepository.findByRolename("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        return "User Registered Successfully";
    }

    @Override
    public RegisterDto updateUser(String userId, RegisterDto registerDto) {
        UserEntity userEntity=userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("There is no user with proven user id"));
        userEntity.setName(registerDto.getUsername());
        userEntity.setUsername(registerDto.getUsername());
        userEntity.setPassword(registerDto.getPassword());
        userEntity.setEmail(registerDto.getEmail());

        UserEntity updatedUser=userRepository.save(userEntity);
        return mapToDTO(updatedUser);
    }

    @Override
    public RegisterDto updateUserRole(String userId, long userRoleNo) {
        UserEntity user=userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("There is no user with proven user id"));
        Set<RoleEntity> roleEntitySet=new HashSet<>();
        RoleEntity userRole= new RoleEntity();
        if(userRoleNo==1)
        {
            userRole=roleRepository.findByRolename("ROLE_ADMIN").get();
        }
        else if(userRoleNo==2){
            userRole=roleRepository.findByRolename("ROLE_USER").get();
        }
        roleEntitySet.add(userRole);
        user.setRoles(roleEntitySet);
        UserEntity updatedRoleEntitiy=userRepository.save(user);
        return mapToDTO(updatedRoleEntitiy);
    }

    @Override
    public RegisterDto findUserById(String userId) {
        UserEntity userEntity=userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("There is no user with proven user id"));
        return mapToDTO(userEntity);
    }

    @Override
    public List<RegisterDto> getAllUsers() {
        List<UserEntity> userEntities=new ArrayList<>();
        userEntities=userRepository.findAll();
        List<RegisterDto> userDtoList=new ArrayList<>();
        userDtoList=userEntities.stream().map(userEntity -> mapToDTO(userEntity))
                .collect(Collectors.toList());
        return userDtoList;
    }

    @Override
    public String deleteUser(String userId) {
        UserEntity userEntity=userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("There is no user with proven user id"));
        if(userEntity.isActive()==true){
            userEntity.setActive(false);
            userRepository.save(userEntity);
            return "Successfully deleted";
        }else {
            return "User is already in passive statu";
        }
    }

    @Override
    public RegisterDto findByUsername(String username) {
        UserEntity user=userRepository.findByUsername(username)
                .orElseThrow(()->new EntityNotFoundException("There is no user with proven username"));
        RegisterDto userDto=mapToDTO(user);

        return userDto;
    }

    private RegisterDto mapToDTO(UserEntity userEntity){
        RegisterDto registerDto=modelMapper.map(userEntity,RegisterDto.class);
        return registerDto;
    }

    private UserEntity dtoToEntity(RegisterDto registerDto){
        UserEntity userEntity=modelMapper.map(registerDto,UserEntity.class);
        return userEntity;
    }
}
