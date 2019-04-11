/**
 * created by
 * Date:2019/4/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.Dictionary;
import com.blog.yongyu.demo.Entity.DictionaryContent;
import com.blog.yongyu.demo.Service.DictionaryContentService;
import com.blog.yongyu.demo.Service.DictionaryService;
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


    @RequestMapping(value = "/addData")
    public DataResult addData(Dictionary dictionary){
        Integer res = dictionaryService.insert(dictionary);
        if (res == 1) {
            return ResultUtils.error(1, "不能添加空对象");
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/selectData")
    public DataResult selectData(){
        List<Dictionary> all = dictionaryService.findAll();
        return ResultUtils.success(all);
    }

    /**
     * 通过字典id获取内容
     * @param dicId
     * @return
     */
    @RequestMapping(value = "/selectDataContent")
    public DataResult selecDataContent(@RequestParam("dicId")Long dicId){
        List<DictionaryContent> dicContentByDicId = dicContentService.findDicContentByDicId(dicId);
        return ResultUtils.success(dicContentByDicId);
    }

    @RequestMapping(value = "/addDataContent")
    public DataResult addDataContent(DictionaryContent dicontent,
                                     @RequestParam("dicId")Long dicId){
        Dictionary byId = dictionaryService.findById(dicId);
        if (byId == null) {
            return ResultUtils.error(2,"字典不存在");
        }
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
    @RequestMapping(value = "/delDataContent")
    public DataResult delDataContent(@RequestParam("id")Long id){
        Integer res = dicContentService.Delete(id);
        if (res == 1) {
            return ResultUtils.error(1, "不能删除空对象");
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/delData")
    public DataResult delData(@RequestParam("id") Long id) {
        Integer res = dictionaryService.Delete(id);
        if (res == 1) {
            return ResultUtils.error(1, "不能删除空对象");
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/modifyData")
    public DataResult modifyData(Dictionary dictionary){
        Integer res = dictionaryService.modify(dictionary);
        if (res == 1) {
            return ResultUtils.error(1, "修改对象为空");
        }
        return ResultUtils.success();
    }

    @RequestMapping(value = "/modifyDataContent")
    public DataResult modifyDataContent(DictionaryContent dicContent) {
        Integer res = dicContentService.modify(dicContent);
        if (res == 1) {
            return ResultUtils.error(1, "修改对象为空");
        }
        return ResultUtils.success();
    }
}