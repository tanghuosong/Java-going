package com.rbac.service;

import com.rbac.model.WebMenu;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: ths
 * @date: 2017/10/23
 * @time: 12:04
 * @description:
 */
@Service
public interface MenuService {

    List<WebMenu> getAll();

}
