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
@Table(name = "Permission")
public class Permission implements Serializable{
    private Long id;
    private String name;
    private Integer code;
    private String urlStr;
    private List<Role> roles;

    public Permission() {
    }

    public Permission(Long id, String name, Integer code, String urlStr) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.urlStr = urlStr;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public void setUrlStr(String urlStr) {
        this.urlStr = urlStr;
    }

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "role_permission",joinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permissionId", referencedColumnName ="id")})
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
