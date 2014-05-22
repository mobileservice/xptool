package zju.ccnt.xptools;


import zju.ccnt.xptools.http.HttpUtil;
import zju.ccnt.xptools.view.TraceView;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;


public class MainActivity extends Activity {
	public final String TAG="TouchLogger";
	private TraceView mPointerLocationView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate");
		//MyWindowManager mwm=new MyWindowManager(this);
		//System.out.println(mwm.toString());
		
//		startService(new Intent(this, LoggerService.class));
		test();
//		finish();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void test(){
		mPointerLocationView=new TraceView(this);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(  
                WindowManager.LayoutParams.MATCH_PARENT,  
                WindowManager.LayoutParams.MATCH_PARENT);  
        lp.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;  
        lp.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN  
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE  
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE  
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;  
        lp.format = PixelFormat.TRANSLUCENT;  
        lp.setTitle("PointerLocation");  
        addContentView(mPointerLocationView, lp);
	}

}
