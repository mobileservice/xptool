package zju.ccnt.xptools.util;

import java.util.List;

import zju.ccnt.xptools.mode.TouchDataModel;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper mapper = null;
	private static String json = null;
	public static String dataToJson(List<Object> data){
		try {
			mapper = new ObjectMapper();
			json = mapper.writeValueAsString(data);
			Log.d("JSON", json);
			return json;
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	public static String dataToJson(Object data){
		try {
			mapper = new ObjectMapper();
			json = mapper.writeValueAsString(data);
			return json;
		} catch (JsonProcessingException e) {
			return null;
		}
	}
}
