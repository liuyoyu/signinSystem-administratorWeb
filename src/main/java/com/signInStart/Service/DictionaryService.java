package com.signInStart.Service;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.Dictionary;

import java.util.List;
import java.util.Map;

public interface DictionaryService {

    Dictionary findById(Long id)throws FriendlyException;

    List<Dictionary> findAll()throws FriendlyException ;

    Integer insert(Dictionary dictionary)throws FriendlyException ;

    Integer Delete(Long id)throws FriendlyException ;

    Integer modify(Dictionary dictionary)throws FriendlyException ;

    List<Map<String,Object>> getDicIdValue()throws FriendlyException ;

    List<Map<String,String>> getDicCntKeyValueByDicKey(String dicKey)throws FriendlyException ;
}