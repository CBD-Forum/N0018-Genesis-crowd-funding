package com.fbd.core.common.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.dao.impl.SysConfigDaoImpl;
import com.fbd.core.util.spring.SpringUtil;

public class FTPUtils implements FTPManager {
    private static final Logger logger = LoggerFactory.getLogger(FTPUtils.class);
    
    private FTPClient ftp = null;

    private ISysConfigDao sysConfigDao = (SysConfigDaoImpl) SpringUtil.getBean("sysConfigDaoImpl");
    
    /**
     * Description: 创建FTP连接
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-14 下午3:52:22
     */
    public void connect() {
        String url = sysConfigDao.getByDisplayName("ftp_hostname").getCode();
        int port = Integer.parseInt(sysConfigDao.getByDisplayName("ftp_port").getCode());
        String username = sysConfigDao.getByDisplayName("ftp_username").getCode(); 
        String password = sysConfigDao.getByDisplayName("ftp_password").getCode();
        if(ftp != null){
            logger.info("ftp is not null");
        }
        int reply;
        try {
            ftp = new FTPClient();
            ftp.enterLocalPassiveMode();
//            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.connect(url, port);//连接FTP服务器
            ftp.setControlEncoding("UTF-8");
            ftp.login(username, password);//登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Description: 关闭FTP连接
     *
     * @param 
     * @return void
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-14 下午3:52:41
     */
    public void disconnect() {
        try {
            if (ftp != null) {
                ftp.logout();
                if (ftp.isConnected()) {
                    ftp.disconnect();
                    logger.info("ftp is closed already.");
                }
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
    
    private String getPath (String path){
        String basePath = sysConfigDao.getByDisplayName("ftp_basePath").getCode();
        if (path.startsWith("/")) {
            path = basePath + path.substring(1);
        }else{
            path = basePath + path;
        }
        return path;
    }
    
    /**
     * Description: 向FTP服务器上传文件
     * @Version1.0 Jul 27, 2008 4:31:09 PM by 董忠伟 创建
     * @param path FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public synchronized boolean uploadFile(String path, String filename, InputStream input) {
        boolean success = false;
        path = getPath (path);
        try {
            //连接ftp
            connect();
            System.out.println("ftp====="+ftp);
            String paths[]=path.split("/");
            for (String p : paths) {
                if (ftp.changeWorkingDirectory(p)) {
                    System.out.println("进入目录==="+p);
               }else{
                   if (ftp.makeDirectory(p)) {
                       ftp.changeWorkingDirectory(p);
                       System.out.println("创建文件目录【"+p+"】 成功！");
                   }else{
                       System.out.println("创建文件目录【"+p+"】 失败！");
                   }
               }
            }
            
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.storeFile(filename, input);    
            input.close();
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            disconnect();
        }
        disconnect();
        return success;
    }
    
    /**
    * Description: 从FTP服务器下载文件
    * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
    * @param remotePath FTP服务器上的相对路径
    * @param fileName 要下载的文件名
    * @param localPath 下载后保存到本地的路径
    * @return
    */ 
    public synchronized boolean downFile(String remotePath,String fileName,String localPath) { 
        boolean success = false; 
        try { 
            int reply; 
            connect();
            reply = ftp.getReplyCode(); 
            if (!FTPReply.isPositiveCompletion(reply)) { 
                ftp.disconnect(); 
                return success; 
            } 
            ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录 
            FTPFile[] fs = ftp.listFiles(); 
            for(FTPFile ff:fs){ 
                if(ff.getName().equals(fileName)){ 
                    File localFile = new File(localPath+"/"+ff.getName()); 
                    OutputStream is = new FileOutputStream(localFile);  
                    ftp.retrieveFile(ff.getName(), is); 
                    is.close(); 
                } 
            } 
            success = true; 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally { 
           disconnect();
        } 
        return success; 
    }
    
    /**
     * Description: 从FTP服务器下载文件
     *
     * @param path 下载地址
     * @param path 下载文件
     * @return InputStream
     * @throws 
     * @Author dongzhongwei
     * Create Date: 2015-4-14 下午4:09:57
     */
    public synchronized InputStream  downFile(String path, String fileName,HttpServletResponse response) {
        InputStream is = null;
        try { 
            connect();
            path = getPath (path);
            ftp.changeWorkingDirectory(path);//转移到FTP服务器目录 
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            FTPFile[] fs = ftp.listFiles(); 
            for(FTPFile ff:fs){ 
                if(ff.getName().equals(fileName)){ 
                    is = ftp.retrieveFileStream(fileName); 
                    break;
                } 
            }
            OutputStream out = response.getOutputStream();  
            //写文件  
            byte[] buf = new byte[2048];  
            int length = is.read(buf);  
            while (length != -1) {  
                out.write(buf, 0, length);  
                length = is.read(buf);  
            }  
            is.close();  
            out.close();
            ftp.completePendingCommand();
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally {
            disconnect();
        } 
        return null; 
    }
    
    /** 
     * 删除一个文件 
     */ 
    public synchronized boolean deleteFile(String filename) {
        boolean flag = true;
        filename = getPath (filename);
        try {
            connect();
            flag = ftp.deleteFile(filename);
            if (flag) {
                logger.info("删除文件成功！");
                System.out.println();
            } else {
                logger.info("删除文件失败！");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }finally{
            disconnect();
        }
        return flag;
    }
}
