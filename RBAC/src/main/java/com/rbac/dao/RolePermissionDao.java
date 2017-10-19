package com.rbac.dao;

import com.rbac.model.RoleToPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* User: ths
 * \* Date: 2017/10/19
 * \* Time: 17:31
 * \* Description:
 * \
 */
@Repository
public interface RolePermissionDao extends JpaRepository<RoleToPermission,Long>{

}
