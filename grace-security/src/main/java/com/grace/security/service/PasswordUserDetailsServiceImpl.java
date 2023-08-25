package com.grace.security.service;

import com.grace.common.entity.SysUser;
import com.grace.common.utils.Result;
import com.grace.security.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 帐号密码模式下的UserDetailsService实现类
 *
 * @author youzhengjie
 * @date 2023/08/25 17:02:37
 */
@Primary
public class PasswordUserDetailsServiceImpl implements CustomUserDetailsService {

	private SysUserFeign sysUserFeign;

	private SysMenuFeign sysMenuFeign;

	@Autowired
	public void setSysUserFeign(SysUserFeign sysUserFeign) {
		this.sysUserFeign = sysUserFeign;
	}

	@Autowired
	public void setSysMenuFeign(SysMenuFeign sysMenuFeign) {
		this.sysMenuFeign = sysMenuFeign;
	}

	/**
	 * 通过用户名构建UserDetails对象
	 *
	 * @param username 用户名
	 * @return
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {
		Result<SysUser> sysUserResponseResult =
				sysUserFeign.queryUserByUserName(username,"123");

		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUserResponseResult.getData());

		//查询用户菜单权限（就是查询Menu类中type=1和2的菜单权限标识perms，但是不包括type=0），并放到loginUser中返回
		Result<List<String>> permissionResponseResult =
				sysMenuFeign.getUserPermissionByUserId(sysUserResponseResult.getData().getId(), "123");
		List<String> permissions = permissionResponseResult.getData();
		if (permissions == null || permissions.size() == 0) {
			userInfo.setPermissions(new String[]{});
		}else {
			String[] perms = new String[permissions.size()];
			for (int i = 0; i < permissions.size(); i++) {
				perms[i]=permissions.get(i);
			}
			userInfo.setPermissions(perms);
		}

		return getUserDetails(Result.ok(userInfo));
	}

	@Override
	public int getOrder() {
		return Integer.MIN_VALUE;
	}

}
