package com.gqs.DowLoadFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HttpDownload {
	 private URL url = null;   
	 private FileUtils fileUtils;
	 public ProgressBar progressBar;
	 private int progress;
	 public int downloadSate;
	 public static final  int DOWNLOAD_BEFORE=0;
	 public static final int DOWNLOAD_LOADING=1;
	 public static final  int DOWNLOAD_END=-1;
	 private final String DIRNAME="Download";
	 public static String SDPath=Environment.getExternalStorageDirectory()+"/";
     public static final String RESULT_SUCCESS="下载完成";
     public static final String RESULT_EXIST="文件已存在！";
     public static final String RESULT_FAILED="下载失败";
     public TextView tvShowProgress;
     public int fileSize;
     public HttpDownload(Context context)
     {
    	 fileUtils=new FileUtils();
    	 progressBar=new ProgressBar(context);
     }
     //下载文件
     public String downloadFile(String urlStr,String fileName)
     {
    	 System.out.println("downFile");
    	 	  File file = null;   
    	     OutputStream output = null; 
    	     InputStream inputStream=getInputStream(urlStr);
    	     fileUtils.createSDDir(DIRNAME);
    	     try {
    	    	 System.out.println(FileUtils.SDPath +DIRNAME+"/"+ fileName);
    	    	 file=new File(FileUtils.SDPath +DIRNAME+"/"+ fileName);
    	    	 //创建要写入的文件
//    	 		fileUtils.createSDFile(FileUtils.SDPath +DIRNAME+"/"+ fileName);
    	 		System.out.println("createSDFile完成"+FileUtils.SDPath +DIRNAME+"/"+ fileName);
    	 		output=new FileOutputStream(file);
    	 		downloadSate=DOWNLOAD_BEFORE;
    	 		  byte[] buffer = new byte[1024];   
    	 		  int count=0;
    	         while((count=inputStream.read(buffer))!=-1)
    	         {
    	        	System.out.print("count...."+count);
    	         	output.write(buffer,0,count);
    	         	progress+=count;
    	         	downloadSate=DOWNLOAD_LOADING;
                   System.out.println("httpDownload"+progress);
    	         }
    	         
    	         output.flush();
    	         downloadSate=DOWNLOAD_END;
    	 	} catch (IOException e) {
    	 		downloadSate=DOWNLOAD_END;
    	 		System.out.println("下载出错了"+e.toString());
    	 		e.printStackTrace();
    	 		return RESULT_FAILED;
    	 	}   
    	 return RESULT_SUCCESS;
     }
     public int getProgress()
     {
    	 return progress;
     }
     //检测下载的文件是否已经存在
     public boolean isExist(String fileName)
     {
    	File file=new File(FileUtils.SDPath +DIRNAME+"/"+ fileName);
    	return file.exists();
     }
     //获取下载状态
     public int getDownloadSate()
     {
    	 return downloadSate;
     }
     //取得要下载文件的大小
     public int getFileSize(String urlStr)
     {
    	 try {
			url = new URL(urlStr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 HttpURLConnection httpUrlCon=null;
		try {
			httpUrlCon = (HttpURLConnection)url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return fileSize=httpUrlCon.getContentLength();
     }
     //获取输入流
     public InputStream getInputStream(String urlStr)
     {
    	 URL url;
    	 InputStream inputStream=null;
		try {
			url = new URL(urlStr);
			
	    	 HttpURLConnection httpUrlCon=(HttpURLConnection)url.openConnection();
            inputStream=httpUrlCon.getInputStream();
            FileUtils.fileSize=httpUrlCon.getContentLength();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return inputStream;
     }
     
}
