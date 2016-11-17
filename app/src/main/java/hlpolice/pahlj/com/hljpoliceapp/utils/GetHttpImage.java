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
	
	public GetHttpImage (String urlString) {
		this.url = urlString;
	}
	
	public void setListener(CallBackListener listener) {
		this.listener = listener;
	}
	
	public void getImage() {
		ConnectionManager.getInstance().push(this);
	}
	public interface CallBackListener {
		public void Callback(Bitmap resultBmp);
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
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.sendMessage(img);
		ConnectionManager.getInstance().didComplete(this);
	}

	private void sendMessage(Bitmap result) {
		Message message = Message.obtain(handler, 2 , listener);
		Bundle data = new Bundle();
		data.putParcelable("callbackbmp", result);
		message.setData(data);
		handler.sendMessage(message);

	}
	
	private static final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			if (message.what == 2) {
				CallBackListener listener = (CallBackListener) message.obj;
				Object data = message.getData();
				if (listener != null) {
					if (data != null) {
						Bundle bundle = (Bundle) data;
						Bitmap result = bundle.getParcelable("callbackbmp");
						listener.Callback(result);
					}
				}
			}
		}
	};
}
