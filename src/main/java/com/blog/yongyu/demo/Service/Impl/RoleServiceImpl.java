package com.blog.yongyu.demo.Service.Impl;

import com.blog.yongyu.demo.Entity.BaseClass.BaseSetting;
import com.blog.yongyu.demo.Entity.BaseClass.FriendLyException;
import com.blog.yongyu.demo.Entity.Role;
import com.blog.yongyu.demo.Repository.RoleRepository;
import com.blog.yongyu.demo.Service.LoginInfoService;
import com.blog.yongyu.demo.Service.RoleService;
import com.blog.yongyu.demo.Utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    LoginInfoService loginInfoService;

    @Override
    public Role findRoleById(Long id) {
        Optional<Role> role = roleRepository.findById(id);
        if (role.isPresent()) {
            return role.get();
        }
        return null;
    }

    /**
     * 创建新的角色，默认角色类型为用户
     *
     * @param role
     * @return
     */
    @Override
    public Integer Insert(Role role) throws FriendLyException {
        if (role == null) {
            throw new FriendLyException("角色不能为空", 1);
        }
        if (DataUtils.isEmptyString(role.getRoleName())) {
            throw new FriendLyException("角色名称不能为空",1);
        }
        if (roleRepository.findByRoleName(role.getRoleName()) != null) {
            throw new FriendLyException("角色名称重复", 2);
        }
        loginInfoService.checkSupperAdimn();
        if (DataUtils.isEmptyString(role.getUserType())) { //设置默认角色类型
            role.setUserType(BaseSetting.ROLE.User_SYS.toString());
        } else if (!loginInfoService.checkSupperAdimn() && !role.getUserType().equals(BaseSetting.ROLE.User_SYS.toString())) {
            throw new FriendLyException("没有权限,请联系超级管理员", 2);
        }
        role.setCreateDate(new Date());
        role.setCreateBy(loginInfoService.getAccount());
        roleRepository.save(role);
        return 0;
    }

    @Override
    public Integer Delete(Long roleId) throws FriendLyException {
        Role role = findRoleById(roleId);
        if (role == null) {
            throw new FriendLyException("删除对象不存在", 1);
        }
        if (loginInfoService.checkUser() || !BaseSetting.ROLE.User_SYS.toString().equals(role.getUserType()) && !loginInfoService.checkSupperAdimn()) {
            throw new FriendLyException("没有权限", 1);
        }
        if (BaseSetting.ROLE.SupperAdmin_SYS.toString().equals(role.getRoleName())) {
            throw new FriendLyException("基本角色不能删除", 2);
        }
        roleRepository.delete(role);
        return 0;
    }

    @Override
    public Integer modify(Role role) throws FriendLyException{
        if (role == null) {
            throw new FriendLyException("传入参数不能为空", 1);
        }
        if (loginInfoService.checkUser() || !BaseSetting.ROLE.User_SYS.toString().equals(role.getUserType()) && !loginInfoService.checkSupperAdimn()) {
            throw new FriendLyException("没有权限", 1);
        }
        Role oldRole = findRoleById(role.getRoleId());
        if (oldRole == null) {
            throw new FriendLyException("修改对象不存在", 2);
        }
        DataUtils.copyProperty(role, oldRole);
        oldRole.setModifyBy(loginInfoService.getAccount());
        oldRole.setModifyDate(new Date());
        roleRepository.save(oldRole);
        return 0;
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> findByRoleNames(String[] names) {
        return roleRepository.findByRoleNames(names);
    }
}