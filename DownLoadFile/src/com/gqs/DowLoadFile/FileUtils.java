package com.gqs.DowLoadFile;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileUtils {
public static int fileSize;
private int  progress;
public boolean isDownloading;
public static String SDPath=Environment.getExternalStorageDirectory()+"/";

public FileUtils()
{
	isDownloading=false;
	System.out.println("ExternalStorageDirectory---"+SDPath);
	String p=Environment.getRootDirectory()+"/";
	System.out.println("RootDirectory---"+p);
	
}
//在SD卡上创建文件
public File createSDFile(String fileName) throws IOException
{
	System.out.println("创建文件");
	File file=new File(fileName);
	System.out.println("创建文件...."+fileName);
	file.createNewFile();
	System.out.println("createNewFile()");
	return file;
}
//在SD卡上创建目录
public  void createSDDir(String dirName)
{
	
	File file=new File(SDPath+dirName);
	if(!file.exists())
	{
		file.mkdir();
	}

//	return file;
}
//判断SD卡上文件夹是否存在
public  boolean isExistFile(String fileName)
{
	File file=new File(SDPath+fileName);
	return file.exists();
}
//将InputSream中的数据写入SD卡中
public  File  writeToSDFromInput(String path,String fileName,InputStream inputStream)
{
	File file = null;   
    OutputStream output = null; 
    
    createSDDir(path);
    try {
		file = createSDFile(path + fileName);
		output=new FileOutputStream(file);
		isDownloading=true;
		  byte[] buffer = new byte[1024];   
		  int count=0;
        while((count=inputStream.read(buffer))!=-1)
        {
        	output.write(buffer,0,count);
        	progress+=count;
        }
        output.flush();
        isDownloading=false;
	} catch (IOException e) {
		 isDownloading=false;
		e.printStackTrace();
	}   
  return file;
}
}
