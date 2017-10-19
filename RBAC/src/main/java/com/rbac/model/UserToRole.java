package com.rbac.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * \* User: ths
 * \* Date: 2017/10/19
 * \* Time: 16:44
 * \* Description:
 * \
 */

@Entity
@Table(name = "user_role")
public class UserToRole implements Serializable{
    private Long id;
    private Long userId;
    private Long roleId;

    public UserToRole() {
    }

    public UserToRole(Long id, Long userId, Long roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
