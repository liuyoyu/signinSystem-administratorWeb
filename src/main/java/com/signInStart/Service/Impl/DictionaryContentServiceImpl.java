/**
 * created by
 * Date:2019/4/10
 **/
package com.signInStart.Service.Impl;

import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.BaseClass.HttpContent;
import com.signInStart.Entity.DictionaryContent;
import com.signInStart.Repository.DictionaryContentRepository;
import com.signInStart.Service.DictionaryContentService;
import com.signInStart.Service.LoginInfoService;
import com.signInStart.Utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("dictionaryContentService")
public class DictionaryContentServiceImpl implements DictionaryContentService {
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
    public Integer modify(DictionaryContent dictionaryContent) throws FriendlyException {
        if (dictionaryContent == null) {
            return 1;//对象不能为空
        }
        List<DictionaryContent> byContentKey = dictionaryContentRepository.findByContentKey(dictionaryContent.getContentKey(), dictionaryContent.getId());
        if (byContentKey != null && byContentKey.size() > 0) {
            throw new FriendlyException("键值不能重复", DataUtils.CurrentMethodName());
        }
        DictionaryContent byId = findById(dictionaryContent.getId());
        byId.setModifyBy(loginInfoService.getAccount());
        byId.setModifyDate(new Date());
        if (dictionaryContent.getContentValue() != null && !"".equals(dictionaryContent.getContentValue())) {
            byId.setContentValue(dictionaryContent.getContentValue());
        }
        if (dictionaryContent.getContentKey() != null && !"".equals(dictionaryContent.getContentKey())) {
            byId.setContentKey(dictionaryContent.getContentKey());
        }
        if (dictionaryContent.getStatus() != null && !"".equals(dictionaryContent.getStatus())) {
            byId.setStatus(dictionaryContent.getStatus());
        }
        if (dictionaryContent.getDescribe() != null && !"".equals(dictionaryContent.getDescribe())) {
            byId.setDescribe(dictionaryContent.getDescribe());
        }
        if (dictionaryContent.getSequence() != null && !"".equals(dictionaryContent.getSequence())) {
            byId.setSequence(dictionaryContent.getSequence());
        }
        dictionaryContentRepository.save(byId);
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
     *
     * @param dicId
     * @param key
     * @return
     */
    @Override
    public DictionaryContent findDicCntByDicIdCntKey(Long dicId, String key) {
        return dictionaryContentRepository.findDicCntByDicIdCntKey(dicId, key);
    }
}