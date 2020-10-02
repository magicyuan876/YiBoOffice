package com.yibo.office.controller;

import java.util.List;

import com.yibo.office.domain.SysFileInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yibo.framework.aspectj.lang.annotation.Log;
import com.yibo.framework.aspectj.lang.enums.BusinessType;
import com.yibo.office.service.IFileInfoService;
import com.yibo.framework.web.controller.BaseController;
import com.yibo.framework.web.domain.AjaxResult;
import com.yibo.common.utils.poi.ExcelUtil;
import com.yibo.framework.web.page.TableDataInfo;

/**
 * 文件转换数据Controller
 * 
 * @author Magic_yuan
 * @date 2020-10-03
 */
@Controller
@RequestMapping("/office/info")
public class FileInfoController extends BaseController
{
    private String prefix = "office/info";

    @Autowired
    private IFileInfoService fileInfoService;

    @RequiresPermissions("office:info:view")
    @GetMapping()
    public String info()
    {
        return prefix + "/info";
    }

    /**
     * 查询文件转换数据列表
     */
    @RequiresPermissions("office:info:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SysFileInfo sysFileInfo)
    {
        startPage();
        List<SysFileInfo> list = fileInfoService.selectFileInfoList(sysFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件转换数据列表
     */
    @RequiresPermissions("office:info:export")
    @Log(title = "文件转换数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysFileInfo sysFileInfo)
    {
        List<SysFileInfo> list = fileInfoService.selectFileInfoList(sysFileInfo);
        ExcelUtil<SysFileInfo> util = new ExcelUtil<SysFileInfo>(SysFileInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 新增文件转换数据
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存文件转换数据
     */
    @RequiresPermissions("office:info:add")
    @Log(title = "文件转换数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(SysFileInfo sysFileInfo)
    {
        return toAjax(fileInfoService.insertFileInfo(sysFileInfo));
    }

    /**
     * 修改文件转换数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap mmap)
    {
        SysFileInfo sysFileInfo = fileInfoService.selectFileInfoById(id);
        mmap.put("sysFileInfo", sysFileInfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件转换数据
     */
    @RequiresPermissions("office:info:edit")
    @Log(title = "文件转换数据", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(SysFileInfo sysFileInfo)
    {
        return toAjax(fileInfoService.updateFileInfo(sysFileInfo));
    }

    /**
     * 删除文件转换数据
     */
    @RequiresPermissions("office:info:remove")
    @Log(title = "文件转换数据", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(fileInfoService.deleteFileInfoByIds(ids));
    }
}
