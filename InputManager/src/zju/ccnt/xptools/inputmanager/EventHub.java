/********************************************************************************
 * Copyright (c) 2014 CCNT
 * Version: 1.0
 * Author:zhongjinwen
 * Modified Time:2014-5-30 
 *******************************************************************************/
package zju.ccnt.xptools.inputmanager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.util.Log;



/**
 * EventHub reads the device file to get the raw events.
 */
public class EventHub {
	private String deviceName="/dev/input/event0";	//not necessary this name
	private FileInputStream fis=null;
	private byte[] rawBuffer;
	
	public EventHub(String deviceName) throws FileNotFoundException {
		
		if(deviceName!=null)
			this.deviceName=deviceName;
		//change the access right
		Process process;
		try {
			String cmd="chmod 666 "+this.deviceName;
			process=Runtime.getRuntime().exec(new String[]{"su","-c",cmd});
			process.waitFor();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		fis=new FileInputStream(this.deviceName);
		rawBuffer=new byte[InputManagerConstants.EVENT_BUFFER_SIZE*
		                   InputManagerConstants.RAW_EVENT_FRAME_SIZE];
	}
	
	/**
	 * Get raw events to the buffer
	 * 
	 * @param timeoutMillis timeout. Ignore it currently.
	 * @param buffer buffer which store the RawEvent objects.It should be
	 * 		  initialized before pass to this method.
	 * @return
	 */
	public int getEvents(int timeoutMillis,RawEvent[] buffer)
	{
		int len=0;
		int realSize=0;
		ByteBuffer byteBuffer;
		
		try {
			len=fis.read(rawBuffer);
		} catch (IOException e) {
			return -1;
		}
		byteBuffer=ByteBuffer.wrap(rawBuffer);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		realSize=len/InputManagerConstants.RAW_EVENT_FRAME_SIZE;
		for(int i=0;i<realSize;i++){
			int sec=byteBuffer.getInt();
			int usec=byteBuffer.getInt();
			buffer[i].when=sec*1000000000L+usec*1000L;
			buffer[i].type=byteBuffer.getShort();
			buffer[i].code=byteBuffer.getShort();
			buffer[i].value=byteBuffer.getInt();
		}
		return realSize;
	}
}
