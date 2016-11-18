package hlpolice.pahlj.com.hljpoliceapp.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GetHttpImage implements Runnable {
	Bitmap bmp = null;
	CallBackListener listener;
	private String url;
	private static int id = 0;

	private static final  int SUCCESS = 0;
	private static final int ERROR = 1;

	public GetHttpImage (String urlString) {
		this.url = urlString;
	}
	
	public void setListener(CallBackListener listener) {
		this.listener = listener;
	}
	public void setId(int id) { this.id = id; }
	public void getImage() {
		ConnectionManager.getInstance().push(this);
	}
	public interface CallBackListener {
		public void Callback(int x, Bitmap resultBmp);
		public void onError(String err);
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Bitmap img = null;
		InputStream is;
		try {
			is = new URL(url).openStream();
			img = BitmapFactory.decodeStream(is); // 解析得到图片
			is.close(); // 关闭数据流
			this.sendMessage(img, SUCCESS);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			sendMessage(null,ERROR);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			sendMessage(null,ERROR);
		}

		ConnectionManager.getInstance().didComplete(this);
	}

	private void sendMessage(Bitmap result,int did) {
		Message message = Message.obtain(handler, did , listener);
		Bundle data = new Bundle();
		data.putParcelable("callbackbmp", result);
		message.setData(data);
		handler.sendMessage(message);

	}
	
	private static final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			CallBackListener listener = (CallBackListener) message.obj;
			if (listener != null) {
				if (message.what == SUCCESS) {
					Object data = message.getData();
					if (data != null) {
						Bundle bundle = (Bundle) data;
						Bitmap result = bundle.getParcelable("callbackbmp");
						listener.Callback(id, result);
					}
				} else if (message.what == ERROR) {
					listener.onError("图像信息请求失败");
				}
			}
		}
	};
}
