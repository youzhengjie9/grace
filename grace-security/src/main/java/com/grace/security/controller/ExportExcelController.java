package com.grace.security.controller;

import com.grace.common.constant.ParentMappingConstants;
import com.grace.security.service.ExportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 导出excel控制器
 *
 * @author youzhengjie
 * @date 2023-11-07 16:05:47
 */
@RestController
@RequestMapping(path = ParentMappingConstants.EXPORT_EXCEL_CONTROLLER)
public class ExportExcelController {

    @Autowired
    private ExportExcelService exportExcelService;

    /**
     * 导出所有用户
     *
     * @param response response
     */
    @GetMapping("/exportAllUser")
    public void exportAllUser(HttpServletResponse response){

        exportExcelService.exportAllUser(response);
    }


    /**
     * 导出所有角色
     *
     * @param response response
     */
    @GetMapping("/exportAllRole")
    public void exportAllRole(HttpServletResponse response){

        exportExcelService.exportAllRole(response);
    }

    /**
     * 导出所有菜单
     *
     * @param response response
     */
    @GetMapping("/exportAllMenu")
    public void exportAllMenu(HttpServletResponse response){

        exportExcelService.exportAllMenu(response);
    }

}
