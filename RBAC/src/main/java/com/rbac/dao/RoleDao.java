package com.rbac.dao;

import com.rbac.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* User: ths
 * \* Date: 2017/10/19
 * \* Time: 17:26
 * \* Description:
 * \
 */
@Repository
public interface RoleDao extends JpaRepository<Role,Long>{

    @Query(value = "SELECT r FROM Role r")
    List<Role> getRoles();
}
