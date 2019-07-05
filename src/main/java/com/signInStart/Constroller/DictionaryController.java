/**
 * created by
 * Date:2019/4/11
 **/
package com.signInStart.Constroller;

import com.signInStart.Entity.BaseClass.*;
import com.signInStart.Entity.Dictionary;
import com.signInStart.Entity.DictionaryContent;
import com.signInStart.Service.DictionaryContentService;
import com.signInStart.Service.DictionaryService;
import com.signInStart.Service.LoginInfoService;
import com.signInStart.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dic")
public class DictionaryController {
    @Autowired
    DictionaryService dictionaryService;
    @Autowired
    DictionaryContentService dicContentService;
    @Autowired
    LoginInfoService loginInfoService;

    Boolean checkAuth() throws FriendlyException {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();
        if (logiInfo == null) {
            return false;
        }
        if (loginInfoService.checkAdmin()) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    public DataResult addData(Dictionary dictionary) throws FriendlyException {
        if (!checkAuth()) {
            return ResultUtils.error(3, "没有权限");
        }
        if (dictionary.getDataTypeKey() == null || dictionary.getDataKey() == null || dictionary.getDataValue() == null) {
            return ResultUtils.error(4, "必填项不能为空");
        }
        dictionary.setCreateBy(loginInfoService.getAccount());
        dictionary.setCreateDate(new Date());
        Integer res = dictionaryService.insert(dictionary);
        if (res != 0) {
            String[] msg = {"成功", "不能添加空对象", "字典键已存在，不能重复添加"};
            return ResultUtils.error(res, msg[res]);
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public DataResult selectData() throws FriendlyException {
        List<Dictionary> all = dictionaryService.findAll();
        return ResultUtils.success(all, all.size());
    }

    /**
     * 通过字典id获取内容
     *
     * @param dicId
     * @return
     */
    @RequestMapping(value = "/dataContent", method = RequestMethod.GET)
    public DataResult selecDataContent(@RequestParam("dicId") Long dicId)throws FriendlyException  {
        List<DictionaryContent> dicContentByDicId = dicContentService.findDicContentByDicId(dicId);
        return ResultUtils.success(dicContentByDicId, dicContentByDicId.size());
    }

    @RequestMapping(value = "/dataContent", method = RequestMethod.POST)
    @Auth(BaseSetting.NOUSER)
    public DataResult addDataContent(DictionaryContent dicontent,
                                     @RequestParam("dicId") Long dicId)throws FriendlyException  {
        LoginInfor logiInfo = loginInfoService.getLogiInfo();

        Dictionary byId = dictionaryService.findById(dicId);
        if (byId == null) {
            return ResultUtils.error(2, "字典不存在");
        }
        if (dicontent.getContentKey() == null || dicontent.getContentValue() == null || dicontent.getStatus() == null ||
                dicId == null) {
            return ResultUtils.error(3, "必填项不能为空");
        }
        if (dicContentService.findDicCntByDicIdCntKey(dicId, dicontent.getContentKey()) != null) {
            return ResultUtils.error(3, "Key已存在");
        }
        dicontent.setCreateBy(logiInfo.getAccount());
        dicontent.setDictionary(byId);
        Integer res = dicContentService.insert(dicontent);
        if (res == 1) {
            return ResultUtils.error(1, "不能添加空对象");
        }
        return ResultUtils.success();
    }

    /**
     * 删除数据字典内容
     * @param id
     * @return
     */
    @RequestMapping(value = "/dataContent", method = RequestMethod.DELETE)
    @Auth(BaseSetting.NOUSER)
    public DataResult delDataContent(@RequestParam("id") Long id) throws FriendlyException {
        Integer res = dicContentService.Delete(id);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"成功", "不能删除空对象", "该数据不能删除"};
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping(value = "/data", method = RequestMethod.DELETE)
    @Auth(BaseSetting.NOUSER)
    public DataResult delData(@RequestParam("id") Long id) throws FriendlyException {
        Integer res = dictionaryService.Delete(id);
        if (res != 0) {
            String[] msg = {"成功", "不能删除空对象", "系统参数不能删除"};
            return ResultUtils.error(res, msg[res]);
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    @Auth(BaseSetting.NOUSER)
    public DataResult modifyData(Dictionary dictionary)throws FriendlyException  {
        Integer res = dictionaryService.modify(dictionary);
        if (res == 1) {
            return ResultUtils.error(1, "修改对象为空");
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/dataContent", method = RequestMethod.PUT)
    @Auth(BaseSetting.NOUSER)
    public DataResult modifyDataContent(DictionaryContent dicContent,
                                        @RequestParam("dicId") Long dicId)throws FriendlyException  {
        DictionaryContent dicCntByDicIdCntId = dicContentService.findDicCntByDicIdCntId(dicId, dicContent.getId());
        if (dicCntByDicIdCntId == null) {
            return ResultUtils.error(2, "修改对象不存在");
        }
        Integer res = dicContentService.modify(dicContent);
        if (res == 1) {
            return ResultUtils.error(1, "输入为空");
        }
        return ResultUtils.success();
    }

    /**
     * 通过字典ID获取字典数据
     * @param dicID
     * @return
     */
    @RequestMapping(value = "/dicId",method = RequestMethod.GET)
    public DataResult findByDicId(@RequestParam("dicID")Long dicID)throws FriendlyException {
        Dictionary byId = dictionaryService.findById(dicID);
        if (byId == null) {
            return ResultUtils.error(1, "字典不存在");
        }
        return ResultUtils.success(byId);
    }

    @RequestMapping(value = "/dicIdValue",method = RequestMethod.GET)
    public DataResult getDicIdValue()throws FriendlyException {
        List<Map<String, Object>> value = dictionaryService.getDicIdValue();
        if (value == null) {
            return ResultUtils.error(1, "没有数据");
        }
        return ResultUtils.success(value);
    }

    @RequestMapping(value = "/dicCnt/dicCntId",method = RequestMethod.GET)
    public DataResult getDicCntById(@RequestParam("dicCntID") Long dicCntID)throws FriendlyException  {
        DictionaryContent byId = dicContentService.findById(dicCntID);
        if (byId == null) {
            return ResultUtils.error(1, "数据不存在");
        }
        return ResultUtils.success(byId);
    }
    @RequestMapping(value = "/dicCnt/dicKey",method = RequestMethod.GET)
    public DataResult getDicCntBydicKey(@RequestParam("dicKey")String dicKey)throws FriendlyException {
        List<Map<String, String>> dicCntKeyValueByDicKey = dictionaryService.getDicCntKeyValueByDicKey(dicKey);
        return ResultUtils.success(dicCntKeyValueByDicKey,dicCntKeyValueByDicKey.size());
    }

}