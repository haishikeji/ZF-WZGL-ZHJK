package com.px.module.common.exception;


/**
 * 文件信息异常类
 * 
 * @author 品讯科技
 */
public class FileException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args)
    {
        super("file", code, args, null);
    }

}
