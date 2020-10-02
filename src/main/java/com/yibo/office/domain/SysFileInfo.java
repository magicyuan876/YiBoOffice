package com.yibo.office.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yibo.framework.aspectj.lang.annotation.Excel;
import com.yibo.framework.web.domain.BaseEntity;
import lombok.Data;

/**
 * 文件转换数据对象 sys_file_info
 * 
 * @author Magic_yuan
 * @date 2020-10-03
 */
@Data
@TableName("sys_file_info")
public class SysFileInfo {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名
     */
    @Excel(name = "文件名")
    private String fileName;

    /**
     * 原始文件路径
     */
    @Excel(name = "原始文件路径")
    private String originalFilePath;

    /**
     * 原始文件md5值
     */
    @Excel(name = "原始文件md5值")
    private String fileMd5;

    /**
     * 转换后文件路径
     */
    @Excel(name = "转换后文件路径")
    private String destFilePath;

}
