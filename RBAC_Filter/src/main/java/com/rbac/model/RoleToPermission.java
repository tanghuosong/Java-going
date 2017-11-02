package com.rbac.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * \* User: ths
 * \* Date: 2017/10/19
 * \* Time: 16:43
 * \* Description:
 * \
 */
@Entity
@Table(name = "role_permission")
public class RoleToPermission implements Serializable{

    private Long id;
    private Long roleId;
    private Role role;
    private Long permissionId;
    private Permission permission;

    public RoleToPermission() {
    }

    public RoleToPermission(Long id, Long roleId, Long permissionId) {
        this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
