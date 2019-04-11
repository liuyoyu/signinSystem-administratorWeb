package com.blog.yongyu.demo.Service;

import com.blog.yongyu.demo.Entity.DictionaryContent;

import java.util.List;

public interface DictionaryContentService {
    DictionaryContent findById(Long id);

    Integer insert(DictionaryContent dictionaryContent);

    Integer Delete(Long id);

    Integer modify(DictionaryContent dictionaryContent);

    List<DictionaryContent>  findDicContentByDicId(Long dicId);
}