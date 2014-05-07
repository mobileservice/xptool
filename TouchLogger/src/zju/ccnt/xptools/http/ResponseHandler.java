package zju.ccnt.xptools.http;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class ResponseHandler extends AsyncHttpResponseHandler {
	public ResponseHandler(){
		
	}
	
	@Override
	public void onSuccess(String args){
		Log.d("RESPONSE", "success: "+args);
	}
	
	@Override 
	public void onFailure(Throwable error){
		Log.d("RESPONSE", "failure: "+error.getMessage());
		
	}
	
	@Override
	public void onFinish(){
		super.onFinish();
		Log.d("RESPONSE", "finish");
	}
}
