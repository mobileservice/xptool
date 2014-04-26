package zju.ccnt.xptools.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
/**
 * 
 * @ClassName: HttpUtil 
 * @Description: Http请求工具类
 * @author Zhouqj 
 * @date 2014-4-24 下午2:38:05 
 *
 */
public class HttpUtil {
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	static {
		client.setTimeout(10000); // 设置链接超时，如果不设置，默认为10s
	}

	// 使用url获取String对象
	public static void get(String url, AsyncHttpResponseHandler res) {
		client.get(url, res);
	}

	// url里面带参数
	public static void get(String url, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.get(url, params, res);
	}

	// 不带参数，获取json对象或者数组
	public static void get(String url, JsonHttpResponseHandler res) {
		client.get(url, res);
	}

	// 带参数，获取json对象或者数组
	public static void get(String url, RequestParams params,
			JsonHttpResponseHandler res) {
		client.get(url, params, res);
	}

	// 下载数据使用，会返回byte数据
	public static void get(String url, BinaryHttpResponseHandler res) {
		client.get(url, res);
	}

	// 上传
	public static void post(String url, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.post(url, params, res);
	}

	public static AsyncHttpClient getClient() {
		return client;
	}
}
