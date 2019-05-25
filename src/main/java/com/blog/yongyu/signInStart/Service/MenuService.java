package com.blog.yongyu.signInStart.Service;

import com.blog.yongyu.signInStart.Entity.BaseClass.FriendlyException;
import com.blog.yongyu.signInStart.Entity.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {

    Menu findById(Long id)throws FriendlyException ;

    List<Menu> findAll()throws FriendlyException ;

    Integer add(Menu menu)throws FriendlyException ;

    Integer remove(Long id)throws FriendlyException ;

    Integer modify(Menu menu)throws FriendlyException ;

    List<Map<String,String>> findAllRootMenu()throws FriendlyException ;

    List<Menu> getSidebar() throws FriendlyException;
}