/* 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 *
 * 本系统软件遵循Apache2.0开源协议.
 *
 * ============================================================
 *
 * FileName: FileUtils.java 
 *
 * Created: [2016-9-26 上午11:56:30] by haolingfeng
 *
 * $Id$
 * 
 * $Revision$
 *
 * $Author$
 *
 * $Date$
 *
 * ============================================================ 
 * 
 * ProjectName: letv-signature-core 
 * 
 * Description: 
 * 
 * ==========================================================*/

package com.fbd.core.app.signature.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.misc.BASE64Decoder;

/** 
 * Copyright (C) 2014-20162016-2017 NBIChain SunCrowdfund System.
 * 
 * Description: 
 *
 * @author zhangweilong
 * @version 1.0
 *
 */

public class FileUtils {
    
    /**
     * 将文件转换为base64字符串
     * Description: 
     * @param 
     * @return String
     * @throws 
     * @Author zhangweilong
     * Create Date: 2016-9-26 上午11:58:14
     */
    public static String fileToBase64Str(File filePdf){
      
        InputStream is = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            is = new FileInputStream(filePdf);
            byte[] b = new byte[1024];
            int n;
            while ((n = is.read(b)) != -1) {
                out.write(b, 0, n);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception ignore) {
                }
            }
        }
        byte[] bytesPdf = out.toByteArray();
        String doc =  new String(new Base64Encoder().encode(bytesPdf));
        return doc;
    }
    
    
    
    @SuppressWarnings("restriction")
    public static void createPDF(String doc,String fileName){
        
        BufferedInputStream bin = null;
        FileOutputStream fout = null;
        BufferedOutputStream bout = null;
        try { 
            BASE64Decoder decoder = new sun.misc.BASE64Decoder();   
            //处理base64字符串
            doc =doc.replace(" ", "+");
            //将base64编码的字符串解码成字节数组
            byte[] bytes = decoder.decodeBuffer(doc);
            //apache公司的API
            //byte[] bytes = Base64.decodeBase64(base64sString);
            //创建一个将bytes作为其缓冲区的ByteArrayInputStream对象
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            //创建从底层输入流中读取数据的缓冲输入流对象
            bin = new BufferedInputStream(bais);
            //指定输出的文件 
            File file = new File(fileName);
            //创建到指定文件的输出流
            fout  = new FileOutputStream(file);
            //为文件输出流对接缓冲输出流对象
            bout = new BufferedOutputStream(fout);
            byte[] buffers = new byte[1024];
            int len = bin.read(buffers);
            while(len != -1){
                bout.write(buffers, 0, len);
                len = bin.read(buffers);
            }
            //刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
            bout.flush();
         } catch (IOException e) {
                e.printStackTrace();
         }finally{
            try { 
                bin.close();
                fout.close();
                bout.close();
            } catch (IOException e) {
                 e.printStackTrace();
            } 
        }
    }
}
