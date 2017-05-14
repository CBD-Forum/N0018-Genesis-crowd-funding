package com.fbd.core.common.ftp;

import java.io.InputStream;
import javax.servlet.http.HttpServletResponse;

public interface FTPManager {
    
    /**
     * Description: 向FTP服务器上传文件
     * @Version1.0 Jul 27, 2008 4:31:09 PM by 董忠伟 创建
     * @param path FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    boolean uploadFile(String path, String filename, InputStream input);
    
    /**
     * Description: 向FTP服务器下载文件
     * @Version1.0 Jul 27, 2008 4:31:09 PM by 董忠伟 创建
     * @param path FTP服务器下载目录
     * @param filename 上传到FTP服务器上的文件名
     * @return 输出流
     */
    InputStream  downFile(String path, String fileName,HttpServletResponse response); 
    
    boolean deleteFile(String filename);
}
