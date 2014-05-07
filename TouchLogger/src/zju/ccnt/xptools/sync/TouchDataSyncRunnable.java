package zju.ccnt.xptools.sync;

import java.util.List;

import com.loopj.android.http.RequestParams;

import android.util.Log;
import zju.ccnt.xptools.http.HttpUtil;
import zju.ccnt.xptools.http.ResponseHandler;
import zju.ccnt.xptools.util.ConfData;
import zju.ccnt.xptools.util.JsonUtil;

public class TouchDataSyncRunnable extends SyncRunnable{

	public TouchDataSyncRunnable(Class model) {
		super(model);
	}

	@Override
	public void run() {
		List<Object> dataList=syncComponent.readModels();
		if(dataList.size()==0)
			return;
		Log.d("SIZE", "datalist size: "+dataList.size());
		RequestParams rp=new RequestParams();
		String s=JsonUtil.dataToJson(dataList);
		rp.add("touchDatalList", s);
		String url=ConfData.HTTP_URL_HEAD+
				"TouchDataService/saveJsonListTouchData";
		//Use HTTP POST in case that parameters too long to be put in the request URI
		HttpUtil.post(url, rp, new ResponseHandler());
	}
}
