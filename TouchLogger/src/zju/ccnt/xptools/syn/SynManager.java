package zju.ccnt.xptools.syn;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * This class manages the synchronization of the client side(APP)
 * and the server side. There should be only one instance of the
 * whole application.
 */
public class SynManager {
	private List<SynRunnable> synComList;
	SynManager instance=new SynManager();

	private SynManager() {
		synComList=new LinkedList<SynRunnable>();
	}
	
	public SynManager getInstance(){
		return instance;
	}
	
	/**
	 * Register a SynRunnable.
	 * @param synComponent.
	 * @return true when success and false when fail.
	 */
	synchronized public boolean register(SynRunnable synComponent){
		synComList.add(synComponent);
		return true;
	}
	
	/**
	 * Unregister a SynRunnable.
	 * @param class name of the SynRunnable.
	 * @return true when success and false when fail.
	 */
	synchronized public boolean unRegister(String className){
		for(SynRunnable sr:synComList){
			if(sr.getClass().getName().equals(className)){
				synComList.remove(sr);
				break;
			}
		}
		return true;
	}
	
	
	
}
