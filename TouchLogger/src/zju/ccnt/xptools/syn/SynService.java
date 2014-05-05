package zju.ccnt.xptools.syn;

import zju.ccnt.xptools.view.MyWindowManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service for synchronizing datas to the web service.
 * @author zhongjinwen
 *
 */
public class SynService extends Service {
	
	@Override
	public void onCreate()
	{
		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
