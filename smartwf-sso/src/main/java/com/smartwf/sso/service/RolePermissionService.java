package com.smartwf.sso.service;

import com.smartwf.sso.dto.RolePermissionDTO;

/**
 * @Auther: WCH
 * @Date: 2018/9/21 17:18
 * @Description: 角色权限业务层接口
 */
public interface RolePermissionService {


    /**
     * 保存角色所拥有的权限
     * @return
     */
    Integer saveRolePermission(RolePermissionDTO rolePermission);

}
