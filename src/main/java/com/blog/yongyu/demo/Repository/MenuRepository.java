package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.Menu;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long>{
    @Query(value = "select m from Menu m order by m.createDate desc ")
    List<Menu> findAll();
}