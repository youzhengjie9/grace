package com.grace.security.controller;

import com.grace.common.constant.Constants;
import com.grace.common.constant.ParentMappingConstants;
import com.grace.common.dto.UserLoginDTO;
import com.grace.common.enums.ResultType;
import com.grace.common.utils.PageData;
import com.grace.common.utils.Result;
import com.grace.common.utils.SnowId;
import com.grace.common.vo.TokenVO;
import com.grace.common.vo.UserInfoVO;
import com.grace.security.dto.AssignRoleDTO;
import com.grace.security.dto.UserFormDTO;
import com.grace.security.entity.Role;
import com.grace.security.entity.User;
import com.grace.security.entity.UserRole;
import com.grace.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户控制器
 *
 * @author youzhengjie
 * @date 2023/09/06 19:43:30
 */
@RestController
@RequestMapping(path = ParentMappingConstants.USER_CONTROLLER)
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    /**
     * 登录
     *
     * @return {@link Result}<{@link TokenVO}>
     */
    @PostMapping("/login")
    public Result<TokenVO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) throws Throwable {
        // 调用登录方法
        TokenVO tokenVO = userService.login(userLoginDTO);
        return Result.build(ResultType.LOGIN_SUCCESS,tokenVO);
    }

    /**
     * 退出登录
     *
     * @param accessToken  accessToken
     * @param refreshToken refreshToken(没有可以不传)
     * @return {@link Result}<{@link Boolean}>
     */
    @PostMapping("/logout")
    public Result<Boolean> logout(@RequestHeader(value = "accessToken") String accessToken,
                                  @RequestHeader(value = "refreshToken",required = false) String refreshToken){

        return Result.ok(userService.logout(accessToken,refreshToken));
    }

    /**
     * 获取当前用户信息(请求头要携带accessToken)
     *
     * @return {@link Result}<{@link UserInfoVO}>
     */
    @GetMapping("/getCurrentUserInfo")
    public Result<UserInfoVO> getCurrentUserInfo(HttpServletRequest request){
        return Result.ok(userService.getCurrentUserInfo(request));
    }

    /**
     * 获取所有用户信息并分页
     */
    @GetMapping(path = "/getUserList")
    public Result<PageData<User>> getUserList(int page, int size){
        // 如果 page < 1 ,则要把page恢复成 1 ,因为page的最小值就为 1
        if(page < 1){
            page = 1;
        }
        // 如果 size <= 0 ,则要把size恢复成 1 ,因为size的最小值为 1
        if(size <= 0 ){
            size = 1;
        }
        // 将当前页转成MySQL分页的起始位置！
        page = (page-1)*size;
        // 对size的大小进行限制,防止一次性获取太多的数据（下面的代码意思是一次“最多”获取500条记录,如果size的值小于500,则size还是原来的值不变）
        size = Math.min(size,500);
        try {
            PageData<User> pageData = new PageData<>();
            List<User> users = userService.getUserList(page, size);
            int totalCount = userService.getUserCount();
            pageData.setPagedList(users);
            pageData.setTotalCount(totalCount);
            return Result.ok(pageData);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 根据用户名获取用户列表并分页
     *
     * @param username 用户名
     * @param page     页面
     * @param size     大小
     * @return {@link Result}
     */
    @GetMapping(path = "/getUserListByUsername")
    public Result<PageData<User>> getUserListByUsername(@RequestParam("username") String username,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size){
        // 如果 page < 1 ,则要把page恢复成 1 ,因为page的最小值就为 1
        if(page < 1){
            page = 1;
        }
        // 如果 size <= 0 ,则要把size恢复成 1 ,因为size的最小值为 1
        if(size <= 0 ){
            size = 1;
        }
        // 将当前页转成MySQL分页的起始位置！
        page = (page-1)*size;
        // 对size的大小进行限制,防止一次性获取太多的数据（下面的代码意思是一次“最多”获取500条记录,如果size的值小于500,则size还是原来的值不变）
        size = Math.min(size,500);
        try {
            PageData<User> pageData = new PageData<>();
            List<User> users = userService.getUserListByUsername(username, page, size);
            int count = userService.getUserCountByUsername(username);
            pageData.setPagedList(users);
            pageData.setTotalCount(count);
            return Result.ok(pageData);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 添加用户
     *
     * @param userFormDTO 用户表单dto
     * @return {@link Result}
     */
    @PostMapping("/addUser")
    public Result<Object> addUser(@RequestBody @Valid UserFormDTO userFormDTO){
        try {
            String username = userFormDTO.getUsername();
            // 如果用户名是默认的grace,则不允许创建
            if(username.equals(Constants.DEFAULT_USERNAME)){
                return Result.fail(null);
            }
            //将密码进行加密
            String encodePassword = passwordEncoder.encode(userFormDTO.getPassword());
            userFormDTO.setPassword(encodePassword);
            userService.addUser(userFormDTO);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 修改用户
     *
     * @param userFormDTO 用户表单dto
     * @return {@link Result}
     */
    @PostMapping(path = "/modifyUser")
    public Result<Object> modifyUser(@RequestBody @Valid UserFormDTO userFormDTO){
        try {
            String username = userFormDTO.getUsername();
            // 如果用户名是默认的grace,则不允许修改
            if(username.equals(Constants.DEFAULT_USERNAME)){
                return Result.fail(null);
            }
            //如果密码不为空，则进行加密再存储到数据库中
            if(StringUtils.hasText(userFormDTO.getPassword())){
                //将密码进行加密
                String encodePassword = passwordEncoder.encode(userFormDTO.getPassword());
                userFormDTO.setPassword(encodePassword);
            }
            userService.modifyUser(userFormDTO);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @DeleteMapping(path = "/deleteUser")
    public Result<Object> deleteUser(@RequestParam("userId") long userId){
        try {
            // 默认grace用户的id为1001,不允许删除这个默认的用户
            final long graceUserId = 1001L;
            // 不允许删除默认的grace用户（ id为1001 ）
            if(userId == graceUserId){
                return Result.fail(null);
            }
            userService.deleteUser(userId);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(null);
        }

    }

    /**
     * 分配角色
     * 前端通过这个格式来传给这个接口：let jsonData={
     *         roles:this.assignRoleSelectedList,
     *         userid:this.currentAssignRoleUserId
     *       }
     * @param assignRoleDTO 分配角色dto
     * @return {@link Result}
     */
    @PostMapping(path = "/assignRole")
    public Result<Object> assignRole(@RequestBody @Valid AssignRoleDTO assignRoleDTO){

        try {
            // 用户需要分配的角色集合
            List<Role> roles = assignRoleDTO.getRoles();
            List<Long> roleIds;
            // 如果用户不需要分配任何角色
            if(roles == null || roles.size()==0){
                // 空集合
                roleIds = new ArrayList<>();
            }
            // 如果用户需要分配角色
            else {
                // 获取用户需要分配的角色id集合
                roleIds = assignRoleDTO
                        .getRoles()
                        .stream()
                        .map(Role::getId)
                        //要进行去重
                        .distinct()
                        .collect(Collectors.toList());
            }
            // 获取需要分配角色的用户id
            long userId = Long.parseLong(assignRoleDTO.getUserId());
            List<UserRole> userRoleList= Collections.synchronizedList(new ArrayList<>());
            for (Long roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setId(SnowId.nextId());
                userRole.setRoleId(roleId);
                userRole.setUserId(userId);
                userRoleList.add(userRole);
            }
            //调用分配角色业务类
            userService.assignRoleToUser(userId,userRoleList);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(null);
        }

    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return {@link Result}<{@link User}>
     */
    @GetMapping(path = "/getUserByUsername/{username}")
    public Result<User> getUserByUsername(@PathVariable("username") String username){

        return Result.ok(userService.lambdaQuery().eq(User::getUsername, username).one());
    }

}
