/**
 * created by
 * Date:2019/4/10
 **/
package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.BaseClass.HttpContent;
import com.blog.yongyu.demo.Entity.DictionaryContent;
import com.blog.yongyu.demo.Repository.DictionaryContentRepository;
import com.blog.yongyu.demo.Service.DictionaryContentService;
import com.blog.yongyu.demo.Service.LoginInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("dictionaryContentService")
public class DictionaryContentServiceImpl implements DictionaryContentService{
    @Autowired
    DictionaryContentRepository dictionaryContentRepository;
    @Autowired
    LoginInfoService loginInfoService;

    @Override
    public DictionaryContent findById(Long id) {
        Optional<DictionaryContent> byId = dictionaryContentRepository.findById(id);
        if (!byId.isPresent()) {
            return null;
        }
        return byId.get();
    }

    @Override
    public Integer insert(DictionaryContent dictionaryContent) {
        if (dictionaryContent == null) {
            return 1; //不能为空
        }
        dictionaryContent.setCreateDate(new Date());
        dictionaryContentRepository.save(dictionaryContent);
        return 0;
    }

    @Override
    public Integer Delete(Long id) {
        DictionaryContent byId = findById(id);
        if (byId == null) {
            return 1;//对像不存在
        }
        if (HttpContent.removeIngoreSet.contains(byId.getContentKey())) {
            return 2; //不可删除
        }
        dictionaryContentRepository.delete(byId);
        return 0;
    }

    @Override
    public Integer modify(DictionaryContent dictionaryContent) {
        if (dictionaryContent == null) {
            return 1;//对象不能为空
        }
        DictionaryContent byId = findById(dictionaryContent.getId());
        dictionaryContent.setModifyBy(loginInfoService.getAccount());
        dictionaryContent.setModifyDate(new Date());
        BeanUtils.copyProperties(dictionaryContent,byId);
        return 0;
    }

    @Override
    public List<DictionaryContent> findDicContentByDicId(Long dicId) {
        List<DictionaryContent> dicContentByDicId = dictionaryContentRepository.findDicContentByDicId(dicId);
        return dicContentByDicId;
    }

    @Override
    public DictionaryContent findDicCntByDicIdCntId(Long dicId, Long cntId) {
        return dictionaryContentRepository.findDicCntByDicIdCntId(dicId, cntId);
    }

    /**
     * 通过contentkey查找dictionaryContent
     * @param dicId
     * @param key
     * @return
     */
    @Override
    public DictionaryContent findDicCntByDicIdCntKey(Long dicId, String key) {
        return dictionaryContentRepository.findDicCntByDicIdCntKey(dicId, key);
    }
}