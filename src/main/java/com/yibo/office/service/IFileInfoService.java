package com.yibo.office.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yibo.office.domain.SysFileInfo;

/**
 * 文件转换数据Service接口
 * 
 * @author Magic_yuan
 * @date 2020-10-03
 */
public interface IFileInfoService extends IService<SysFileInfo>
{
    /**
     * 查询文件转换数据
     * 
     * @param id 文件转换数据ID
     * @return 文件转换数据
     */
    public SysFileInfo selectFileInfoById(Integer id);

    /**
     * 查询文件转换数据列表
     * 
     * @param sysFileInfo 文件转换数据
     * @return 文件转换数据集合
     */
    public List<SysFileInfo> selectFileInfoList(SysFileInfo sysFileInfo);

    /**
     * 新增文件转换数据
     * 
     * @param sysFileInfo 文件转换数据
     * @return 结果
     */
    public int insertFileInfo(SysFileInfo sysFileInfo);

    /**
     * 修改文件转换数据
     * 
     * @param sysFileInfo 文件转换数据
     * @return 结果
     */
    public int updateFileInfo(SysFileInfo sysFileInfo);

    /**
     * 批量删除文件转换数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileInfoByIds(String ids);

    /**
     * 删除文件转换数据信息
     * 
     * @param id 文件转换数据ID
     * @return 结果
     */
    public int deleteFileInfoById(Integer id);
}
