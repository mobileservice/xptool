/********************************************************************************
 * Copyright (c) 2014 CCNT
 * Version: 1.0
 * Author:zhongjinwen
 * Modified Time:2014-5-30 
 *******************************************************************************/
package zju.ccnt.xptools.inputmanager;

import java.io.FileNotFoundException;

public class InputReader {
	private EventHub eventHub=null;
	private int readSize;
	public RawEvent[] rawEventBuffer;
	public MultiTouchInputMapper multiTouchInputMapper;
//	private InputThread inputThread;
	
	
	public InputReader(){
		try {
			eventHub=new EventHub(null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		init();
	}
	
	public void init(){
		int ebs=InputManagerConstants.EVENT_BUFFER_SIZE;
		rawEventBuffer=new RawEvent[ebs];
		for(int i=0;i<ebs;i++){
			rawEventBuffer[i]=new RawEvent();
		}
		multiTouchInputMapper=new MultiTouchInputMapper();
		
	}
	
	/**
	 * Read once of the device
	 *
	 * @return the int
	 */
	public int readOnce(){
		int size;
		size=eventHub.getEvents(0, rawEventBuffer);
		readSize=size;
		return size;
	}
	
	/**
	 * Process the RawEvents we read
	 * 
	 * @return
	 */
	public boolean processOnce(){
		if(readSize==-1)
			return false;
		for(int i=0;i<readSize;i++){
			multiTouchInputMapper.process(rawEventBuffer[i]);
		}
		return true;
	}
	
//	public class InputThread extends Thread{
//		
//		@Override
//		public void run() {
//			
//		}
//	}
}
