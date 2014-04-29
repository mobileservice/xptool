package zju.ccnt.xptools;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import zju.ccnt.xptools.http.HttpUtil;
import zju.ccnt.xptools.mode.DeviceInfo;
import zju.ccnt.xptools.util.ConfData;
import zju.ccnt.xptools.util.DeviceInfoUtil;
import zju.ccnt.xptools.util.FileUtil;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;


public class MainActivity extends Activity {
	public final String TAG="TouchLogger";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		//MyWindowManager mwm=new MyWindowManager(this);
		//System.out.println(mwm.toString());
		try {
			File file = FileUtil.createFile(ConfData.DEVICE_INFO_PATH);
			InputStream inputStream = new FileInputStream(file);
			if (inputStream != null) {
				String content =null;
				InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;
                //∑÷––∂¡»°
                while (( line = buffreader.readLine()) != null) {
                    content += line ;
                } 
                inputStream.close();
                if (content == "") {
                	DeviceInfo deviceInfo = new DeviceInfo();
    				DeviceInfoUtil util = new DeviceInfoUtil(this);
    				deviceInfo.setId(util.getDeviceId());
    				deviceInfo.setxSize(util.getXSize());
    				deviceInfo.setySize(util.getYSize());
    				deviceInfo.setCPU_info(util.getCpuInfo());
    				deviceInfo.setStorage(util.getStorage());
    				deviceInfo.setModel(util.getModel());
    				deviceInfo.setSys_Version(util.getSysVersion());
    				deviceInfo.setOut_Storage(util.getOut_Storage());
    				FileUtil.writeDeviceInfo(ConfData.DEVICE_INFO_PATH, deviceInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		startService(new Intent(this, LoggerService.class));
		finish();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
