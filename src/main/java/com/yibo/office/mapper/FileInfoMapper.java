package com.yibo.office.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yibo.office.domain.SysFileInfo;

/**
 * 文件转换数据Mapper接口
 * 
 * @author Magic_yuan
 * @date 2020-10-03
 */
public interface FileInfoMapper extends BaseMapper<SysFileInfo>
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
     * 删除文件转换数据
     * 
     * @param id 文件转换数据ID
     * @return 结果
     */
    public int deleteFileInfoById(Integer id);

    /**
     * 批量删除文件转换数据
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteFileInfoByIds(String[] ids);
}
