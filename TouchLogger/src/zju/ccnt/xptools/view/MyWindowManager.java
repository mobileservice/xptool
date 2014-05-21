package zju.ccnt.xptools.view;

//import com.android.internal.widget.PointerLocationView;





import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.input.IInputManager;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputQueue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManagerPolicy.WindowManagerFuncs;

public class MyWindowManager {
	private String TAG = "TouchLogger";
	final Object mLock = new Object();
	private Context mContext=null;
	private IWindowManager mWindowManager=null;
	private WindowManagerFuncs mWindowManagerFuncs;
//	PointerLocationView mPointerLocationView = null;
	TraceView mPointerLocationView = null;
	private View addView=null;
	Handler mHandler;
	InputChannel mPointerLocationInputChannel = null;
	PointerLocationInputEventReceiver mPointerLocationInputEventReceiver;

	public MyWindowManager(Context context) {
		Log.d(TAG, "strucurer 1");

		mContext=context;
		mWindowManager=IWindowManager.Stub
	               .asInterface(ServiceManager.getService("window"));
		//TODO ≥ı ºªØmWindowManagerFuncs
		initWindowManagerFuncs();
		IInputManager ii;
		
		mPointerLocationView=new TraceView(mContext);
		addView=mPointerLocationView;
		mHandler=new Handler();
		Log.d(TAG, "strucurer 2");
		setParams();
		Log.d(TAG, "strucurer 3");
	}
	
	private boolean initWindowManagerFuncs(){
		
		return true;
	}

//	private final InputHandler mPointerLocationInputHandler = new BaseInputHandler() {
//
//        @Override
//        public void handleMotion(MotionEvent event, InputQueue.FinishedCallback finishedCallback) {
//            boolean handled = false;
//            Log.d(TAG, "event "+event.getX()+" "+event.getY());
//            try {
//                if ((event.getSource() & InputDevice.SOURCE_CLASS_POINTER) != 0) {
//                    synchronized (mLock) {
//                        if (mPointerLocationView != null) {
//                            mPointerLocationView.addPointerEvent(event);
//                            handled = true;
//                        }
//                    }
//                }
//            } finally {
//                finishedCallback.finished(handled);
//            }
//        }
//    };
	
	/**
	 * Replace the InputHandler before version 4.0
	 * @author zhongjinwen
	 *
	 */
	private static final class PointerLocationInputEventReceiver extends
			InputEventReceiver {
		private final TraceView mView;

		public PointerLocationInputEventReceiver(InputChannel inputChannel,
				Looper looper, TraceView view) {
			super(inputChannel, looper);
			mView = view;
		}

		@Override
		public void onInputEvent(InputEvent event) {
			boolean handled = false;
			try {
				if (event instanceof MotionEvent
						&& (event.getSource() & InputDevice.SOURCE_CLASS_POINTER) != 0) {
					final MotionEvent motionEvent = (MotionEvent) event;
					mView.addPointerEvent(motionEvent);
					handled = true;
				}
			} finally {
				finishInputEvent(event, handled);
			}
		}
	}
	
	private void setParams() {
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
		WindowManager wm = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		wm.addView(addView, lp);
		if (mPointerLocationInputChannel == null) {
//			mPointerLocationInputChannel =
//                    mWindowManager.monitorInput("PointerLocationView");
			mPointerLocationInputChannel = mWindowManagerFuncs
					.monitorInput("TraceView");
			// InputQueue.registerInputChannel(mPointerLocationInputChannel,
			// mPointerLocationInputHandler, mHandler.getLooper().getQueue());
			mPointerLocationInputEventReceiver = new PointerLocationInputEventReceiver(
					mPointerLocationInputChannel, Looper.myLooper(),
					mPointerLocationView);
		}
	}
  
    public void destroy()
    {
    	//TODO do nothing now
    }
	
}
