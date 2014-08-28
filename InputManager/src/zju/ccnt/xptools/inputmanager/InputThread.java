package zju.ccnt.xptools.inputmanager;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.util.Log;


public class InputThread extends Thread{
	InputReader inputReader;
	InputThread(InputReader inputReader){
		this.inputReader=inputReader;
	}
	
	@Override
	public void run() {
//		Process process;
//		String fn="/dev/input/event0";
//		try {
//			String cmd="chmod 666 "+fn;
//			process=Runtime.getRuntime().exec(new String[]{"su","-c",cmd});
//			process.waitFor();
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
		
		while(true){
			
			int readSize=inputReader.readOnce();
			inputReader.processOnce();
		}
		
		//processOnce
	}
}
