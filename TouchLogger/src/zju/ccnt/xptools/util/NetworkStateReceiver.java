package zju.ccnt.xptools.util;

import zju.ccnt.xptools.mode.DeviceInfo;
import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.sync.DeviceInfoSyncRunnable;
import zju.ccnt.xptools.sync.SyncManager;
import zju.ccnt.xptools.sync.TouchDataSyncRunnable;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

public class NetworkStateReceiver extends BroadcastReceiver {
	SyncManager syncManager;
	private SyncManager.SyncComponent syncComponent;
	Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 这个监听wifi的打开与关闭，与wifi的连接无关
			int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
			Log.e("H3c", "wifiState" + wifiState);
			switch (wifiState) {
			case WifiManager.WIFI_STATE_DISABLED:
				break;
			case WifiManager.WIFI_STATE_DISABLING:
				break;
			//
			}
		}
		// 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager.WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
		// 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，当然刚打开wifi肯定还没有连接到有效的无线
		if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
			Parcelable parcelableExtra = intent
					.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (null != parcelableExtra) {
				NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
				State state = networkInfo.getState();
				boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态
				Log.e("H3c", "isConnected" + isConnected);
				if (isConnected) {
					SharedPreferences check = context.getSharedPreferences(
							SharedData.CHECK_FILE, 0);
					boolean firstUse = check.getBoolean(SharedData.FIRSTUSE,
							true);
					syncManager = SyncManager.getInstance();
					syncComponent = SyncManager.getInstance().find(
							DeviceInfo.class.getName());
					if (firstUse) {
						writeDeviceInfo();
						registerDeviceModel();
					}
					registerModels();
				} else {

				}
			}
		}
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
