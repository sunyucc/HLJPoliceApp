package hljpolice.pahlj.com.hljpoliceapp.download;


import android.content.Context;
import android.os.Binder;

import hljpolice.pahlj.com.hljpoliceapp.listener.ProgressListener;

public class DownloadBinder extends Binder {

	private Context mContext;
	private ProgressListener listener;
    private DownloadFile downloadFile;
	public DownloadBinder(Context c) {
		downloadFile = new DownloadFile();
		mContext = c;
	}
	
	public void setProgressListener(ProgressListener listener) {
		this.downloadFile.setProgressListener(listener);
	}

	public void setDownloadFile(String file) {
		downloadFile.setFileName(file);
	}

	public void startDownload() {
		downloadFile.download();
	}



//	public void setAction(String action) {
//		sendMap.put("action", action);
//	}
	
//	public void setModule(String module) {
//	sendMap.put("module", module);
//	}
	
//	public void setData(Object obj) {
//		sendMap.put("data", obj);
//	}
//	public void send() {
//		socket.send(json.toJson(sendMap));
//		sendMap = new HashMap<String, Object>();
//	}
	
	
//	public void initSocket() {
//		VariableData var = (VariableData) mContext.getApplicationContext();
//		socket = new SocketConnect(
//				var.getBasicData().getSckIpAddress(),
//				Integer.valueOf(var.getBasicData().getSckPort())
//		);
//	}
//
//	public void connectSck() {
//		socket.connect();
//	}
//
//	public boolean getSckStatue() {
//		return socket.getSocketStatue();
//	}
//
//	public void chkRate() {
//		socket.chkConnect();
//	}


}
