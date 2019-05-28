package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.DictionaryContent;

import java.util.List;

public interface DictionaryContentService {
    DictionaryContent findById(Long id)throws FriendlyException;

    Integer insert(DictionaryContent dictionaryContent)throws FriendlyException ;

    Integer Delete(Long id)throws FriendlyException ;

    Integer modify(DictionaryContent dictionaryContent)throws FriendlyException ;

    DictionaryContent findDicCntByDicIdCntId(Long dicId, Long cntId)throws FriendlyException ;

    DictionaryContent findDicCntByDicIdCntKey(Long dicId, String key)throws FriendlyException ;

    List<DictionaryContent>  findDicContentByDicId(Long dicId)throws FriendlyException ;
}