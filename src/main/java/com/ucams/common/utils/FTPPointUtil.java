package com.ucams.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.Test;

/** */
/**
 * 支持断点续传的FTP实用类
 * 
 * @version 0.1 实现基本断点上传下载
 * @version 0.2 实现上传下载进度汇报
 * @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持
 */
public class FTPPointUtil {

	// 枚举类UploadStatus代码
	public enum UploadStatus {
		Create_Directory_Fail, // 远程服务器相应目录创建失败
		Create_Directory_Success, // 远程服务器闯将目录成功
		Upload_New_File_Success, // 上传新文件成功
		Upload_New_File_Failed, // 上传新文件失败
		File_Exits, // 文件已经存在
		Remote_Bigger_Local, // 远程文件大于本地文件
		Upload_From_Break_Success, // 断点续传成功
		Upload_From_Break_Failed, // 断点续传失败
		Delete_Remote_Faild; // 删除远程文件失败
	}

	// 枚举类DownloadStatus代码
	public enum DownloadStatus {
		Remote_File_Noexist, // 远程文件不存在
		Local_Bigger_Remote, // 本地文件大于远程文件
		Download_From_Break_Success, // 断点下载文件成功
		Download_From_Break_Failed, // 断点下载文件失败
		Download_New_Success, // 全新下载文件成功
		Download_New_Failed; // 全新下载文件失败
	}

	public FTPClient ftpClient = new FTPClient();
	private static PropertiesLoader loader = new PropertiesLoader("ucams.properties");
	
	String hostname,port,username,password;
	/**
	 * 构造
	 * @param username FTP虚拟用户名
	 * @param password FTP虚拟密码
	 */
	public FTPPointUtil(String username,String password){
		this.hostname = loader.getProperty("ftps.host");
		this.port = loader.getProperty("ftps.port");
		this.username = username;
		this.password = password;
	}
	
	/** */
	/**
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws IOException
	 */
	public boolean connect () throws IOException {
		ftpClient.connect(hostname, Integer.parseInt(port));
		ftpClient.setControlEncoding("UTF-8");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, password)) {
				return true;
			}
		}
		disconnect();
		return false;
	}

	/** */
	/**
	 * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @return 上传的状态
	 * @throws IOException
	 */
	public DownloadStatus download(String remote, String local)
			throws IOException {
		// 设置被动模式
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		DownloadStatus result;

		// 检查远程文件是否存在
		FTPFile[] files = ftpClient.listFiles(new String(
				remote.getBytes("UTF-8"), "iso-8859-1"));
		if (files.length != 1) {
			System.out.println("远程文件不存在");
			return DownloadStatus.Remote_File_Noexist;
		}

		long lRemoteSize = files[0].getSize();
		File f = new File(local);
		// 本地存在文件，进行断点下载
		if (f.exists()) {
			long localSize = f.length();
			// 判断本地文件大小是否大于远程文件大小
			if (localSize >= lRemoteSize) {
				System.out.println("本地文件大于远程文件，下载中止");
				return DownloadStatus.Local_Bigger_Remote;
			}

			// 进行断点续传，并记录状态
			FileOutputStream out = new FileOutputStream(f, true);
			ftpClient.setRestartOffset(localSize);
			InputStream in = ftpClient.retrieveFileStream(new String(remote
					.getBytes("UTF-8"), "iso-8859-1"));
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100;
			long process = localSize / step;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					//if (process % 10 == 0)
//						System.out.println("下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean isDo = ftpClient.completePendingCommand();
			if (isDo) {
				result = DownloadStatus.Download_From_Break_Success;
			} else {
				result = DownloadStatus.Download_From_Break_Failed;
			}
		} else {
			OutputStream out = new FileOutputStream(f);
			InputStream in = ftpClient.retrieveFileStream(new String(remote
					.getBytes("UTF-8"), "iso-8859-1"));
			byte[] bytes = new byte[1024];
			long step = lRemoteSize / 100;
			long process = 0;
			long localSize = 0L;
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localSize += c;
				long nowProcess = localSize / step;
				if (nowProcess > process) {
					process = nowProcess;
					//if (process % 10 == 0)
//						System.out.println("下载进度：" + process);
					// TODO 更新文件下载进度,值存放在process变量中
				}
			}
			in.close();
			out.close();
			boolean upNewStatus = ftpClient.completePendingCommand();
			if (upNewStatus) {
				result = DownloadStatus.Download_New_Success;
			} else {
				result = DownloadStatus.Download_New_Failed;
			}
		}
		return result;
	}

	/** */
	/**
	 * 上传文件到FTP服务器，支持断点续传
	 * 
	 * @param local
	 *            本地文件名称，绝对路径
	 * @param remote
	 *            远程文件路径，使用/home/directory1/subdirectory/file.ext或是
	 *            http://www.guihua.org /subdirectory/file.ext
	 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @return 上传结果
	 * @throws IOException
	 */
	public UploadStatus upload(File local, String remote) throws IOException {
		// 设置PassiveMode传输
		ftpClient.enterLocalPassiveMode();
		// 设置以二进制流的方式传输
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.setControlEncoding("UTF-8");
		UploadStatus result;
		// 对远程目录的处理
		String remoteFileName = remote;
		if (remote.contains("/")) {
			remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
			// 创建服务器远程目录结构，创建失败直接返回
			if (CreateDirecroty(remote, ftpClient) == UploadStatus.Create_Directory_Fail) {
				return UploadStatus.Create_Directory_Fail;
			}
		}

		// 检查远程是否存在文件
		FTPFile[] files = ftpClient.listFiles(new String(remoteFileName
				.getBytes("UTF-8"), "iso-8859-1"));
		if (files.length == 1) {
			long remoteSize = files[0].getSize();
			long localSize = local.length();
			if (remoteSize == localSize) {
				return UploadStatus.File_Exits;
			} else if (remoteSize > localSize) {
				return UploadStatus.Remote_Bigger_Local;
			}

			// 尝试移动文件内读取指针,实现断点续传
			result = uploadFile(remoteFileName, local, ftpClient, remoteSize);

			// 如果断点续传没有成功，则删除服务器上文件，重新上传
			if (result == UploadStatus.Upload_From_Break_Failed) {
				if (!ftpClient.deleteFile(remoteFileName)) {
					return UploadStatus.Delete_Remote_Faild;
				}
				result = uploadFile(remoteFileName, local, ftpClient, 0);
			}
		} else {
			result = uploadFile(remoteFileName, local, ftpClient, 0);
		}
		return result;
	}

	/** */
	/**
	 * 断开与远程服务器的连接
	 * 
	 * @throws IOException
	 */
	public void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/** */
	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * @param ftpClient
	 *            FTPClient 对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public UploadStatus CreateDirecroty(String remote, FTPClient ftpClient)
			throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory
						.getBytes("UTF-8"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes("UTF-8"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
						return UploadStatus.Create_Directory_Fail;
					}
				}

				start = end + 1;
				end = directory.indexOf("/", start);

				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	/** */
	/**
	 * 上传文件到服务器,新上传和断点续传
	 * 
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile
	 *            本地文件 File句柄，绝对路径
	 * @param processStep
	 *            需要显示的处理进度步进值
	 * @param ftpClient
	 *            FTPClient 引用
	 * @return
	 * @throws IOException
	 */
	public UploadStatus uploadFile(String remoteFile, File localFile,
			FTPClient ftpClient, long remoteSize) throws IOException {
		UploadStatus status;
		// 显示进度的上传
		long step = localFile.length() / 100;
		long process = 0;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		OutputStream out = ftpClient.appendFileStream(new String(remoteFile
				.getBytes("UTF-8"), "iso-8859-1"));
		// 断点续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			process = remoteSize / step;
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[1024];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;
			if (localreadbytes / step != process) {
				process = localreadbytes / step;
//				System.out.println("上传进度:" + process);
				// TODO 汇报上传状态
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success
					: UploadStatus.Upload_From_Break_Failed;
		} else {
			status = result ? UploadStatus.Upload_New_File_Success
					: UploadStatus.Upload_New_File_Failed;
		}
		return status;
	}
	
	/**
	 * 获取在线用户访问url
	 * @return url
	 */
	public static String getOnlineUrl(){
		StringBuffer url = new StringBuffer();
		String httpdUrl = loader.getProperty("httpd.online.url");
		url.append(httpdUrl + "/");
		return url.toString();
	}
	
	public static void main(String[] args) throws IOException {
		//调用
		//File local = new File("http://192.168.0.215:9090/online/projectFile/5f36aadf2602491999938a784ad966e7/1059f05752c840bfb9925a0c4b9b7da7/20170925152511_Ge81P0.pdf");
		
		File local = new File("E://testfile/test.pdf");
		FTPPointUtil ftp = new FTPPointUtil("tadmin", "admin");
		ftp.connect();
		ftp.upload(local, "123/201708080844/321.pdf");
		ftp.disconnect();
		System.out.println(local.exists());
	}

}