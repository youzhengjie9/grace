package com.grace.security.dto;

import com.grace.common.entity.SysUser;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 用户信息
 *
 * @author youzhengjie
 * @date 2023/08/25 01:01:04
 */
public class UserInfo implements Serializable {

	/**
	 * 用户基本信息
	 */
	private SysUser sysUser;

	/**
	 * 权限标识集合
	 */
	private String[] permissions;

//	/**
//	 * 角色集合
//	 */
//	private Long[] roles;
//
//	/**
//	 * 角色集合
//	 */
//	private List<SysRole> roleList;


	public UserInfo() {
	}

	public UserInfo(SysUser sysUser, String[] permissions) {
		this.sysUser = sysUser;
		this.permissions = permissions;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String[] getPermissions() {
		return permissions;
	}

	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
				"sysUser=" + sysUser +
				", permissions=" + Arrays.toString(permissions) +
				'}';
	}
}
