package com.safak.filemanagement.Entity;

import com.safak.filemanagement.BaseClasses.AbstarctEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "users")
public class UserEntity extends AbstarctEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "username")
    private String username;
    @Column(name="emailadress")
    private String email;
    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "userId",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roleId",referencedColumnName = "id")
    )
    private Set<RoleEntity> roles;

    public UserEntity() {
    }

    public UserEntity(String name, String username, String email, String password, Set<RoleEntity> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
