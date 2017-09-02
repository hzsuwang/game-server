package com.iterror.game.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import com.iterror.game.common.constant.Constants.FileConstants;

import org.apache.commons.lang.StringUtils;


public class FileUtil {
    public static String[] CONTACT_ALLOW_TYPES = { "gif", "jpg", "jpeg", "bmp", "png", "x-png", "x-bmp", "x-ms-bmp" };

    public static String[] SOUND_FILE_TYPES    = { "amr", "mp3", "wma", "wmv", "midi", "mp4", "mod", "cda", "fla", "flac", "mid" };

    /**
     * 获取当前文件保存路径
     * 
     * @return
     */
    public static String getBaseDirPath(String fileType, String fileRoot) {
        GregorianCalendar now;
        String year, day, month, hour;
        now = new GregorianCalendar();
        year = String.valueOf(now.get(Calendar.YEAR));
        month = (now.get(Calendar.MONTH) + 1) < 10 ? "0" + String.valueOf(now.get(Calendar.MONTH) + 1) : String.valueOf(now.get(Calendar.MONTH) + 1);
        day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        StringBuilder sb = new StringBuilder();
        sb.append(fileRoot).append(fileType).append(FileConstants.FileServerSeperator);
        sb.append(year).append(FileConstants.FileServerSeperator);
        sb.append(month).append(FileConstants.FileServerSeperator);
        sb.append(day).append(FileConstants.FileServerSeperator);
        sb.append(hour).append(FileConstants.FileServerSeperator);
        return sb.toString();
    }

    /**
     * 文件的本地全目录
     * 
     * @param propertyBean
     * @param fileType
     * @return
     */
    public static String getLocalDirPath(PropertyBean propertyBean, String fileType) {
        String path = getBaseDirPath(fileType, FileConstants.FileServerRoot);
        return propertyBean.getImgLocal() + path;
    }

    /**
     * 文件的url地扯
     * 
     * @param propertyBean
     * @param fileType
     * @return
     */
    public static String getFileDirPath(PropertyBean propertyBean, String fileType) {
        String path = getBaseDirPath(fileType, FileConstants.FileServerRoot);
        return propertyBean.getImgServerDomain() + path;
    }

    /**
     * 文件名
     * 
     * @return
     */
    public static String getUniqueId() {
        String uuidram = "" + UUID.randomUUID().getLeastSignificantBits();
        return uuidram.replace("-", "");
    }

    /**
     * 文件重命名
     * 
     * @param extname
     * @return
     */
    public static String createFileNewName(String extname) {
        return FileUtil.getUniqueId() + "." + extname;
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    public static String getFileExtName(String fileName) {
        String ext = "";
        if (StringUtils.isBlank(fileName)) {
            return ext;
        }
        int i = fileName.lastIndexOf(".");
        ext = fileName.substring(i + 1); // --扩展名
        return ext;
    }

    /**
     * 验证图片格式
     * 
     * @param fileName
     * @return
     */
    public static boolean checkImgFileName(String fileName) {
        boolean checkResult = false;
        String fileType = getFileExtName(fileName);
        for (String allowType : CONTACT_ALLOW_TYPES) {
            if (allowType.equalsIgnoreCase(fileType)) {
                checkResult = true;
                break;
            }
        }
        return checkResult;
    }

    /**
     * 验证声音文件
     * 
     * @param fileName
     * @return
     */
    public static boolean checkSoundFileName(String fileName) {
        boolean checkResult = false;
        String fileType = getFileExtName(fileName);
        for (String allowType : SOUND_FILE_TYPES) {
            if (allowType.equals(fileType)) {
                checkResult = true;
                break;
            }
        }
        return checkResult;
    }

    /**
     * 得到amr的时长
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public static long getAmrDuration(File file) throws IOException {
        long duration = -1;
        int[] packedSize = { 12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0 };
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            long length = file.length();// 文件的长度
            int pos = 6;// 设置初始位置
            int frameCount = 0;// 初始帧数
            int packedPos = -1;
            // ///////////////////////////////////////////////////
            byte[] datas = new byte[1];// 初始数据值
            while (pos <= length) {
                randomAccessFile.seek(pos);
                if (randomAccessFile.read(datas, 0, 1) != 1) {
                    duration = length > 0 ? ((length - 6) / 650) : 0;
                    break;
                }
                packedPos = (datas[0] >> 3) & 0x0F;
                pos += packedSize[packedPos] + 1;
                frameCount++;
            }
            // ///////////////////////////////////////////////////
            duration += frameCount * 20;// 帧数*20
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        return duration;
    }

    /**
     * 创建文件目录
     * 
     * @param localDirName
     */
    public static void createFileDir(String localDirName) {
        File createFile6 = new File(localDirName);
        if (!createFile6.exists()) {
            createFile6.mkdirs();
        }
    }

    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }
	/**
	 * 写文件 
	 * @param f
	 * @param content
	 */
	public static void writeFile(File f,String content){
		  writeFile(f, content,"utf-8");
	}
	/**
	 * 写文件 
	 * @param f
	 * @param content
	 */
	public static void writeFile(File f,String content,String encode){
		  try {
		   if (!f.exists()) {
		    f.createNewFile();
		   }
		   OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(f),encode);
		   BufferedWriter utput = new BufferedWriter(osw);
		   utput.write(content);
		   utput.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	/**
	 * 写文件
	 * @param path
	 * @param content
	 */
	public static void writeFile(String path, String content,String encode) {
	       File f = new File(path);
	       writeFile(f, content,encode);
	}
	/**
	 * 写文件
	 * @param path
	 * @param content
	 */
	public static void writeFile(String path, String content) {
	       File f = new File(path);
	       writeFile(f, content,"utf-8");
	}

	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String readFile(File file){
		return readFile(file,"UTF-8");
	}
	/**
	 * 读取文件
	 * @param file
	 * @return
	 */
	public static String readFile(File file,String encode){
		String output = "";

		if (file.exists()) {
			if (file.isFile()) {
				try {
					InputStreamReader isr=new InputStreamReader(new FileInputStream(file),encode);
					BufferedReader input = new BufferedReader(isr);
					StringBuffer buffer = new StringBuffer();
					String text;
					while ((text = input.readLine()) != null)
						buffer.append(text + "\n");
					output = buffer.toString();
					input.close();
				} catch (IOException ioException) {
					System.err.println("File Error！");
				}
			} else if (file.isDirectory()) {
				String[] dir = file.list();
				output += "Directory contents：\n";
				for (int i = 0; i < dir.length; i++) {
					output += dir[i] + "\n";
				}
			}

		} else {
			System.err.println("Does not exist！");
		}

		return output;
	}
	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName,String encode) {
		File file = new File(fileName);
		return readFile(file,encode);
	}
	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 */
	public static String readFile(String fileName) {
		return readFile(fileName,"utf-8");
	}


	/**
	 * 获取目录下所有文件
	 * @param folder
	 * @return
	 */
	public static List<File> getFiles(String folder){
		File file=new File(folder);
		List<File> files=new ArrayList<File>();
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (!sonFiles[i].isDirectory()) {
						files.add(sonFiles[i]);
					}
				}
			}
		}
		return files;
	}
	/**
	 * 获取目录下所有文件夹
	 * @param folder
	 * @return
	 */
	public static List<File> getFolders(String folder){
		File file=new File(folder);
		List<File> files=new ArrayList<File>();
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (sonFiles[i].isDirectory()) {
						files.add(sonFiles[i]);
					}
				}
			}
		}
		return files;
	}
	/**
	 * 判断是否有子目录
	 * @param folder
	 * @return
	 */
	public static boolean hasSonFolder(String folder){
		File file=new File(folder);
		return hasSonFolder(file);
	}
	/**
	 * 判断是否有子目录
	 * @param file
	 * @return
	 */
	public static boolean hasSonFolder(File file){
		if (file.exists()) {
			File[] sonFiles=file.listFiles();
			if (sonFiles!=null && sonFiles.length>0) {
				for (int i = 0; i < sonFiles.length; i++) {
					if (sonFiles[i].isDirectory()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 创建目录
	 * @param folder
	 */
	public static void mkdir(String folder){
		File file=new File(folder);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	/**
	 * 复制文件
	 * @param src
	 * @param dst
	 */
	public static void copy(File src, File dst) {
		try {
			int BUFFER_SIZE = 32 * 1024;
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(src);
				out = new FileOutputStream(dst);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count;
				while ((count = in.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 复制文件夹
     * @param sourceDir
     * @param targetDir
     * @throws IOException
     */
    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
    	if (new File(sourceDir).exists()) {
            // 新建目标目录
        	File targetFolder=new File(targetDir);
        	if (!targetFolder.exists()) {
    			targetFolder.mkdirs();
    		}
            // 获取源文件夹当前下的文件或目录
            File[] file = (new File(sourceDir)).listFiles();
            for (int i = 0; i < file.length; i++) {
                if (file[i].isFile()) {
                    // 源文件
                    File sourceFile = file[i];
                    // 目标文件
                    File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                    copy(sourceFile, targetFile);
                }
                if (file[i].isDirectory()) {
                    // 准备复制的源文件夹
                    String dir1 = sourceDir + "/" + file[i].getName();
                    // 准备复制的目标文件夹
                    String dir2 = targetDir + "/" + file[i].getName();
                    copyDirectiory(dir1, dir2);
                }
            }
		}
    }

	/**
	 * 获取扩展名
	 */
	public static String getExt(File src){
		if (src!=null) {
			String name=src.getName();
			return name.substring(name.lastIndexOf("."), name.length());
		}
		return "";
	}
	/**
	 * 获取扩展名
	 */
	public static String getExt(String src){
		if (src!=null) {
			return src.substring(src.lastIndexOf("."), src.length());
		}
		return "";
	}
	/**
	 * 删除指定文件
	 * @param path
	 */
	public static void del(String path){
		File file=new File(path);
		deleteFile(file);
	}
	/**
	 * 递归删除文件夹下所有文件
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { //判断文件是否存在
			if (file.isFile()) { //判断是否是文件
				file.delete(); //delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { //否则如果它是一个目录
				File files[] = file.listFiles(); //声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { //遍历目录下所有的文件
					deleteFile(files[i]); //把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		}
	} 
	public static void upzip() throws Exception{
		File file = new File("D:\\test.zip");//压缩文件  
		ZipFile zipFile = new ZipFile(file);//实例化ZipFile，每一个zip压缩文件都可以表示为一个ZipFile  
		//实例化一个Zip压缩文件的ZipInputStream对象，可以利用该类的getNextEntry()方法依次拿到每一个ZipEntry对象  
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));  
		ZipEntry zipEntry = null;  
		while ((zipEntry = zipInputStream.getNextEntry()) != null) {  
			String fileName = zipEntry.getName();  
			File temp = new File("D:\\un\\" + fileName);  
			temp.getParentFile().mkdirs();  
			OutputStream os = new FileOutputStream(temp);  
			//通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流  
			InputStream is = zipFile.getInputStream(zipEntry);  
			int len = 0;  
			while ((len = is.read()) != -1)  
				os.write(len);  
			os.close();  
			is.close();  
		}  
		zipInputStream.close();  
		zipFile.close();
	}
	
	 /**
     * 获取当前文件保存路径
     * 
     * @return
     */
    public static String getBaseDirPath(String pathType) {
        String year, day, month;
        GregorianCalendar now = new GregorianCalendar();
        year = String.valueOf(now.get(Calendar.YEAR));
        month = (now.get(Calendar.MONTH) + 1) < 10 ? "0" + String.valueOf(now.get(Calendar.MONTH) + 1) : String.valueOf(now.get(Calendar.MONTH) + 1);
        day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        StringBuilder sb = new StringBuilder();
        sb.append(pathType).append(File.separator);
        sb.append(year).append(File.separator);
        sb.append(month).append(File.separator);
        sb.append(day).append(File.separator);
        return sb.toString();
    }
	
	public static void main(String[] args) {
		try {
			String path = getBaseDirPath("vod");
			System.out.println(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
