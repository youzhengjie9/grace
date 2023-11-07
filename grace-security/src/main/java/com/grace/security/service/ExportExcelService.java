package com.grace.security.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 导出excel服务
 *
 * @author youzhengjie
 * @date 2023-11-07 16:07:34
 */
public interface ExportExcelService {

    /**
     * 导出所有用户
     *
     * @param response       响应
     */
    void exportAllUser(HttpServletResponse response);

    /**
     * 导出所有角色
     *
     * @param response       响应
     */
    void exportAllRole(HttpServletResponse response);

    /**
     * 导出所有菜单
     *
     * @param response       响应
     */
    void exportAllMenu(HttpServletResponse response);

}
