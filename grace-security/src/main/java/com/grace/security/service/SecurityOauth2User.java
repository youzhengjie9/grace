package com.grace.security.service;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class SecurityOauth2User extends User implements OAuth2AuthenticatedPrincipal {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final Map<String, Object> attributes = new HashMap<>();

	/**
	 * 用户ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private final Long id;

	/**
	 * 手机号
	 */
	private final String phone;

	public SecurityOauth2User(Long id, String username, String password, String phone, boolean enabled,
							  boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
							  Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = id;
		this.phone = phone;
	}

	/**
	 * Get the OAuth 2.0 token attributes
	 * @return the OAuth 2.0 token attributes
	 */
	@Override
	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	@Override
	public String getName() {
		return this.getUsername();
	}


	public Long getId() {
		return id;
	}

	public String getPhone() {
		return phone;
	}
}
