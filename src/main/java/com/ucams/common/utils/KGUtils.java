package com.ucams.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kinggrid.pdf.KGPdfHummer;
import com.ucams.common.mapper.JsonMapper;

/** 
 * @author 作者: sunjx 
 * @version 创建时间：2017年8月9日
 *  金格 签章检测
 */
public class KGUtils {
	private static Logger logger = LoggerFactory.getLogger(KGUtils.class);
	
	/**
	 * 检测PDF文件签章状态
	 * @param pdfFile 文件路径
	 * @return  -1：文档不存在数字签名。 0：至少有一个签名是无效的。 1：所有签名有效。 
	 * 					2：所有签名有效，最后一次签名后追加了内容。
	 * @throws GeneralSecurityException 
	 * @throws IOException 
	 */
	public static String getSigninfo(InputStream pdfFile) throws IOException, GeneralSecurityException {

		KGPdfHummer hummer = null;
		String result = "";
		try {
			hummer = KGPdfHummer.createInstance(pdfFile, null);
			List<Certificate> certificates = hummer.getSignatureCertificates();
			logger.info("文件: "+pdfFile +"  证书的个数：" + certificates.size());
			for (Certificate certificate : certificates) {
				X509Certificate x509Certificate = (X509Certificate) certificate;
//				logger.info("文件: "+pdfFile +"  [" + x509Certificate.getSubjectDN().getName() + "]");
//				logger.info("文件: "+pdfFile +"  [" + x509Certificate.getIssuerDN().getName() + "]");
//				logger.info("文件: "+pdfFile +"  [" + x509Certificate.getSigAlgName() + "]");
//				logger.info("文件: "+pdfFile +"  [" + x509Certificate.getVersion() + "]");
//				logger.info("文件: "+pdfFile +"  [" + x509Certificate.getType() + "]");
				//解析检测结果
				//-1：文档不存在数字签名。 0：至少有一个签名是无效的。 1：所有签名有效。 2：所有签名有效，最后一次签名后追加了内容。
				 result = getResult(hummer.verifySignatures());
				logger.info("文件: "+pdfFile +"  证书验证结果："+"[" + result + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (hummer != null)
				hummer.close();
		}
		
		return result;
	}
	
	public static String getResult(String i) {

		String str = "";
		switch (i) {
		case "1":
			str = "所有签名有效";
			break;
		case "2":
			str = "所有签名有效，最后一次签名后追加了内容";
			break;
		case "0":
			str = "至少有一个签名是无效的。";
			break;
		case "-1":
			str = "文档不存在数字签名";
			break;
		default:
			break;

		}
		return str;
	}
	
	public static void main(String[] args) throws IOException, GeneralSecurityException {
		//String a = getSigninfo("G:\\e9398e8f-84c2-42ec-ab9b-247be98498d9.pdf");
		//System.out.println(a);
		try {
			URL url = new URL("http://192.168.0.215:9090/online/projectFile/5f36aadf2602491999938a784ad966e7/1059f05752c840bfb9925a0c4b9b7da7/20170925152511_Ge81P0.pdf");
			URLConnection urlConn = url.openConnection();
			InputStream netFileInputStream = urlConn.getInputStream();
			System.out.println(netFileInputStream);
			File file = new File("E://testfile//test.pdf");
			InputStream in = new FileInputStream(file);
			String map = getSigninfo(netFileInputStream);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
