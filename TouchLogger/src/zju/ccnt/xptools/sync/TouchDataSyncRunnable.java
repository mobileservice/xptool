package zju.ccnt.xptools.sync;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.loopj.android.http.RequestParams;

import android.text.style.SuperscriptSpan;
import zju.ccnt.xptools.http.HttpUtil;
import zju.ccnt.xptools.http.ResponseHandler;
import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.util.ConfData;
import zju.ccnt.xptools.util.FileUtil;
import zju.ccnt.xptools.util.JsonUtil;

public class TouchDataSyncRunnable extends SyncRunnable{

	public TouchDataSyncRunnable(Class model) {
		super(model);
	}

	@Override
	public void run() {
		//List<TouchDataModel> dataList=FileUtil.readTouchDataModelsFromStream(inputStream);
		List<Object> dataList=syncComponent.readModels();
		RequestParams rp=new RequestParams();
		String s=JsonUtil.dataToJson(dataList);
		rp.add("touchDataModel", s);
		String url=ConfData.HTTP_URL_HEAD+
				"TouchDataService/saveJsonListTouchData";
		//TODO 使用HTTP GET 还是 HTTP POST
		//upload to server
		HttpUtil.get(url, new ResponseHandler());
	}
	
//	/**
//	 * 
//	 * 从文件中读取TouchDataModel对象
//	 * 
//	 * @param fileName
//	 * @return List<TouchDataModel> 返回类型
//	 * @throws
//	 */
//	public List<TouchDataModel> readList() {
//		List<TouchDataModel> list = new ArrayList<TouchDataModel>();
//			try {
//				ObjectInputStream ois = new ObjectInputStream(inputStream);
//				Object object = null;
//				while (inputStream.available() > 0) {
//					object = ois.readObject();
//					if (object instanceof TouchDataModel) {
//						list.add((TouchDataModel)object);
//					}
//				}
//				ois.close();
//			} catch (StreamCorruptedException e) {
//				e.printStackTrace();
//			} catch (OptionalDataException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		return list;
//	}

}
