package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.BaseClass.FriendLyException;
import com.blog.yongyu.demo.Entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {

    Menu findById(Long id);

    List<Menu> findAll();

    Integer add(Menu menu);

    Integer remove(Long id);

    Integer modify(Menu menu);

    List<Map<String,String>> findAllRootMenu();

    List<Menu> getSidebar() throws FriendLyException;
}