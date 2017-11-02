package com.rbac.dao;

import com.rbac.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT u From User u WHERE u.userName = :userName")
    User findByUserName(@Param("userName") String userName);

}
