package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.ShortMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortMessageRepository extends JpaRepository<ShortMessage, Long> {
    @Query("select * from ShortMessage where accout = ?1 and email = ?2 ORDER BY sendTime desc")
    List<ShortMessage> findShoerMessagesByAccountEmail(String account, String email);
}