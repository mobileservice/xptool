package zju.ccnt.xptools.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.util.ConfData;
import zju.ccnt.xptools.util.FileBasedObjectOutputStream;
import zju.ccnt.xptools.util.FileUtil;


/**
 * This class manages the syncchronization of the client side(APP)
 * and the server side. There should be only one instance of the
 * whole application.
 */
public class SyncManager {
	private List<SyncComponent> syncComList;
	private static SyncManager instance=new SyncManager();
	Timer timer;
	TimerTask syncTask;
	private static final String TAG="SyncManager";

	private SyncManager() {
		syncComList=new LinkedList<SyncComponent>();
		//TODO
		syncTask=new SyncTask();
		//TODO 把时间改到配置中
		timer=new Timer("syntimer");
		timer.schedule(syncTask, 1000, 5000);
	}
	
	public static SyncManager getInstance(){
		return instance;
	}
	
	/**
	 * Register a syncRunnable. The inputStream should have been initialized after
	 * this method.
	 * @param syncRunnable.
	 * @return true when success and false when fail.
	 */
	synchronized public boolean register(SyncRunnable syncRunnable){
		SyncComponent sc=new SyncComponent(syncRunnable);
		syncRunnable.setSyncComponent(sc);
		syncComList.add(sc);
		return true;
	}
	
	/**
	 * Find the SyncComponent by className;
	 * @param className
	 * @return
	 */
	synchronized public SyncComponent find(String className){
		Log.d(TAG, "find");
		for(SyncComponent sc:syncComList){
			SyncRunnable sr=sc.syncRunnable;
			if(sr.getModelClass().getName().equals(className)){
				Log.d(TAG, "found");
				return sc;
			}
		}
		Log.d(TAG, "not find");
		return null;
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
	
	private void dosync(){
		for(SyncComponent sc:syncComList){
			SyncRunnable sr=sc.syncRunnable;
			sr.run();
		}
	}
	
	/**
	 * Swap 
	 * @param syncComponent
	 */
	private void swap(){
		for(SyncComponent sc:syncComList){
			sc.swap();
		}
	}
	
	/**
	 * Component contains all the resources including
	 * syncRunnable instance, InputStream, OutputStream, etc.
	 * 
	 * @author zhongjinwen
	 */
	public static class SyncComponent{
		private SyncRunnable syncRunnable;
		public InputStream inputStream;
		private OutputStream outputStream;
		private ObjectOutputStream objectOutputStream;
		private String fileNames[];
		boolean canSwap=true;
		//indicate which file for input
		private int inputFileIdx=0;
		
		public SyncComponent(SyncRunnable syncRunnable) {
			this.syncRunnable=syncRunnable;
			//TODO 新建文件，并取得I/O stream
			fileNames=new String[2];
			fileNames[0]=syncRunnable.getClass().getName()+".txt";
			fileNames[1]=syncRunnable.getClass().getName()+".swap.txt";
			
			
			try {
		    	Log.d("FILE", fileNames[0]);
				FileUtil.createFile(fileNames[0]);
				FileUtil.createFile(fileNames[1]);
			} catch (IOException e) {
				Log.e("FILE", e.getMessage());
				e.printStackTrace();
			}
		    try {
				inputStream=new FileInputStream(ConfData.FILE_PATH+fileNames[inputFileIdx]);
				outputStream=new FileOutputStream(ConfData.FILE_PATH+fileNames[1-inputFileIdx]);
				objectOutputStream=new FileBasedObjectOutputStream(outputStream);
		    } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		
		public boolean writeModel(Object model){
			try {
				objectOutputStream.writeObject(model);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		
		public List<Object> readModels(){
			List<Object> list = new ArrayList<Object>();
				try {
					ObjectInputStream ois = new ObjectInputStream(inputStream);
					Object object = null;
					while (inputStream.available() > 0) {
						object = ois.readObject();
						list.add(object);
					}
					ois.close();
				} catch (StreamCorruptedException e) {
					e.printStackTrace();
				} catch (OptionalDataException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			return list;
		}
		
		/**
		 * Swap the files for Input and Output
		 * @return true for success
		 */
		synchronized public boolean swap(){
			Log.d("TIMER", "do swap");
			if(canSwap){
				try {
					//close streams
					inputStream.close();
					objectOutputStream.close();
					outputStream.close();
					
					FileUtil.clearFile(fileNames[inputFileIdx]);
					inputFileIdx=1-inputFileIdx;
					inputStream=new FileInputStream(ConfData.FILE_PATH+fileNames[inputFileIdx]);
					outputStream=new FileOutputStream(ConfData.FILE_PATH+fileNames[1-inputFileIdx]);
					objectOutputStream=new FileBasedObjectOutputStream(outputStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			}
			return false;
		}
		
		/**
		 * Do some work such as close the I/O stream and 
		 * delete files.
		 */
		public void cleanResources(){
			//TODO 关闭I/O stream，删除文件
		}
	}
	
	/**
	 * The TimerTask which upload the data to server.
	 * On timeout, the task should first exchange the
	 * swapping files which is I/O stream is from, and
	 * then run the SyncRunnable.
	 * @author zhongjinwen
	 *
	 */
	private class SyncTask extends TimerTask{
		/**
		 * Two steps:
		 * 1. Swap files.
		 * 2. Run upload.
		 */
		
		@Override
		public void run() {
			swap();
			dosync();
			Log.d("TIMER", "schedule once");
		}
	}
	
	
}
