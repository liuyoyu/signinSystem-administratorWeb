package com.signInStart.Repository;

import com.signInStart.Entity.Dictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {

    @Query("select d from Dictionary d order by d.createDate desc ")
    List<Dictionary> findAll();
    @Query("select d from Dictionary d where d.DataKey = ?1")
    Dictionary findByDataKey(String dataKey);

    @Query(value = "select d.id, d.data_value from Dictionary d",nativeQuery = true)
    List<Map<String,Object>> getDicIdValue();

    @Query("select d from Dictionary d where d.DataKey = ?1 and d.id <> ?2")
    Dictionary findMultiDataKey(String dataKey, Long id);
}