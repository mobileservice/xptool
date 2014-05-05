package zju.ccnt.xptools.sync;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class manages the syncchronization of the client side(APP)
 * and the server side. There should be only one instance of the
 * whole application.
 */
public class SyncManager {
	private List<SyncComponent> syncComList;
	SyncManager instance=new SyncManager();
	Timer timer;
	TimerTask synccTask;

	private SyncManager() {
		syncComList=new LinkedList<SyncComponent>();
		synccTask=new TimerTask() {
			@Override
			public void run() {
				dosyncc();
			}
		};
		//TODO 把时间改到配置中
		timer.schedule(synccTask, 1000, 20000);
		
	}
	
	public SyncManager getInstance(){
		return instance;
	}
	
	/**
	 * Register a syncRunnable. The inputStream should have been initialized after
	 * this method.
	 * @param syncRunnable.
	 * @return true when success and false when fail.
	 */
	synchronized public boolean register(SyncRunnable syncRunnable){
		syncComList.add(new SyncComponent(syncRunnable));
		return true;
	}
	
	/**
	 * Unregister a syncRunnable.
	 * @param class name of the syncRunnable.
	 * @return true when success and false when fail.
	 */
	synchronized public boolean unRegister(String className){
		for(SyncComponent sc:syncComList){
			SyncRunnable sr=sc.syncRunnable;
			if(sr.getClass().getName().equals(className)){
				syncComList.remove(sc);
				sc.cleanResources();
				break;
			}
		}
		return true;
	}
	
	private void dosyncc(){
		for(SyncComponent sc:syncComList){
			SyncRunnable sr=sc.syncRunnable;
			sr.run();
		}
	}
	
	/**
	 * Component contains all the resources including
	 * syncRunnable instance, InputStream, OutputStream, etc.
	 * @author zhongjinwen
	 */
	private static class SyncComponent{
		SyncRunnable syncRunnable;
		InputStream inputStream;
		OutputStream outputStream;
		
		public SyncComponent(SyncRunnable syncRunnable) {
			this.syncRunnable=syncRunnable;
			//TODO 新建文件，并取得I/O stream
		}
		
		/**
		 * Do some work such as close the I/O stream and 
		 * delete files.
		 */
		public void cleanResources(){
			//TODO 关闭I/O stream，删除文件
		}
	}
	
	
}
