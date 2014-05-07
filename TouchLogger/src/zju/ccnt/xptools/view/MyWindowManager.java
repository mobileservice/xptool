package zju.ccnt.xptools.view;

import com.android.internal.view.BaseInputHandler;
//import com.android.internal.widget.PointerLocationView;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.InputHandler;
import android.view.InputQueue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class MyWindowManager {
	private String TAG = "TouchLogger";
	final Object mLock = new Object();
	private Context mContext=null;
	private IWindowManager mWindowManager=null;
//	PointerLocationView mPointerLocationView = null;
	TraceView mPointerLocationView = null;
	private View addView=null;
	Handler mHandler;
	InputChannel mPointerLocationInputChannel = null;

	public MyWindowManager(Context context) {
		Log.d(TAG, "strucurer 1");

		mContext=context;
		mWindowManager=IWindowManager.Stub
	               .asInterface(ServiceManager.getService("window"));
		mPointerLocationView=new TraceView(mContext);
		addView=mPointerLocationView;
		mHandler=new Handler();
		Log.d(TAG, "strucurer 2");
		setParams();
		Log.d(TAG, "strucurer 3");
	}

	private final InputHandler mPointerLocationInputHandler = new BaseInputHandler() {

        @Override
        public void handleMotion(MotionEvent event, InputQueue.FinishedCallback finishedCallback) {
            boolean handled = false;
            Log.d(TAG, "event "+event.getX()+" "+event.getY());
            try {
                if ((event.getSource() & InputDevice.SOURCE_CLASS_POINTER) != 0) {
                    synchronized (mLock) {
                        if (mPointerLocationView != null) {
                            mPointerLocationView.addPointerEvent(event);
                            handled = true;
                        }
                    }
                }
            } finally {
                finishedCallback.finished(handled);
            }
        }
    };
	
	private void setParams()
	{
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
        WindowManager wm = (WindowManager)  
                mContext.getSystemService(Context.WINDOW_SERVICE);  
        wm.addView(addView, lp);  
		
		//added by zjw:ok
//		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
//				WindowManager.LayoutParams.MATCH_PARENT,
//				WindowManager.LayoutParams.WRAP_CONTENT,
//				WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
//				WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//						| WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//				PixelFormat.TRANSLUCENT);
//		WindowManager wm = (WindowManager) mContext
//				.getSystemService(Context.WINDOW_SERVICE);
//		wm.addView(addView, lp);

        if (mPointerLocationInputChannel == null) {  
            try {  
                mPointerLocationInputChannel =  
                    mWindowManager.monitorInput("TraceView");  
                
                InputQueue.registerInputChannel(mPointerLocationInputChannel,  
                        mPointerLocationInputHandler, mHandler.getLooper().getQueue());  
            } catch (RemoteException ex) {  
                Log.e(TAG, "Could not set up input monitoring channel for PointerLocation.",  
                        ex);  
            }  
        }  
    }  
  
    public void destroy()
    {
    	//TODO do nothing now
    }
	
}
