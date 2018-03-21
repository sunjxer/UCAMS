package com.ucams.test.test;

import java.io.File;
import java.security.cert.*;
import java.util.List;

import com.kinggrid.pdf.KGPdfHummer;

/**
 * @author 作者: sunjx
 * @version 创建时间：2017年8月9日 类说明
 */
public class PDFTest {

	public static void getSigninfo(String pdfFile) {

		KGPdfHummer hummer = null;
		try {
			hummer = KGPdfHummer.createInstance(pdfFile, null, true);
			List<Certificate> certificates = hummer.getSignatureCertificates();
			System.out.println("证书的个数：" + certificates.size());
			for (Certificate certificate : certificates) {
				X509Certificate x509Certificate = (X509Certificate) certificate;
				System.out.println(x509Certificate.getSubjectDN().getName());
				System.out.println(x509Certificate.getIssuerDN().getName());
				System.out.println(x509Certificate.getSigAlgName());
				System.out.println(x509Certificate.getVersion());
				System.out.println(x509Certificate.getType());
				System.out.println("证书验证结果："
						+ getResult(hummer.verifySignatures()));

				/*
				 * -1：文档不存在数字签名。 0：至少有一个签名是无效的。 1：所有签名有效。 2：所有签名有效，最后一次签名后追加了内容。
				 */

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (hummer != null)
				hummer.close();
		}
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

	public static void main(String[] args) {
		getSigninfo("E://test02.pdf");
	}
}
