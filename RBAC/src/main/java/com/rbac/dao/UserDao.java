package com.rbac.dao;

import com.rbac.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* User: ths
 * \* Date: 2017/10/19
 * \* Time: 17:29
 * \* Description:
 * \
 */
@Repository
public interface UserDao extends JpaRepository<User,Long>{


}
