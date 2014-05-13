package zju.ccnt.xptools.http;

import zju.ccnt.xptools.mode.DeviceInfo;
import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.sync.DeviceInfoSyncRunnable;
import zju.ccnt.xptools.sync.SyncManager;
import zju.ccnt.xptools.sync.TouchDataSyncRunnable;
import zju.ccnt.xptools.util.ConfData;
import zju.ccnt.xptools.util.DeviceInfoUtil;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class TestConResponseHandler extends AsyncHttpResponseHandler {
	Context mContext;
	SyncManager syncManager;
	private SyncManager.SyncComponent syncComponent;
	
	public TestConResponseHandler(Context mContext) {
		super();
		this.mContext = mContext;
		syncManager = SyncManager.getInstance();
		
	}
	
	@Override
	public void onSuccess(String args){
		Log.d("TestConRESPONSE", "success: "+args);
		SharedPreferences check = mContext.getSharedPreferences(ConfData.CHECK_FILE,
				0);
		boolean firstUse = check.getBoolean(ConfData.FIRSTUSE, true);
		if (firstUse) {
			Log.d("FIRSTUSE", "yes");
			registerDeviceModel();
			writeDeviceInfo();
		} else {
			Log.d("FIRSTUSE", "no");
		}
		registerModels();
	}
	
	@Override 
	public void onFailure(Throwable error){
		Log.d("TestConRESPONSE", "failure: "+error.getMessage());
		
	}
	
	@Override
	public void onFinish(){
		super.onFinish();
		Log.d("TestConRESPONSE", "finish");
	}
	
	private void writeDeviceInfo() {
		DeviceInfo deviceInfo = new DeviceInfo();
		DeviceInfoUtil util = new DeviceInfoUtil(mContext);
		deviceInfo.setId(util.getDeviceId());
		deviceInfo.setxSize(util.getXSize());
		deviceInfo.setySize(util.getYSize());
		deviceInfo.setCPU_info(util.getCpuInfo());
		deviceInfo.setStorage(util.getStorage());
		deviceInfo.setModel(util.getModel());
		deviceInfo.setSys_Version(util.getSysVersion());
		deviceInfo.setOut_Storage(util.getOut_Storage());
		syncComponent.writeModel(deviceInfo);
	}

	private void registerDeviceModel() {
		Log.d("DEVICERUN", "register");
		DeviceInfoSyncRunnable disr = new DeviceInfoSyncRunnable(
				DeviceInfo.class, mContext);
		syncManager.register(disr);
		syncComponent=syncManager.find(DeviceInfo.class.getName());
	}

	/**
	 * Register models that need synchronization.
	 */
	private void registerModels() {
		TouchDataSyncRunnable tdsr = new TouchDataSyncRunnable(
				TouchDataModel.class);
		Log.d("REG", "call register");
		syncManager.register(tdsr);
	}
}
