package zju.ccnt.xptools.inputmanager;

import zju.ccnt.xptools.TouchDataApplication;
import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	int w,h;
	float den;
	Button btn;
	TextView tv;
	int t;
	InputManager inputManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TouchDataApplication tda=(TouchDataApplication)getApplication();
		DisplayMetrics dm=new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		w=dm.widthPixels;
		h=dm.heightPixels;
		den=dm.density;
		Log.d("WHY", "w h den: "+w+" "+h+" "+den);
//		tda.screenWidth=h
//		tda.screenHeight=w;
		tda.screenWidth=w;
		tda.screenHeight=h;
		//TODO right?
		tda.screenHeight+=48;
		tda.screenDensity=den;
		
		inputManager=new InputManager();
		inputManager.start();
		
	
		
		
//		btn=(Button)findViewById(R.id.button1);
//		tv=(TextView)findViewById(R.id.textView1);
//		btn.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
////				int a=100/0;
////				if(t==0){
////					tv.setText("0");
////					t=1-t;
////					Log.v("INPUT", "ok");
////				}else{
////					tv.setText("1");
////					t=1-t;
////					Log.v("INPUT", "ok");
////				}
//					
//				Log.d("WHY", "w h den: "+w+" "+h+" "+den);
//				
//			}
//		});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
