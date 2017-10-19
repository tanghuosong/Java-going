package com.rbac.dao;

import com.rbac.model.UserToRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* User: ths
 * \* Date: 2017/10/19
 * \* Time: 17:30
 * \* Description:
 * \
 */

@Repository
public interface UserRoleDao extends JpaRepository<UserToRole,Long>{
}
