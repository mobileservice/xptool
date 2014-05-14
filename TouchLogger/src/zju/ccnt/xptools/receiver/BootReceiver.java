package zju.ccnt.xptools.receiver;

import zju.ccnt.xptools.MainActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context mContext, Intent mIntent) {
		// start activity
		String action = "android.intent.action.MAIN";
		String category = "android.intent.category.LAUNCHER";
		Intent myi = new Intent(mContext, MainActivity.class);
		myi.setAction(action);
		myi.addCategory(category);
		myi.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(myi);
		/*
		 * //start service Intent s=new Intent(ctx,MyService.class);
		 * ctx.startService(s);
		 */
	}
}
