/**
 * created by
 * Date:2019/5/11
 **/
package com.signInStart.Constroller;

import com.signInStart.Entity.BaseClass.DataResult;
import com.signInStart.Entity.Organization;
import com.signInStart.Service.OrganizationService;
import com.signInStart.Utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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