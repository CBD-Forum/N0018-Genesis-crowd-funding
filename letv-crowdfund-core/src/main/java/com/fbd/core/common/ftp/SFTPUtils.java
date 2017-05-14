package com.fbd.core.common.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fbd.core.app.config.dao.ISysConfigDao;
import com.fbd.core.app.config.dao.impl.SysConfigDaoImpl;
import com.fbd.core.util.spring.SpringUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtils implements FTPManager {
    
    private static final Logger logger = LoggerFactory.getLogger(SFTPUtils.class);
    
    private ChannelSftp sftp = null;
    private String localPath = "/var/test";
    private String remotePath = "/var/tset";
    private String fileListPath = "/var/test/java/test.txt";
    private final String seperator = "/";
    
    private static ISysConfigDao sysConfigDao = (SysConfigDaoImpl) SpringUtil.getBean("sysConfigDaoImpl");
    
    /**
     * connect server via sftp
     */
    public void connect() {
        try {
            if(sftp != null){
                logger.info("sftp is not null");
            }
            String host = sysConfigDao.getByDisplayName("ftp_hostname").getCode();
            int port = Integer.parseInt(sysConfigDao.getByDisplayName("ftp_port").getCode());
            String username = sysConfigDao.getByDisplayName("ftp_username").getCode(); 
            String password = sysConfigDao.getByDisplayName("ftp_password").getCode();
            
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            logger.info("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.info("Session connected.");
            logger.info("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("Connected to " + host + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Disconnect with server
     */
    public void disconnect() {
        if(this.sftp != null){
            if(this.sftp.isConnected()){
                this.sftp.disconnect();
            }else if(this.sftp.isClosed()){
                logger.info("sftp is closed already.");
            }
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

    public void download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * upload all the files to the server
     */
    public void upload() {
        List<String> fileList = this.getFileEntryList(fileListPath);
        try {
            if(fileList != null){
                for (String filepath : fileList) {
                    String localFile = this.localPath + this.seperator+ filepath;
                    File file = new File(localFile);
                    
                    if(file.isFile()){
                        logger.info("localFile : " + file.getAbsolutePath());
                        String remoteFile = this.remotePath + this.seperator + filepath;
                        logger.info("remotePath:" + remoteFile);
                        File rfile = new File(remoteFile);
                        String rpath = rfile.getParent();
                        try {
                            createDir(rpath, sftp);
                        } catch (Exception e) {
                            logger.info("*******create path failed" + rpath);
                        }

                        this.sftp.put(new FileInputStream(file), file.getName());
                        logger.info("=========upload down for " + localFile);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Description: 向FTP服务器上传文件
     * @Version1.0 Jul 27, 2008 4:31:09 PM by 董忠伟 创建
     * @param path FTP服务器保存目录
     * @param filename 上传到FTP服务器上的文件名
     * @param input 输入流
     * @return 成功返回true，否则返回false
     */
    public synchronized boolean uploadFile(String path,String filename,InputStream input) {
        boolean success = false;
        try {
            path = getPath (path);
            String remoteFile = path+this.seperator+filename;
            if (path.endsWith("/")) {
                remoteFile = path+filename;
            }
            logger.info("remotePath:" + remoteFile);
            File rfile = new File(remoteFile);
            String rpath = rfile.getParent();
            try {
                connect();
                createDir(path, sftp);
            } catch (Exception e) {
                logger.info("*******create path failed" + rpath);
            }
            this.sftp.put(input, filename);
            success = true;
        } catch (SftpException e) {
            e.printStackTrace();
            success = false;
        }finally{
            disconnect();
        }
        return success;
    }
    
    /**
     * Description: 向FTP服务器下载文件
     * @Version1.0 Jul 27, 2008 4:31:09 PM by 董忠伟 创建
     * @param path FTP服务器下载目录
     * @param filename 上传到FTP服务器上的文件名
     * @return 输出流
     */
    public synchronized InputStream downFile(String path, String fileName,HttpServletResponse response) {
        InputStream is = null;
        try {
            connect();
            sftp.cd(path);
            is = sftp.get(fileName);
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
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            disconnect();
        }
        return null;
    }
    
    
    /**
     * create Directory
     * @param filepath
     * @param sftp
     */
    private synchronized void createDir(String filepath, ChannelSftp sftp){
        try {
            this.sftp.cd("/"+filepath);
        } catch (Exception e) {
            String files[] = filepath.split("/");
            for (int i = 0; i < files.length; i++) {
                String file=files[i];
                if (i==0) {
                    file="/"+file;
                }
                try {
                    this.sftp.cd(file);
                } catch (Exception e1) {
                    try {
                        this.sftp.mkdir(file);
                        logger.info("mkdir failed :" + file);
                        this.sftp.cd(file);
                    } catch (Exception e2) {
                    }
                }
            }
        }
        
    }
    /**
     * get all the files need to be upload or download
     * @param file
     * @return
     */
    private List<String> getFileEntryList(String file){
        ArrayList<String> fileList = new ArrayList<String>();
        InputStream in = null;
        try {
            
            in = new FileInputStream(file);
            InputStreamReader inreader = new InputStreamReader(in);
            
            LineNumberReader linreader = new LineNumberReader(inreader);
            String filepath = linreader.readLine();
            while(filepath != null){
                fileList.add(filepath);
                filepath = linreader.readLine();
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(in != null){
                in = null;
            }
        }

        return fileList;
    }

    /**
     * @return the sftp
     */
    public ChannelSftp getSftp() {
        return sftp;
    }

    /**
     * @param sftp the sftp to set
     */
    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }

    /**
     * @return the localPath
     */
    public String getLocalPath() {
        return localPath;
    }

    /**
     * @param localPath the localPath to set
     */
    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    /**
     * @return the remotePath
     */
    public String getRemotePath() {
        return remotePath;
    }

    /**
     * @param remotePath the remotePath to set
     */
    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    /**
     * @return the fileListPath
     */
    public String getFileListPath() {
        return fileListPath;
    }

    /**
     * @param fileListPath the fileListPath to set
     */
    public void setFileListPath(String fileListPath) {
        this.fileListPath = fileListPath;
    }
    
    public static void main(String[] args) {
        SFTPUtils ftp= new SFTPUtils();
        ftp.connect();
        ftp.upload();
        ftp.disconnect();
        System.exit(0);
    }

    public boolean deleteFile(String filename) {
        try {
            String directory = filename.substring(0, filename.lastIndexOf("/"));
            String deleteFile = filename.substring(filename.lastIndexOf("/"));
            sftp.cd(directory);
            sftp.rm(deleteFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
