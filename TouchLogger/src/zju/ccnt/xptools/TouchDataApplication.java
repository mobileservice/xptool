package zju.ccnt.xptools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import zju.ccnt.xptools.mode.DeviceInfo;
import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.sync.DeviceInfoSyncRunnable;
import zju.ccnt.xptools.sync.SyncManager;
import zju.ccnt.xptools.sync.TouchDataSyncRunnable;
import zju.ccnt.xptools.util.ConfData;
import zju.ccnt.xptools.util.DeviceInfoUtil;
import zju.ccnt.xptools.util.FileUtil;
import zju.ccnt.xptools.util.SharedData;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * The Application class which saves some whole APP variables.
 * 
 * @author zhongjinwen
 * 
 */
public class TouchDataApplication extends Application {
	private boolean firstUse = false;
	Context mContext;
	SyncManager syncManager;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		SharedPreferences check = getSharedPreferences(SharedData.CHECK_FILE, MODE_PRIVATE);
		SharedPreferences.Editor editor = check.edit();
		firstUse = check.getBoolean(SharedData.FIRSTUSE, true);
		syncManager = SyncManager.getInstance();
		if (firstUse) {
			writeDeviceInfo();
			registerDeviceModel();
		}
		registerModels();
	}
	private void writeDeviceInfo(){
		try {
			File file = FileUtil.createFile(ConfData.DEVICE_INFO_PATH);
			InputStream inputStream = new FileInputStream(file);
			if (inputStream != null) {
				String content =null;
				InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                while (( line = buffreader.readLine()) != null) {
                    content += line ;
                } 
                inputStream.close();
                if (content == "") {
                	DeviceInfo deviceInfo = new DeviceInfo();
    				DeviceInfoUtil util = new DeviceInfoUtil(this);
    				deviceInfo.setId(util.getDeviceId());
    				deviceInfo.setxSize(util.getXSize());
    				deviceInfo.setySize(util.getYSize());
    				deviceInfo.setCPU_info(util.getCpuInfo());
    				deviceInfo.setStorage(util.getStorage());
    				deviceInfo.setModel(util.getModel());
    				deviceInfo.setSys_Version(util.getSysVersion());
    				deviceInfo.setOut_Storage(util.getOut_Storage());
    				FileUtil.writeDeviceInfo(ConfData.DEVICE_INFO_PATH, deviceInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private void registerDeviceModel() {
		DeviceInfoSyncRunnable disr = new DeviceInfoSyncRunnable(
				DeviceInfo.class, mContext);
		syncManager.register(disr);
	}

	/**
	 * Register models that need synchronization.
	 */
	private void registerModels() {
		TouchDataSyncRunnable tdsr = new TouchDataSyncRunnable(
				TouchDataModel.class);
		syncManager.register(tdsr);
	}
}
