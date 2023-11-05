package com.grace.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.security.dto.UserFormDTO;
import com.grace.common.vo.TokenVO;
import com.grace.common.vo.UserInfoVO;
import com.grace.security.constant.JwtConstants;
import com.grace.common.dto.UserLoginDTO;
import com.grace.security.entity.User;
import com.grace.security.entity.UserRole;
import com.grace.security.mapper.UserMapper;
import com.grace.security.service.UserService;
import com.grace.security.token.TokenManagerDelegate;
import com.grace.security.users.GraceUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * user service impl
 *
 * @author youzhengjie
 * @date 2023/09/08 22:29:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private TokenManagerDelegate tokenManagerDelegate;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Override
    public TokenVO login(UserLoginDTO userLoginDTO) throws Throwable {
        // 前端传来的帐号
        String username = userLoginDTO.getUsername();
        // 前端传来的密码
        String password = userLoginDTO.getPassword();
        // UsernamePasswordAuthenticationToken两个参数的构造方法就是用来分别传递帐号密码的。（这里我们一定要使用这个）
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username,password);
        // 使用AuthenticationManager的authenticate方法对帐号密码进行验证
        // 重点1: authenticationManager.authenticate底层原理:
        //   (1): authenticationManager.authenticate底层就是调用了UserDetailsService的loadUserByUserName方法，获取到UserDetails对象（也就是GraceUser对象）
        //   (2): 将usernamePasswordAuthenticationToken（前端传入的帐号密码）和loadUserByUsername中的userMapper.selectOne(lambdaQueryWrapper)方法查询的帐号密码进行比对，判断帐号密码输入是否正确。
        //   (3): 如果验证失败的话，就会在loadUserByUsername方法中抛出异常并且被AuthenticationEntryPointImpl方法捕获
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 注意: 走到这里说明帐号密码验证正确,因为如果帐号密码不正确的话authenticationManager.authenticate方法会抛出异常,就走不到这里了

        // 让SpringSecurity知道我们已经登录成功
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 获取登录成功后的UserDetails对象（例如: GraceUser对象）
        GraceUser graceUser = (GraceUser) authentication.getPrincipal();
        long userId = graceUser.getUserId();
        // 生成accessToken和refreshToken
        Map<String, String> tokenMap = tokenManagerDelegate.createAccessTokenAndRefreshToken(userId);
        String accessToken = tokenMap.get(JwtConstants.ACCESS_TOKEN);
        String refreshToken = tokenMap.get(JwtConstants.REFRESH_TOKEN);
        // 把accessToken和refreshToken返回出去
        TokenVO tokenVO = new TokenVO();
        tokenVO.setAccessToken(accessToken);
        tokenVO.setRefreshToken(refreshToken);
        return tokenVO;
    }

    @Override
    public Boolean logout(String accessToken,String refreshToken) {

        try {
            // 删除accessToken
            tokenManagerDelegate.deleteAccessToken(accessToken);
            // 删除refreshToken
            tokenManagerDelegate.deleteRefreshToken(refreshToken);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public UserInfoVO getCurrentUserInfo() {
        // 从SpringSecurity中获取当前用户的UserDetails对象
        GraceUser graceUser=(GraceUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return new UserInfoVO(graceUser.getUsername());
    }


    @Override
    public List<User> getUserList(int page, int size) {
        return userMapper.getUserList(page, size);
    }

    @Override
    public int getUserCount() {
        return userMapper.getUserCount();
    }

    @Override
    public List<User> getUserListByUsername(String username, int page, int size) {
        return userMapper.getUserListByUsername(username, page, size);
    }

    @Override
    public int getUserCountByUsername(String username) {
        return userMapper.getUserCountByUsername(username);
    }

    @Override
    public int addUser(UserFormDTO userFormDTO) {

        User user = new User();
        BeanUtils.copyProperties(userFormDTO,user);

        user.setStatus(userFormDTO.getStatus() ?0:1);
        //然后再补充一些前端没有传过来的属性
        user.setCreateTime(LocalDate.now());
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(UserFormDTO userFormDTO) {

        User user = new User();
        BeanUtils.copyProperties(userFormDTO,user);

        user.setStatus(userFormDTO.getStatus() ?0:1);
        //然后再补充一些前端没有传过来的属性
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.updateUser(user);
    }

    @Override
    public boolean deleteUser(long id) {
        try {
            //删除用户
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getId,id);
            userMapper.delete(lambdaQueryWrapper);
            //删除用户所拥有的所有角色
            userMapper.deleteUserAllRoles(id);
            return true;
        }catch (Exception e){
            throw new RuntimeException("删除用户失败");
        }
    }

    @Override
    public int deleteUserAllRoles(long userid) {
        return userMapper.deleteUserAllRoles(userid);
    }


    /**
     * 将角色分配给用户
     *
     * @param userRoleList 用户角色列表
     * @return boolean
     */
    @Override
    public boolean assignRoleToUser(List<UserRole> userRoleList) {
        try {
            //先删除用户所有角色
            userMapper.deleteUserAllRoles(userRoleList.get(0).getUserId());
            //再把所有新的角色（包括以前选过的）都重新添加到数据库中
            userMapper.addRoleToUser(userRoleList);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("assignRoleToUser异常,事务已回滚。");
        }
    }

    @Override
    public int addRoleToUser(List<UserRole> userRoleList) {
        return userMapper.addRoleToUser(userRoleList);
    }


}
