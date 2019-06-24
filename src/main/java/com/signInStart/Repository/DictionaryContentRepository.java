package com.signInStart.Repository;

import com.signInStart.Entity.DictionaryContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DictionaryContentRepository extends JpaRepository<DictionaryContent,Long>{

    @Query("select dc from DictionaryContent dc where dc.dictionary.id = ?1")
    List<DictionaryContent> findDicContentByDicId(Long dicId);

    @Query("select dc from DictionaryContent dc where dc.dictionary.id = ?1 and dc.id = ?2")
    DictionaryContent findDicCntByDicIdCntId(Long dicId, Long cntId);

    @Query("select dc from DictionaryContent dc where dc.dictionary.id = ?1 and dc.contentKey = ?2")
    DictionaryContent findDicCntByDicIdCntKey(Long dicId, String key);

    @Query(value = "select dc.contentKey as value, dc.contentValue as text from DictionaryContent dc where dc.dictionary.DataKey = ?1")
    List<Map<String,String>> findDicCntKeyValueByDicKey(String dicKey);

    @Query(value = "from DictionaryContent where contentKey = ?1 and id != ?2")
    List<DictionaryContent> findByContentKey(String contentKey, Long id);
}