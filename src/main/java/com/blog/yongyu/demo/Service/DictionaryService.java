package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.Dictionary;

import java.util.List;

public interface DictionaryService {

    Dictionary findById(Long id);

    List<Dictionary> findAll();

    Integer insert(Dictionary dictionary);

    Integer Delete(Long id);

    Integer modify(Dictionary dictionary);
}