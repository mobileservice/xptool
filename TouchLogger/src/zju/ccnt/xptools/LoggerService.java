package zju.ccnt.xptools;

import zju.ccnt.xptools.view.MyWindowManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LoggerService extends Service{
	public static LoggerService instance;
	public final String TAG="TouchLogger";
	public MyWindowManager myWindowManager;
	@Override
	public void onCreate()
	{
		super.onCreate();
		instance=this;
		myWindowManager=new MyWindowManager(this);
	}
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId)
	{
		Log.d(TAG, "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onStart(Intent intent, int startId)
	{
		Log.d(TAG, "onStart");
		super.onStart(intent, startId);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if(myWindowManager!=null)
		{
			myWindowManager.destroy();
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	
	
}
