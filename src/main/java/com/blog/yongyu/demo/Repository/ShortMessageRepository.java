package com.blog.yongyu.demo.Repository;

import com.blog.yongyu.demo.Entity.ShortMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShortMessageRepository extends JpaRepository<ShortMessage, Long> {
    @Query("select s from ShortMessage s where s.account = ?1 and s.email = ?2 order by s.sendTime desc")
    ShortMessage findShortMessagesByAccountEmail(String account, String email);
}