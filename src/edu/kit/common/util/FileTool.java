package edu.kit.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FileTool
{
	/**
	 * 复制文件从源文件到目标文件
	 * @param sourceFile 源文件
	 * @param targetFile 目标文件
	 * @throws IOException
	 */
	 public static void copyFile(File sourceFile, File targetFile) throws IOException
	 {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try 
        {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) 
                outBuff.write(b, 0, len);
            outBuff.flush();
        } 
        finally 
        {
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	 
	 public static void copyFile(String sourceFile, String targetFile)
	 {
		 try {
			copyFile(new File(sourceFile), new File(targetFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	/**
	 * 如果不存在文件夹，新建文件夹
	 * @param path
	 */
	public static void createDirectoryIfNotExsit(String path)
	{
		File dir=new File(path);
		if(!dir.exists())
			dir.mkdir();
	}
	
	/**
	 * 判断文件夹是否为空
	 * @param path 文件夹路径
	 * @return 文件夹是否为空
	 */
	public static boolean isEmptyDirectory(String path)
	{
		File file = new File(path);
        File[] files = file.listFiles();
        if(files.length==0)
        	return true;
        return false;
	}
	
	/**
	 * 得到文件格式
	 * @param fileName 文件名
	 * @return 文件格式
	 */
	public static String getFormat(String fileName)
	{
		int index = fileName.lastIndexOf(".");
	    String format = fileName.substring(index+1);
		return format;
	}
	
	public static String getName(String fileName)
	{
		int index = fileName.lastIndexOf(".");
	    String format = fileName.substring(0,index);
		return format;
	}
	
	/**
	 * 得到指定路径下指定格式的文件列表
	 * @param path 指定路径
	 * @param format 指定格式
	 * @return 指定格式文件列表
	 */
	public static ArrayList<File> getFiles(String path,String format)
	{
		File dir=new File(path);
		File[] files=dir.listFiles();
		ArrayList<File> fileList=new ArrayList<File>();
		for(int i=0;i<files.length;i++)
			if(format.equals(getFormat(files[i].getName())))
				fileList.add(files[i]);
		return fileList;
	}
	
	/**
	 * 修改文件名
	 * @param path 文件路径
	 * @param fileName 文件名
	 * @param newFileName 新文件名
	 * @return 更改是否成功
	 */
	public static boolean modifyFileName(String path,String fileName,String newFileName)
	{
		File oldFile = new File(path+"/"+fileName);
		String rootPath = oldFile.getParent();
		File newFile = new File(rootPath + File.separator + newFileName);
		if (oldFile.renameTo(newFile)) 
			return true;
		else 
			return false;
	}
	
	/**
	 * 批量更改文件名
	 * @param path
	 * @param format
	 * @param fileName
	 * @param start
	 * @return
	 */
	public static int modifyFiles(String path,String format,String fileName,int start)
	{
		ArrayList<File> files=getFiles(path,format);
		for(int i=0;i<files.size();i++)
			modifyFileName(path,files.get(i).getName(),fileName+(start+i)+"."+format);
		return files.size();
	}
	
	/**
	 * 删除所有文件
	 * @param path
	 * @return
	 */
	public static boolean delAllFile(String path)
	{
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }
	
	public static void delFolder(String folderPath) 
	{
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}

}
