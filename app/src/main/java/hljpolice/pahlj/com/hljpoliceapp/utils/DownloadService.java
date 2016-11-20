package hljpolice.pahlj.com.hljpoliceapp.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.util.Map;

import hljpolice.pahlj.com.hljpoliceapp.bean.ApkInfo;

public class DownloadService {
	String downURL = "";
	Context mContext;
	SysHelper sys;

	public DownloadService(Context c) {
		mContext = c;
		sys = new SysHelper(c);
	}

	// 检测软件版本
	public void chkVer() {
		//这里可以通过okHttp 请求url "http://61.180.150.46:8080/cs/version.json"
		//文字可以修改为 "正在检测当前是否有新版本..."

//		Map<String, String> rtnMap = json.jsonToGeneric(result,	Generic.ToMap);	//这个json 是从服务端下载的json数据
//		if (isUpdate(rtnMap)) {
			//z这里调用下载文件
//			HttpConnection http = new HttpConnection(mContext);
//			http.setDialogText("发现新版本客户端,正在下载,请耐心等待!");
//			http.setUrl(rtnMap.get("apkname"));
//			http.setDownPath(sys.getSdCardPath("tmp/download"));
//			http.setDownFile(rtnMap.get("apkname"));

			//	execute(map.get("downPath"), map.get("downFile"));	这里调用下载后的文件安装
//		}
	}

	// 执行下载的文件
	public void execute(String downPath, String downFile) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(downPath, downFile)),
				"application/vnd.android.package-archive");
		mContext.startActivity(intent);
		System.exit(0);
	}

	private boolean isUpdate(Map<String,String> rtnMap) {
		int newVerCode, verCode;
		ApkInfo apk = sys.getAppInfo();
		
		newVerCode = Integer.valueOf(rtnMap.get("verCode"));
		verCode = apk.getVersionCode();

		return newVerCode>verCode;
	}
}
