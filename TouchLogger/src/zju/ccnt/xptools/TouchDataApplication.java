package zju.ccnt.xptools;

import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.sync.SyncManager;
import zju.ccnt.xptools.sync.TouchDataSyncRunnable;
import android.app.Application;

/**
 * The Application class which saves some
 * whole APP variables.
 * @author zhongjinwen
 * 
 */
public class TouchDataApplication extends Application {
	SyncManager syncManager;
	
	@Override
	public void onCreate(){
		super.onCreate();
		syncManager=SyncManager.getInstance();
		registerModels();
	}
	
	/**
	 * Register models that need synchronization.
	 */
	private void registerModels(){
		TouchDataSyncRunnable tdsr=new TouchDataSyncRunnable(TouchDataModel.class);
		syncManager.register(tdsr);
	}
}
