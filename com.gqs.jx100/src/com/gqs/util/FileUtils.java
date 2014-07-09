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
//������ݿ��ļ��Ƿ����
 public static boolean isExist(String fileName)
 {
	 File file=new File(Const.path+fileName);
	 if(file.exists())
	 {
		 return true;
	 }
	 return false;
 }
 //����Ƿ���ڴ洢��
 public static boolean isExistSDCard()
 {
	 if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
	 {
		 getPath();
		 return true;
	 }
	 
	 return false;
 }
 //��ȡ�洢��·��   �� Environment.getExternalStorageDirectory() ������ȡ SD ����·�� , ���洢�ռ��С����ռ�ÿռ��ȡ���� 
 public static String getPath()
 {
	 return Const.path=Environment.getExternalStorageDirectory()+"/";
	
 }
 //�����ļ�
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
 //�����ļ���
 public static void createDir(String dirName)
 {
	
	 File file=new File(Const.path+dirName);
	 if(!file.exists())
	 {
		 System.out.println("�����ļ���"+dirName);
		 file.mkdir();
	 }
	
	 
 }
 
 //���ļ���asset��д��SD����
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
		System.out.println("���ݿ��ļ�д�����");
		return false;
	}
	 return true;
 }
}
