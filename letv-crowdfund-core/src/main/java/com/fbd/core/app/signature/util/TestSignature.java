package com.fbd.core.app.signature.util;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.kinggrid.pdf.KGPdfHummer;
import com.kinggrid.pdf.executes.PdfSignature4KG;
public class TestSignature {
	public static void main(String[] args) {
		KGPdfHummer hummer = null;
		FileInputStream cert = null;
		FileOutputStream  fileOutputStream = null;
		try {
			//加载数字证书（pfx文件）
			cert = new FileInputStream("E:/test/iSignature.pfx");
			//设置输出路径
			fileOutputStream = new FileOutputStream("E:/test/test_done1.pdf");
			File tmpDic = new File("E:/testing/");
			//设置需要签名的PDF文件路径
			String srcFilePathString = "E:/test/20160817.pdf";
			//初始化签名类
			hummer = KGPdfHummer.createSignature(srcFilePathString, null, 
					true, fileOutputStream,tmpDic,true);
			//加载数字证书密码
			hummer.setCertificate(cert, "123456", "123456");
			//加载时间戳服务地址、用户名、密码
			hummer.setTSAClient("http://test1.tsa.cn/tsa", "tsademo", "tsademo");
			//加载电子图章 *.key文件  ，正式版本的电子签章软件需设置为1 
			PdfSignature4KG pdfSignature4KG = new PdfSignature4KG("E:/test/unitrust.key",0,"123456");
			//获取总页数
			int num=hummer.getNumberOfPages();
			//设置签名所在页数（设置签名在最后一页）
			pdfSignature4KG.setPagen(num);
			//设置签名 0表示所有页
			//pdfSignature4KG.setPagen(0);
			//设置签章的固定位置
			pdfSignature4KG.setXY(200,200);
			//执行签名方法
			hummer.setPdfSignature(pdfSignature4KG);	
			hummer.doSignature();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			close(cert);
			close(fileOutputStream);
			if(hummer != null) hummer.close();
		}
	}
	public static void close(Closeable stream){
		try {
			if(stream != null) stream.close();
		} catch (IOException e) {
		}
	}
}
