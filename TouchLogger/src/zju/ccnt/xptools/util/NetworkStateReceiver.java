package zju.ccnt.xptools.util;

import zju.ccnt.xptools.http.HttpUtil;
import zju.ccnt.xptools.http.TestConResponseHandler;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

public class NetworkStateReceiver extends BroadcastReceiver {
	Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		// 这个监听wifi的打开与关闭，与wifi的连接无关
		if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
			int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
			Log.e("H3c", "wifiState" + wifiState);
			switch (wifiState) {
			case WifiManager.WIFI_STATE_DISABLED:
				break;
			case WifiManager.WIFI_STATE_DISABLING:
				break;
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
					testConnection();
				} else {

				}
			}
		}
	}

	private void testConnection() {
		HttpUtil.get(ConfData.URL_CONNECTION_TEST, new TestConResponseHandler(
				mContext));
	}
}
