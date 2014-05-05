package zju.ccnt.xptools.http;

import com.loopj.android.http.AsyncHttpResponseHandler;

public class ResponseHandler extends AsyncHttpResponseHandler {
	public ResponseHandler(){
		
	}
	
	@Override
	public void onSuccess(String args){
		System.out.println("response success: "+args);
	}
	
	@Override 
	public void onFailure(Throwable error){
		System.out.println("response failure");
	}
	
	@Override
	public void onFinish(){
		super.onFinish();
		System.out.println("response onFinish");
	}
}
