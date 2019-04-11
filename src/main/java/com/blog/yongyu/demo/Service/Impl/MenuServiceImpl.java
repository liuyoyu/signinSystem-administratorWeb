/**
 * created by liuyoyu
 * Date:2019/4/7
 **/
package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.Menu;
import com.blog.yongyu.demo.Repository.MenuRepository;
import com.blog.yongyu.demo.Service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuRepository menuRepository;

    @Override
    public Menu findById(Long id) {
        Optional<Menu> byId = menuRepository.findById(id);
        if (!byId.isPresent()) {
            return null;
        }
        return byId.get();
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> all = menuRepository.findAll();
        if (all == null || all.size() < 1) {
            return null;
        }
        return all;
    }

    @Override
    public Integer add(Menu menu) {
        if (menu == null) {
            return 1;//不能添加空对象
        }
        menuRepository.save(menu);
        return 0;
    }

    @Override
    public Integer remove(Long id) {
        Menu menu = findById(id);
        if (menu == null) {
            return 1;//不能删除空对象
        }
        menuRepository.delete(menu);
        return 0;
    }

    @Override
    public Integer modify(Menu menu) {
        if (menu == null) {
            return 1;//不能修改空对象
        }
        menu.setModifyDate(new Date());
        menuRepository.save(menu);
        return 0;
    }
}