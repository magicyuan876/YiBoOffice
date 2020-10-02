package com.yibo.office.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yibo.office.domain.SysFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yibo.office.mapper.FileInfoMapper;
import com.yibo.office.service.IFileInfoService;
import com.yibo.common.utils.text.Convert;

/**
 * 文件转换数据Service业务层处理
 * 
 * @author Magic_yuan
 * @date 2020-10-03
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper,SysFileInfo> implements IFileInfoService
{
    /**
     * 查询文件转换数据
     * 
     * @param id 文件转换数据ID
     * @return 文件转换数据
     */
    @Override
    public SysFileInfo selectFileInfoById(Integer id)
    {
        return this.baseMapper.selectFileInfoById(id);
    }

    /**
     * 查询文件转换数据列表
     * 
     * @param sysFileInfo 文件转换数据
     * @return 文件转换数据
     */
    @Override
    public List<SysFileInfo> selectFileInfoList(SysFileInfo sysFileInfo)
    {
        return this.baseMapper.selectFileInfoList(sysFileInfo);
    }

    /**
     * 新增文件转换数据
     * 
     * @param sysFileInfo 文件转换数据
     * @return 结果
     */
    @Override
    public int insertFileInfo(SysFileInfo sysFileInfo)
    {
        return this.baseMapper.insertFileInfo(sysFileInfo);
    }

    /**
     * 修改文件转换数据
     * 
     * @param sysFileInfo 文件转换数据
     * @return 结果
     */
    @Override
    public int updateFileInfo(SysFileInfo sysFileInfo)
    {
        return this.baseMapper.updateFileInfo(sysFileInfo);
    }

    /**
     * 删除文件转换数据对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteFileInfoByIds(String ids)
    {
        return this.baseMapper.deleteFileInfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除文件转换数据信息
     * 
     * @param id 文件转换数据ID
     * @return 结果
     */
    @Override
    public int deleteFileInfoById(Integer id)
    {
        return this.baseMapper.deleteFileInfoById(id);
    }
}
