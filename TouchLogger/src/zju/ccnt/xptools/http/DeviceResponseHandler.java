package zju.ccnt.xptools.http;

import zju.ccnt.xptools.TouchDataApplication;
import zju.ccnt.xptools.mode.DeviceInfo;
import zju.ccnt.xptools.sync.SyncManager;
import zju.ccnt.xptools.util.ConfData;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class DeviceResponseHandler extends AsyncHttpResponseHandler {
	Context mContext;
	
	public DeviceResponseHandler(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public void onSuccess(String args){
		SyncManager syncManager = SyncManager.getInstance();
		Log.d("DEVICE_RESPONSE", "success: "+args);
		SharedPreferences check = mContext.getSharedPreferences(ConfData.CHECK_FILE, 0);
		SharedPreferences.Editor editor = check.edit();
		editor.putBoolean(ConfData.FIRSTUSE, false);
		editor.commit();
		syncManager.unRegister(DeviceInfo.class.getName());
	}
	
	@Override 
	public void onFailure(Throwable error){
		Log.d("DEVICE_RESPONSE", "failure: "+error.getMessage());
		
	}
	
	@Override
	public void onFinish(){
		super.onFinish();
		Log.d("DEVICE_RESPONSE", "finish");
	}
}
