/**
 * created by
 * Date:2019/5/11
 **/
package com.blog.yongyu.demo.Constroller;

import com.blog.yongyu.demo.Entity.BaseClass.DataResult;
import com.blog.yongyu.demo.Entity.Organization;
import com.blog.yongyu.demo.Service.OrganizationService;
import com.blog.yongyu.demo.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/organization")
@RestController
public class OrganizationControl {
    @Autowired
    OrganizationService organizationService;

    @RequestMapping(value = "/organization",method = RequestMethod.POST)
    public DataResult insert(@Validated Organization organization){
        try {
            organizationService.insert(organization);
            return ResultUtils.success();
        } catch (Exception e) {
            return ResultUtils.error(1, e.getMessage());
        }

    }
}