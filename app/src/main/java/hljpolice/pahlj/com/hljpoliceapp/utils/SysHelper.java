package hljpolice.pahlj.com.hljpoliceapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hljpolice.pahlj.com.hljpoliceapp.bean.ApkInfo;
import hljpolice.pahlj.com.hljpoliceapp.bean.DeviceInfo;

/**
 *系统辅助
 */
public class SysHelper {
	private Context mContext; 
	public SysHelper(Context c) {
		mContext = c;
	}
    //创建文件
    public File createFile(String dirPath,String dbname){
    	File f=null;
    	if(SdCardExist()){
    		File path = new File(Environment.getExternalStorageDirectory().getPath()+dirPath); //数据库文件目录  
    		f = new File(Environment.getExternalStorageDirectory().getPath()+dirPath, dbname); //数据库文件 
	    	if(!path.exists()){   //判断目录是否存在 		
				path.mkdirs();    //创建目录  		
			} 		
			if(!f.exists()){      //判断文件是否存在		
			  try{		
			      f.createNewFile();  //创建文件 		
			  }catch(IOException e){	
			      e.printStackTrace(); 		
			  }		
			}
	    }
    	return f;
    }
    //创建文件目录
    public void createFileDir(String dirPath){
    	File path = new File(dirPath);
    	if(!path.exists()){   //判断目录是否存在 		
			path.mkdirs();    //创建目录  		
		}
    }
    
    //读文件
    public String readFile(String dirPath,String filename){
    	createFile(dirPath,filename);
    	String s="";
    	StringBuffer sb = new StringBuffer();
    	File file=new File(Environment.getExternalStorageDirectory().getPath()+dirPath,filename);
    	BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			while ((s = br.readLine()) != null) {
				  sb.append(s);
			}
			br.close();			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    		
		return sb.toString();
	     
    }
    //写文件
    public void writeFile(String dirPath,String filename,String content){
    	createFile(dirPath,filename);    	
    	FileWriter fw;
		try {
			fw = new FileWriter(Environment.getExternalStorageDirectory().getPath()+dirPath+filename);
			String s = content;  
	    	fw.write(s,0,s.length());  
	    	fw.flush();  
	    	fw.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	     
    }
    //获取文件扩展名
    public String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos + 1);// 获得后缀名
	}
    //获取文件名
	public String getFileName(String file) {
		int pos = file.lastIndexOf("/");
		return file.substring(pos + 1);// 获得文件名
	}	
	
	//获取目录下符合条件文件
	public List<File> getIvfile(String path,String vFilter){		
		List<File> fileList=new ArrayList<File>();
		File[] fFiles = new File(Environment.getExternalStorageDirectory().getPath()+ path).listFiles();
		if(null==fFiles) return fileList;
		
		for (File file :fFiles) {
				String fileName=file.getName();
				if (vFilter.equals(getExtention(fileName))) {
					fileList.add(file);
				}
		}
		return fileList;
	}
	
    //删除文件
    public void delFile(String pathname){
    	File f = new File(Environment.getExternalStorageDirectory().getPath()+pathname); //
    	if(f.exists()){   //判断目录是否存在 		
			f.delete();	
		} 
    }
    //创建缩略图（图片）
    public Bitmap createImgThumbnail(String filePath){
	    Bitmap bm = null;
		    Options op = new Options();
		    op.inSampleSize = 4;
		    op.inJustDecodeBounds = false;
		    bm = BitmapFactory.decodeFile(filePath,op);
	    return bm;
    }

	public ApkInfo getAppInfo() {
		ApkInfo info = new ApkInfo();

		try{
			String pkName=mContext.getPackageName();
			PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(pkName, 0);						
			info.setVersionName(pInfo.versionName);															//版本名
			info.setVersionCode(pInfo.versionCode);															//版本号
			String packagePath ="/" + mContext.getApplicationContext().getFilesDir().getAbsolutePath();
			packagePath = packagePath.substring(1, packagePath.indexOf(pkName)) + pkName;
			info.setApkInstallPath(packagePath);															//获取程序安装路径
		} catch (Exception e) {
			// TODO: handle exception
		}
		return info;
	}

	private boolean SdCardExist() {  //判断SDCard 是否存在
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getSdCardPath(String dirName) {
		String path = "";
		if (SdCardExist()) {
			path = Environment.getExternalStorageDirectory().toString();
			if (dirName == null) {
				path += "/";
			} else {
				path += "/" + dirName + "/";
			}
			File dirFile = new File(path);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
		}
		return path;
	}
	
	public DeviceInfo getDeviceInfo() {
		DeviceInfo dInfo = new DeviceInfo();
		TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		dInfo.setIMEI(tm.getDeviceId());
		dInfo.setICCID(tm.getSimSerialNumber());
		dInfo.setPhoneNumber(tm.getLine1Number());
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				if ("ID".equals(field.getName())) dInfo.setDeviceId(field.get(null).toString());
				if ("SERIAL".equals(field.getName())) dInfo.setSerial(field.get(null).toString());
				if ("MODEL".equals(field.getName())) dInfo.setDeviceModel(field.get(null).toString());
				if ("DEVICE".equals(field.getName())) dInfo.setDeviceName(field.get(null).toString());
			} catch (Exception e) {
				
			}
		}
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumlpAddr = intf.getInetAddresses(); enumlpAddr.hasMoreElements();) {
					InetAddress inteAddress = enumlpAddr.nextElement();
					if (!inteAddress.isLoopbackAddress()) {
						dInfo.setDeviceIP(inteAddress.getHostAddress().toString());
					}
				}
			}
		} catch (Exception e) {
		}
		return dInfo;
	}

	/**
	 * @author Carklote
	 * @category 检测网络状态
	 * @return int 0=无网络状态
	 * 			   1=Mobile 
	 * 			   2=WIFI
	 * 			   3=EtherNet
	 *  		   4=BlueTools
	 */
	public int getNetStatue() {
		int statue = 0;
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (null == info) return 0;
		switch (info.getType()) {
		case ConnectivityManager.TYPE_MOBILE:
			statue =1;
			break;
		case ConnectivityManager.TYPE_WIFI:
			statue = 2;
			break;
		case ConnectivityManager.TYPE_ETHERNET:
			statue = 3;
			break;
		case ConnectivityManager.TYPE_BLUETOOTH:
			statue = 4;
		}
		return statue;
	}
	
	//判断文件是否存在
    public boolean isFileExist(String filePath,String fileName){
    	return isFileExist(filePath + fileName);
    }
    public boolean isFileExist(String fileName){
    	File file = new File(fileName);
    	return file.exists();
    }
    
    //复制文件到指定位置
    public  boolean copyFile(Context context,String fileName,String destination){
 //   	System.out.println("srcDBFile:" + fileName);
 //   	System.out.println("desDBFile:" + destination);
    	try{
    		InputStream myInput = context.getAssets().open(fileName);
    		File file=new File(destination);
    		file.createNewFile();
    		OutputStream myOutput = new FileOutputStream(file);
    		byte[] buffer = new byte[1024];
    		int length;
    		while ((length = myInput.read(buffer))>0){
    			myOutput.write(buffer,0,length);
    		}
    		myOutput.flush();
    		myOutput.close();
    		myInput.close();
    		return true;
    	} catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
	public String toDateFormat(String date,String sformat,String dformat) {
		if ("".equals(date)) {
			return date;
		}
		String strDate = date;
		SimpleDateFormat df = new SimpleDateFormat(sformat,Locale.CHINA);
		try {
			Date data =df.parse(date);
			DateFormat fm = new SimpleDateFormat(dformat,Locale.CHINA);
			strDate = fm.format(data);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strDate;
	}
    //获取日期格式字符串
	public String toDateFormat(String date,String format) {
		return toDateFormat(date, format, "yyyyMMdd");
	}
	
	//判断是否为日期
	public boolean isDate(String date) {
		int year, month, day;
		String[] D = date.split("-");
		if (D.length != 3) {
			return false;
		}
		for (int i = 0; i < D.length; i++) {
			if (isNum(D[i]) == false) {
				return false;
			}
		}
		year = Integer.parseInt(D[0]);
		month = Integer.parseInt(D[1]);
		day = Integer.parseInt(D[2]);

		if (year <= 1000 || year >= 3000) {
			return false;
		}
		if (month < 1 || month > 12) {
			return false;
		}
		if (month == 2 && isLeapYear(year)) {
			return day >= 1 && day <= 29;
		} else
			return day >= 1 && day <= DAYS_OF_MONTH[month - 1];

	}
	
	private final int[] DAYS_OF_MONTH = { 31, 28, 31, 30, 31, 30, 31,
		31, 30, 31, 30, 31 };

	// 判断当前字符串是否为数字
	public boolean isNum(String str) {
		if ("".equals(str)) {
			return false;
		}
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	private  boolean isLeapYear(int year) {
		return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
	}
	
	// 是否字符串是否是汉字
	public boolean isContainsChinese(String str) {
		if ("".equals(str)) {
			return false;
		}
		String regEx = "[\\u4E00-\\u9FA5]";
		Pattern pat = Pattern.compile(regEx);
		Matcher matcher = pat.matcher(str);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		if (count == str.length()) {
			return true;
		} else {
			return false;
		}
	}
	
	// 获取当前系统时间
	public String getDate(String format) {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(format,Locale.CHINA); //"yyyyMMddhhmmss");
		String date = sDateFormat.format(new Date());
//		System.out.println(date);
		return date;
	}

	public boolean isDecimal(String s,int x) {
		int position = s.indexOf(".");
		if (position == -1) {
			return true;
		}
		String substr = s.substring(position);
		if (substr.length() == x) {
			return true;
		} else {
			return false;
		}
	}
	public  String intToDecimal(String s) {
		int position = s.indexOf(".");
		if (position == -1) {
			return s + ".0";
		} else {
			return s;
		}
	}
	/**
	 * 获得屏幕高度
	 *
	 * @return
	 */
	public int getScreenHeight()
	{
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}
}
