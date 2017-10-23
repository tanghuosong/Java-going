package com.rbac.dao;

import com.rbac.model.WebMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: ths
 * @date: 2017/10/23
 * @time: 11:47
 * @description:
 */

@Repository
public interface WebMenuDao extends JpaRepository<WebMenu,Integer>{
}
