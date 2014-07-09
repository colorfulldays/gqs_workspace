package com.gqs.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

import com.gqs.Const.Const;

public class FileUtils {
//检测数据库文件是否存在
 public static boolean isExist(String fileName)
 {
	 File file=new File(Const.path+fileName);
	 if(file.exists())
	 {
		 return true;
	 }
	 return false;
 }
 //检测是否存在存储卡
 public static boolean isExistSDCard()
 {
	 if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
	 {
		 getPath();
		 return true;
	 }
	 
	 return false;
 }
 //获取存储卡路径   用 Environment.getExternalStorageDirectory() 方法获取 SD 卡的路径 , 卡存储空间大小及已占用空间获取方法 
 public static String getPath()
 {
	 return Const.path=Environment.getExternalStorageDirectory()+"/";
	
 }
 //创建文件
 public boolean createFile(String fileName)
 {
	 File file=new File(Const.path+Const.FOLDER_NAME+"/"+fileName);
	 try {
		return file.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	 
 }
 //创建文件夹
 public static void createDir(String dirName)
 {
	
	 File file=new File(Const.path+dirName);
	 if(!file.exists())
	 {
		 System.out.println("创建文件夹"+dirName);
		 file.mkdir();
	 }
	
	 
 }
 
 //将文件从asset中写入SD卡中
 public static boolean fromAssetWriteToSDCard(InputStream inputStream,String fileName) throws IOException
 {
	 File file=new File(Const.path+Const.FOLDER_NAME+"/"+fileName);
	 try {
		OutputStream outputStream=new FileOutputStream(file);
		byte [] buffer=new byte[1024];
		int count=0;
		while((count=inputStream.read(buffer))!=-1)
		{
			
			outputStream.write(buffer,0,count);
		}
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
		System.out.println("数据库文件写入错误");
		return false;
	}
	 return true;
 }
}
