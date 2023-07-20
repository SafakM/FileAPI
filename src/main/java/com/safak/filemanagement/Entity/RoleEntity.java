package com.safak.filemanagement.Entity;

import com.safak.filemanagement.BaseClasses.AbstarctEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity extends AbstarctEntity {
    @Column(name = "rolename")
    private String rolename;

    public RoleEntity() {
    }

    public RoleEntity(String rolename) {
        this.rolename = rolename;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

}
