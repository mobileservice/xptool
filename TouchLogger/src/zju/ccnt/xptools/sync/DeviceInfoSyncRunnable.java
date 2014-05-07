package zju.ccnt.xptools.sync;

import java.util.List;

import zju.ccnt.xptools.http.DeviceResponseHandler;
import zju.ccnt.xptools.http.HttpUtil;
import zju.ccnt.xptools.http.ResponseHandler;
import zju.ccnt.xptools.util.ConfData;
import zju.ccnt.xptools.util.JsonUtil;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.RequestParams;

public class DeviceInfoSyncRunnable extends SyncRunnable {
	Context mContext;
	public DeviceInfoSyncRunnable(Class model, Context mContext) {
		super(model);
		this.mContext = mContext;
	}

	@Override
	public void run() {
		List<Object> dataList=syncComponent.readModels();
		if(dataList.size()==0)
			return;
		Log.d("SIZE", "datalist size: "+dataList.size());
		RequestParams rp=new RequestParams();
		String s=JsonUtil.dataToJson(dataList.get(0));
		rp.add("deviceInfo", s);
		String url=ConfData.HTTP_URL_HEAD+
				"DeviceInfoService/saveDeviceInfoJson";
		//Use HTTP POST in case that parameters too long to be put in the request URI
		HttpUtil.post(url, rp, new DeviceResponseHandler(mContext));
	}

}
