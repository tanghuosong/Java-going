package com.rbac.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * \* User: ths
 * \* Date: 2017/10/19
 * \* Time: 16:43
 * \* Description:
 * \
 */
@Entity
@Table(name = "Role")
public class Role implements Serializable{

    private Long id;
    private String roleName;
    private Integer code;
    private String description;
    private List<User> users;
    private List<Permission> permissions;
    private List<String> permissionStr;

    public Role() {
    }

    public Role(Long id, String roleName, Integer code, String description) {
        this.id = id;
        this.roleName = roleName;
        this.code = code;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "UserToRole",joinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "userId", referencedColumnName ="id")})
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "RoleToPermission",joinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permissionId", referencedColumnName ="id")})
    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Transient
    public List<String> getPermissionStr() {
        return permissionStr;
    }

    public void setPermissionStr(List<String> permissionStr) {
        this.permissionStr = permissionStr;
    }

}
