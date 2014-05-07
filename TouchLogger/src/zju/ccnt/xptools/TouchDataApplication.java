package zju.ccnt.xptools;


import zju.ccnt.xptools.mode.DeviceInfo;
import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.sync.DeviceInfoSyncRunnable;
import zju.ccnt.xptools.sync.SyncManager;
import zju.ccnt.xptools.sync.TouchDataSyncRunnable;
import zju.ccnt.xptools.util.ConfData;
import zju.ccnt.xptools.util.DeviceInfoUtil;
import zju.ccnt.xptools.util.FileUtil;
import zju.ccnt.xptools.util.NetWorkStateUtil;
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
	private SyncManager.SyncComponent syncComponent;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		SharedPreferences check = getSharedPreferences(SharedData.CHECK_FILE,
				MODE_PRIVATE);
		firstUse = check.getBoolean(SharedData.FIRSTUSE, true);
		FileUtil.createDir(ConfData.FILE_PATH);
		syncManager = SyncManager.getInstance();
		syncComponent=SyncManager.getInstance().find(DeviceInfo.class.getName());
		if (NetWorkStateUtil.checkNetWorkState(mContext) == NetWorkStateUtil.WIFI) {
			if (firstUse) {
				writeDeviceInfo();
				registerDeviceModel();
			}
			registerModels();
		}
	}

	private void writeDeviceInfo() {
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
		syncComponent.writeModel(deviceInfo);
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
