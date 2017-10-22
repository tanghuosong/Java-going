package com.rbac.dao;

import com.rbac.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * \* User: ths
 * \* Date: 2017/10/19
 * \* Time: 17:29
 * \* Description:
 * \
 */
@Repository
public interface PermissionDao extends JpaRepository<Permission,Long>{

    /**
     * 根据角色id 获取相对应的权限
     * @param roleId 角色id
     * @return List<Permission>
     */
    @Query(value = "SELECT * from Permission p left join role_permission rp on rp.permissionId = p.id WHERE rp.roleId= :roleId",nativeQuery = true)
    List<Permission> getPermissionsByRoleId(@Param("roleId")Long roleId);
}
