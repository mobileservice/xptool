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
public class syncManager {
	private List<synccComponent> syncComList;
	syncManager instance=new syncManager();
	Timer timer;
	TimerTask synccTask;

	private syncManager() {
		syncComList=new LinkedList<synccComponent>();
		synccTask=new TimerTask() {
			@Override
			public void run() {
				dosyncc();
			}
		};
		//TODO 把时间改到配置中
		timer.schedule(synccTask, 1000, 20000);
		
	}
	
	public syncManager getInstance(){
		return instance;
	}
	
	/**
	 * Register a syncRunnable. The inputStream should have been initialized after
	 * this method.
	 * @param syncRunnable.
	 * @return true when success and false when fail.
	 */
	syncchronized public boolean register(syncRunnable syncRunnable){
		syncComList.add(new synccComponent(syncRunnable));
		return true;
	}
	
	/**
	 * Unregister a syncRunnable.
	 * @param class name of the syncRunnable.
	 * @return true when success and false when fail.
	 */
	syncchronized public boolean unRegister(String className){
		for(synccComponent sc:syncComList){
			syncRunnable sr=sc.syncRunnable;
			if(sr.getClass().getName().equals(className)){
				syncComList.remove(sc);
				sc.cleanResources();
				break;
			}
		}
		return true;
	}
	
	private void dosyncc(){
		for(synccComponent sc:syncComList){
			syncRunnable sr=sc.syncRunnable;
			sr.run();
		}
	}
	
	/**
	 * Component contains all the resources including
	 * syncRunnable instance, InputStream, OutputStream, etc.
	 * @author zhongjinwen
	 */
	private static class synccComponent{
		syncRunnable syncRunnable;
		InputStream inputStream;
		OutputStream outputStream;
		
		public synccComponent(syncRunnable syncRunnable) {
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
