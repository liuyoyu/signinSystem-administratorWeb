package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.Menu;

import java.util.List;

public interface MenuService {

    Menu findById(Long id);

    List<Menu> findAll();

    Integer add(Menu menu);

    Integer remove(Long id);

    Integer modify(Menu menu);

}