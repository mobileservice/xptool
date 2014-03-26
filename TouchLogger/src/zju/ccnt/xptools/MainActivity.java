package zju.ccnt.xptools;
import zju.ccnt.xptools.view.MyWindowManager;
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
