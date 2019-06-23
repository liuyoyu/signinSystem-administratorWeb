package com.signInStart.Service.Impl;

import com.signInStart.Entity.BaseClass.BaseSetting;
import com.signInStart.Entity.BaseClass.FriendlyException;
import com.signInStart.Entity.Role;
import com.signInStart.Repository.RoleRepository;
import com.signInStart.Service.LoginInfoService;
import com.signInStart.Service.RoleService;
import com.signInStart.Utils.DataUtils;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * 根据用户类型获取角色
     * @param userType
     * @return
     */
    @Override
    public List<Role> findByUserType(String userType) {
        return roleRepository.findByUserType(userType);
    }

    /**
     * 创建新的角色，默认角色类型为用户
     *
     * @param role
     * @return
     */
    @Override
    public Integer Insert(Role role) throws FriendlyException {
        if (role == null) {
            throw new FriendlyException("角色不能为空", DataUtils.CurrentMethodName());
        }
        if (DataUtils.isEmptyString(role.getRoleName())) {
            throw new FriendlyException("角色名称不能为空", 1);
        }
        if (roleRepository.findByRoleName(role.getRoleName()) != null) {
            throw new FriendlyException("角色名称重复", DataUtils.CurrentMethodName());
        }
        loginInfoService.checkSupperAdimn();
        if (DataUtils.isEmptyString(role.getUserType())) { //设置默认角色类型
            role.setUserType(BaseSetting.ROLE.User_SYS.toString());
        } else if (!loginInfoService.checkSupperAdimn() && !role.getUserType().equals(BaseSetting.ROLE.User_SYS.toString())) {
            throw new FriendlyException("没有权限,请联系超级管理员", DataUtils.CurrentMethodName());
        }
        role.setCreateDate(new Date());
        role.setCreateBy(loginInfoService.getAccount());
        roleRepository.save(role);
        return 0;
    }

    @Override
    public Integer Delete(Long roleId) throws FriendlyException {
        Role role = findRoleById(roleId);
        if (role == null) {
            throw new FriendlyException("删除对象不存在", DataUtils.CurrentMethodName());
        }
        if (loginInfoService.checkUser() || !BaseSetting.ROLE.User_SYS.toString().equals(role.getUserType()) && !loginInfoService.checkSupperAdimn()) {
            throw new FriendlyException("没有权限", 1);
        }
        if (BaseSetting.ROLE.SupperAdmin_SYS.toString().equals(role.getRoleName())) {
            throw new FriendlyException("基本角色不能删除", DataUtils.CurrentMethodName());
        }
        roleRepository.delete(role);
        return 0;
    }

    @Override
    public Integer modify(Role role) throws FriendlyException {
        if (role == null) {
            throw new FriendlyException("传入参数不能为空", DataUtils.CurrentMethodName());
        }
        if ("".equals(role.getUserType())) {
            throw new FriendlyException("用户类型不能为空", DataUtils.CurrentMethodName());
        }
        if (loginInfoService.checkUser() || !BaseSetting.ROLE.User_SYS.toString().equals(role.getUserType()) && !loginInfoService.checkSupperAdimn()) {
            throw new FriendlyException("没有权限", DataUtils.CurrentMethodName());
        }
        Role oldRole = findRoleById(role.getRoleId());
        if (oldRole == null) {
            throw new FriendlyException("修改对象不存在", DataUtils.CurrentMethodName());
        }
        DataUtils.copyProperty(role, oldRole);
        oldRole.setModifyBy(loginInfoService.getAccount());
        oldRole.setModifyDate(new Date());
        roleRepository.save(oldRole);
        return 0;
    }

    @Override
    public List<JSONObject> findAll() throws FriendlyException {
        List<Role> all = roleRepository.findAll(new Sort(Sort.Direction.ASC, "userType"));
        if (all == null) {
            throw new FriendlyException("没有角色，请先创建", DataUtils.CurrentMethodName());
        }
        String tmp = all.get(0).getUserType();
        List<JSONObject> jsonObjects = new LinkedList<>();
        List<JSONObject> roles = new LinkedList<>();
        for (int i=0; i<all.size(); i++) {
            Role r = all.get(i);
            if (!tmp.equals(r.getUserType()) || i + 1 == all.size()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", tmp);
                jsonObject.put("label", tmp.equals(BaseSetting.ROLE.SupperAdmin_SYS.toString()) ? "超级管理员" :
                        (tmp.equals(BaseSetting.ROLE.Admin_SYS.toString()) ? "管理员" : "用户"));
                jsonObject.put("children", roles);
                jsonObjects.add(jsonObject);
                tmp = r.getUserType();
                roles = new LinkedList<>();
            }
            JSONObject js = new JSONObject();
            js.put("roleId", r.getRoleId());
            js.put("id", r.getUserType());
            js.put("label", r.getRoleName());
            js.put("status", r.getStatus());
            js.put("createDate", r.getCreateDate());
            js.put("modifyDate", r.getModifyDate());
            js.put("createBy", r.getCreateBy());
            js.put("modifyBy", r.getModifyBy());
            js.put("detail", r.getDetail());
            roles.add(js);
        }
        return jsonObjects;
    }

    @Override
    public List<Role> findByRoleNames(String[] names) {
        return roleRepository.findByRoleNames(names);
    }
}