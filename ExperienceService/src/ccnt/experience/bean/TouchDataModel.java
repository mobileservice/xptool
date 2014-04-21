package ccnt.experience.bean;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * @ClassName TouchDataModel
 * @Description 轨迹数据
 * @author zhouqj
 * @date date 2014-4-16 上午10:52:39
 */
public class TouchDataModel {
	public static String TABLE = "touch_data";
	public static String ID = "id";
	public static String DEVICE_ID = "device_id";
	public static String TRACE_DETAIL = "trace_detail";
	// 当前app
	public static String CURRENTAPP = "current_app";
	// 当前activity
	public static String CURRENTACTIVITY = "current_activity";

	private int id;
	private String current_app;
	private String current_activity;
	private ArrayList<ArrayList<PointData>> trace_detail;
	private int device_id;
	public void TouchDataModel() {
		trace_detail = new ArrayList<ArrayList<PointData>>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrent_app() {
		return current_app;
	}

	public void setCurrent_app(String current_app) {
		this.current_app = current_app;
	}

	public String getCurrent_activity() {
		return current_activity;
	}

	public void setCurrent_activity(String current_activity) {
		this.current_activity = current_activity;
	}

	public ArrayList<ArrayList<PointData>> getTrace_detail() {
		return trace_detail;
	}

	private void setTrace_detail(ArrayList<ArrayList<PointData>> trace_detail) {
		this.trace_detail = trace_detail;
	}

	public int getDevice_id() {
		return device_id;
	}

	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}

	/**
	 * 每个像素点的位置、速度、压力等信息
	 */
	public class PointData {
		public Calendar calendar;
		public float x;
		public float y;
		public float velocity;
		public float xVelocity;
		public float yVelocity;
		public float pressure;
		public float extimateX;
		public float extimateY;
	}
}
