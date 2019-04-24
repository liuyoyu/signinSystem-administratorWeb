/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.BaseRole;
import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.BaseClass.LoginInfor;
import com.blog.yongyu.demo.Entity.Dictionary;
import com.blog.yongyu.demo.Entity.DictionaryContent;
import com.blog.yongyu.demo.Service.DictionaryContentService;
import com.blog.yongyu.demo.Service.DictionaryService;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        if (logiInfo.getRoleId().equals(BaseRole.AdminId)) {
            return true;
        }
        return false;
    }

    @RequestMapping(value = "/addData", method = RequestMethod.POST)
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

    @RequestMapping(value = "/selectData", method = RequestMethod.POST)
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
    @RequestMapping(value = "/selectDataContent", method = RequestMethod.POST)
    public DataResult selecDataContent(@RequestParam("dicId") Long dicId) {
        List<DictionaryContent> dicContentByDicId = dicContentService.findDicContentByDicId(dicId);
        return ResultUtils.success(dicContentByDicId, dicContentByDicId.size());
    }

    @RequestMapping(value = "/addDataContent", method = RequestMethod.POST)
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
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delDataContent", method = RequestMethod.POST)
    public DataResult delDataContent(@RequestParam("id") Long id) {
        Integer res = dicContentService.Delete(id);
        if (res == 0) {
            return ResultUtils.success();
        }
        String[] msg = {"成功", "不能删除空对象", "该数据不能删除"};
        return ResultUtils.error(res, msg[res]);
    }

    @RequestMapping(value = "/delData", method = RequestMethod.POST)
    public DataResult delData(@RequestParam("id") Long id) {
        Integer res = dictionaryService.Delete(id);
        if (res != 0) {
            String[] msg = {"成功", "不能删除空对象", "系统参数不能删除"};
            return ResultUtils.error(res, msg[res]);
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/modifyData", method = RequestMethod.POST)
    public DataResult modifyData(Dictionary dictionary) {
        Integer res = dictionaryService.modify(dictionary);
        if (res == 1) {
            return ResultUtils.error(1, "修改对象为空");
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/modifyDataContent", method = RequestMethod.POST)
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

    @RequestMapping(value = "/findByDicId",method = RequestMethod.POST)
    public DataResult findByDicId(@RequestParam("dicID")Long dicID){
        Dictionary byId = dictionaryService.findById(dicID);
        if (byId == null) {
            return ResultUtils.error(1, "字典不存在");
        }
        return ResultUtils.success(byId);
    }
}