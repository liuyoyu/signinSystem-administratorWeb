/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.BaseSetting;
import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.BaseClass.LoginInfor;
import com.blog.yongyu.demo.Entity.Dictionary;
import com.blog.yongyu.demo.Entity.DictionaryContent;
import com.blog.yongyu.demo.Service.DictionaryContentService;
import com.blog.yongyu.demo.Service.DictionaryService;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    Boolean checkAuth() {
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
    public DataResult addData(Dictionary dictionary) {
        if (!checkAuth()) {
            return ResultUtils.error(3, "没有权限");
        }
        if (dictionary.getDataTypeKey() == null || dictionary.getDataKey() == null || dictionary.getDataValue() == null) {
            return ResultUtils.error(4, "必填项不能为空");
        }
        dictionary.setCreateBy(loginInfoService.getAccount());
        Integer res = dictionaryService.insert(dictionary);
        if (res != 0) {
            String[] msg = {"成功", "不能添加空对象", "字典键已存在，不能重复添加"};
            return ResultUtils.error(res, msg[res]);
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public DataResult selectData() {
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
    public DataResult selecDataContent(@RequestParam("dicId") Long dicId) {
        List<DictionaryContent> dicContentByDicId = dicContentService.findDicContentByDicId(dicId);
        return ResultUtils.success(dicContentByDicId, dicContentByDicId.size());
    }

    @RequestMapping(value = "/dataContent", method = RequestMethod.POST)
    public DataResult addDataContent(DictionaryContent dicontent,
                                     @RequestParam("dicId") Long dicId) {
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
    public DataResult delDataContent(@RequestParam("id") Long id) {
        Integer res = dicContentService.Delete(id);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"成功", "不能删除空对象", "该数据不能删除"};
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping(value = "/data", method = RequestMethod.DELETE)
    public DataResult delData(@RequestParam("id") Long id) {
        Integer res = dictionaryService.Delete(id);
        if (res != 0) {
            String[] msg = {"成功", "不能删除空对象", "系统参数不能删除"};
            return ResultUtils.error(res, msg[res]);
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public DataResult modifyData(Dictionary dictionary) {
        Integer res = dictionaryService.modify(dictionary);
        if (res == 1) {
            return ResultUtils.error(1, "修改对象为空");
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/dataContent", method = RequestMethod.PUT)
    public DataResult modifyDataContent(DictionaryContent dicContent,
                                        @RequestParam("dicId") Long dicId) {
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
    public DataResult findByDicId(@RequestParam("dicID")Long dicID){
        Dictionary byId = dictionaryService.findById(dicID);
        if (byId == null) {
            return ResultUtils.error(1, "字典不存在");
        }
        return ResultUtils.success(byId);
    }

    @RequestMapping(value = "/dicIdValue",method = RequestMethod.GET)
    public DataResult getDicIdValue(){
        List<Map<String, Object>> value = dictionaryService.getDicIdValue();
        if (value == null) {
            return ResultUtils.error(1, "没有数据");
        }
        return ResultUtils.success(value);
    }

    @RequestMapping(value = "/dicCnt/dicCntId",method = RequestMethod.GET)
    public DataResult getDicCntById(@RequestParam("dicCntID") Long dicCntID) {
        DictionaryContent byId = dicContentService.findById(dicCntID);
        if (byId == null) {
            return ResultUtils.error(1, "数据不存在");
        }
        return ResultUtils.success(byId);
    }
    @RequestMapping(value = "/dicCnt/dicKey",method = RequestMethod.GET)
    public DataResult getDicCntBydicKey(@RequestParam("dicKey")String dicKey){
        List<Map<String, String>> dicCntKeyValueByDicKey = dictionaryService.getDicCntKeyValueByDicKey(dicKey);
        return ResultUtils.success(dicCntKeyValueByDicKey,dicCntKeyValueByDicKey.size());
    }

}