package com.grace.security.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.security.dto.AssignRoleDTO;
import com.grace.security.dto.UserFormDTO;
import com.grace.common.enums.ResultType;
import com.grace.common.utils.Result;
import com.grace.common.utils.SnowId;
import com.grace.common.vo.TokenVO;
import com.grace.common.vo.UserInfoVO;
import com.grace.common.dto.UserLoginDTO;
import com.grace.security.entity.Role;
import com.grace.security.entity.User;
import com.grace.security.entity.UserRole;
import com.grace.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("@pms.hasPermission('user:list')")
    @GetMapping(path = "/getUserList")
    public Result<List<User>> getUserList(int page, int size){
        page=(page-1)*size;
        try {
            List<User> users = userService.getUserList(page, size);
            return Result.ok(users);
        }catch (Exception e){
            return Result.fail(null);
        }
    }
    /**
     * 查询所有用户数量
     *
     * @return {@link Result}
     */
    @PreAuthorize("@pms.hasPermission('user:list')")
    @GetMapping(path = "/getUserCount")
    public Result<Integer> getUserCount(){
        try {
            int count = userService.getUserCount();
            return Result.ok(count);
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
    @PreAuthorize("@pms.hasPermission('user:list')")
    @GetMapping(path = "/getUserListByUsername")
    public Result<List<User>> getUserListByUsername(@RequestParam("username") String username,
                                                    @RequestParam("page") int page,
                                                    @RequestParam("size") int size){
        page=(page-1)*size;
        try {
            List<User> users = userService.getUserListByUsername(username, page, size);
            return Result.ok(users);
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 根据用户名获取用户数量
     *
     * @param username 用户名
     * @return {@link Result}
     */
    @PreAuthorize("@pms.hasPermission('user:list')")
    @GetMapping(path = "/getUserCountByUsername")
    public Result<Integer> getUserCountByUsername(@RequestParam("username") String username){

        try {
            int count = userService.getUserCountByUsername(username);
            return Result.ok(count);
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
    @PreAuthorize("@pms.hasPermission('user:list:add')")
    @PostMapping("/addUser")
    public Result<Object> addUser(@RequestBody @Valid UserFormDTO userFormDTO){
        try {
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
    @PreAuthorize("@pms.hasPermission('user:list:update')")
    @PostMapping(path = "/updateUser")
    public Result<Object> updateUser(@RequestBody @Valid UserFormDTO userFormDTO){
        try {
            //如果密码不为空，则进行加密再存储到数据库中
            if(StringUtils.hasText(userFormDTO.getPassword())){
                //将密码进行加密
                String encodePassword = passwordEncoder.encode(userFormDTO.getPassword());
                userFormDTO.setPassword(encodePassword);
            }
            userService.updateUser(userFormDTO);
            return Result.ok();
        }catch (Exception e){
            return Result.fail(null);
        }
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return {@link Result}
     */
    @PreAuthorize("@pms.hasPermission('user:list:delete')")
    @DeleteMapping(path = "/deleteUser")
    public Result<Object> deleteUser(@RequestParam("id") long id){
        try {
            userService.deleteUser(id);
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
    @PreAuthorize("@pms.hasPermission('user:list:assign-role')")
    @PostMapping(path = "/assignRole")
    public Result<Object> assignRole(@RequestBody @Valid AssignRoleDTO assignRoleDTO){

        try {
            if(assignRoleDTO.getRoles()==null || assignRoleDTO.getRoles().size()==0){
                return Result.ok();
            }
            //通过stream流把role的id组成一个新的集合
            List<Long> roleIds = assignRoleDTO
                    .getRoles()
                    .stream()
                    .map(Role::getId)
                    //要进行去重
                    .distinct()
                    .collect(Collectors.toList());
            long userid = Long.parseLong(assignRoleDTO.getUserid());

            List<UserRole> userRoleList= Collections.synchronizedList(new ArrayList<UserRole>());
            for (Long roleId : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setId(SnowId.nextId());
                userRole.setRoleId(roleId);
                userRole.setUserId(userid);
                userRoleList.add(userRole);
            }
            //调用分配角色业务类
            userService.assignRoleToUser(userRoleList);
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
