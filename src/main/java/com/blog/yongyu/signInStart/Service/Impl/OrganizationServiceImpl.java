/**
 * created by
 * Date:2019/5/11
 **/
package com.blog.yongyu.signInStart.Service.Impl;

import com.blog.yongyu.signInStart.Entity.Organization;
import com.blog.yongyu.signInStart.Repository.OrganizationRepository;
import com.blog.yongyu.signInStart.Service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("OriganizationService")
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    OrganizationRepository repository;
    @Override
    public Integer insert(Organization organization) throws Exception {
        if (organization.getName() == null) {
            throw new Exception("name为空");
        }
        repository.save(organization);
        return 0;
    }
}