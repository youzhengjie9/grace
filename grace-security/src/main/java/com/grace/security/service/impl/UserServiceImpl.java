package com.grace.security.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grace.common.constant.Constants;
import com.grace.common.constant.TokenTypeConstants;
import com.grace.common.utils.SnowId;
import com.grace.security.dto.UserFormDTO;
import com.grace.common.vo.TokenVO;
import com.grace.common.vo.UserInfoVO;
import com.grace.security.constant.JwtConstants;
import com.grace.common.dto.UserLoginDTO;
import com.grace.security.entity.User;
import com.grace.security.entity.UserRole;
import com.grace.security.mapper.UserMapper;
import com.grace.security.service.MenuService;
import com.grace.security.service.MenuTreeService;
import com.grace.security.service.UserService;
import com.grace.security.token.TokenManagerDelegate;
import com.grace.security.users.GraceUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuTreeService menuTreeService;

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
    public UserInfoVO getCurrentUserInfo(HttpServletRequest request) {
        // 创建UserInfoVO对象
        UserInfoVO userInfoVO = new UserInfoVO();
        // 获取accessToken
        String accessToken = request.getHeader("accessToken");
        // 获取这个accessToken对应的用户认证（Authentication）对象
        Authentication authentication =
                tokenManagerDelegate.getAuthentication(accessToken, "accessToken");
        // 从用户认证（Authentication）对象,获取对应的用户信息
        GraceUser graceUser = (GraceUser) authentication.getPrincipal();
        userInfoVO.setUsername(graceUser.getUsername());
        Long userId = graceUser.getUserId();
        // 获取用户动态菜单（侧边栏）
        String dynamicMenu = menuTreeService.buildTreeByUserId(userId);
        // 由于VUE动态路由刷新会丢失，所以需要再获取获取该用户的所有路由（只包含类型为菜单，type=1的菜单）
        String dynamicRouter = menuService.getRouterByUserId(userId);
        userInfoVO.setDynamicMenu(dynamicMenu);
        userInfoVO.setDynamicRouter(dynamicRouter);
        // 设置用户权限perm
        List<String> perms = menuService.getUserPermissionByUserId(userId);
        userInfoVO.setPerm(JSON.toJSONString(perms));
        return userInfoVO;
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
        // 生成id
        user.setId(SnowId.nextId());
        user.setUsername(userFormDTO.getUsername());
        user.setPassword(userFormDTO.getPassword());
        user.setStatus(userFormDTO.getStatus() ?0:1);
        //然后再补充一些前端没有传过来的属性
        user.setCreateTime(LocalDate.now());
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.insert(user);
    }

    @Override
    public int modifyUser(UserFormDTO userFormDTO, HttpServletRequest request) {
        User user = new User();
        Long modifyUserId = userFormDTO.getId();
        user.setId(modifyUserId);
        user.setUsername(userFormDTO.getUsername());
        user.setPassword(userFormDTO.getPassword());
        user.setStatus(userFormDTO.getStatus() ?0:1);
        //然后再补充一些前端没有传过来的属性
        user.setUpdateTime(LocalDateTime.now());
        return userMapper.modifyUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        try {
            //删除用户
            LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(User::getId,userId);
            userMapper.delete(lambdaQueryWrapper);
            //删除用户所拥有的所有角色
            userMapper.deleteUserAllRoles(userId);
            return true;
        }catch (Exception e){
            throw new RuntimeException("删除用户失败");
        }
    }

    @Override
    public int deleteUserAllRoles(long userId) {
        return userMapper.deleteUserAllRoles(userId);
    }


    /**
     * 给用户分配角色
     *
     * @param userId 用户id
     * @param userRoleList 用户角色列表
     * @return boolean
     */
    @Override
    public boolean assignRoleToUser(Long userId,List<UserRole> userRoleList) {
        try {
            //先删除用户所有角色
            userMapper.deleteUserAllRoles(userId);
            // 如果需要分配角色
            if(userRoleList != null && userRoleList.size() > 0) {
                //再把所有新的角色（包括以前选过的）都重新添加到数据库中
                userMapper.addRoleToUser(userRoleList);
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("assignRoleToUser异常,事务已回滚。");
        }
    }

}
