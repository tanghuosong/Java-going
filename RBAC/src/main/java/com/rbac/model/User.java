package com.rbac.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "User")
public class User implements Serializable{

    public User() {}

    private Long id;

    private String userName;
    private List<Role> roles;
//    private List<Permission> permissions;
    private String password;
    private String salt;

    private List<String> roleList;
    private List<String> permissionList;

    public User(Long id, String userName, List<Role> roles,  String password, String salt) {
        this.id = id;
        this.userName = userName;
        this.roles = roles;
        this.password = password;
        this.salt = salt;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "UserToRole",joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName ="id")})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        for(Role role : roles){

        }
        this.roleList = roleList;
    }

    @Transient
    public List<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<String> permissionList) {
        this.permissionList = permissionList;
    }

}
