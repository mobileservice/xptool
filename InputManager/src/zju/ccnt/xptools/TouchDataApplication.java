package zju.ccnt.xptools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import zju.ccnt.xptools.util.BaseFileUtil;
import zju.ccnt.xptools.util.ConfData;
import android.app.Application;

/**
 * The Application class which saves some
 * whole APP variables.
 * @author zhongjinwen
 * 
 */
public class TouchDataApplication extends Application {
	//Calibration parameters. should be initialzed in an activity
	public float screenWidth;	//in pixel
	public float screenHeight;	//in pixel
	public float screenDensity;	//in pixel
	public Properties driverProperties;
	private static TouchDataApplication mInstance;
	
	private void init(){
		//TODO read the device driver to get the driver properties,
		//save the properties to the file.
		//readDriver();
		
//		driverProperties=new Properties();
//		try {
//			InputStream in=new BufferedInputStream(new FileInputStream(ConfData.DRIVER_PROPERTIES_FILE_PATH));
//			driverProperties.load(in);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		mInstance=this;
	}
	
	public static TouchDataApplication getInstance(){
		return mInstance;
	}
}