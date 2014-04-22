/********************************************************************************
 * Copyright (c) CCNT
 * Version: 1.0
 * Author:zhouqj
 * Modified Time:2014-4-17 
 *******************************************************************************/
package zju.ccnt.xptools.mode;

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
	public static String DEVICE_ID="device_id";
	public static String TRACE_DETAIL="trace_detail";
	//当前app
	public static String CURRENTAPP = "current_app";
	//当前activity
	public static String CURRENTACTIVITY = "current_activity";
	//当前package
	public static String CURRENTPACKAGE = "current_package";
	
	private int id;
	private String device_id;
	private String current_app;
	private String current_activity;
	private String current_package;
	//多指、多点
	private ArrayList<ArrayList<PointData>> trace_detail;
	
	public TouchDataModel(){
		trace_detail=new ArrayList<ArrayList<PointData>>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
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
	
	public String getCurrent_package() {
		return current_package;
	}

	public void setCurrent_package(String current_package) {
		this.current_package = current_package;
	}

	public ArrayList<ArrayList<PointData>> getTrace_detail() {
		return trace_detail;
	}
	public void setTrace_detail(ArrayList<ArrayList<PointData>> trace_detail) {
		this.trace_detail = trace_detail;
	}
	public void addPointDatas(ArrayList<PointData> pointDatas){
		this.trace_detail.add(pointDatas);
	}

	/**
	 * 每个像素点的位置、速度、压力等信息
	 */
	public class PointData{
		public Calendar calendar;
		public float x;
		public float y;
		public float velocity;
		public float xVelocity;
		public float yVelocity;
		public float pressure;
		//预测坐标值
		public float extimateX;
		public float extimateY;
		public PointData(){
			this.calendar =  Calendar.getInstance();
		}
	}
}
