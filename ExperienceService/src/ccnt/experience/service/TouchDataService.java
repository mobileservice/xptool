package ccnt.experience.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ccnt.experience.bean.TouchDataModel;
import ccnt.experience.bean.TouchDataModel.PointData;

import ccnt.experience.var.DataBaseInfo;

public class TouchDataService {
	private Connection cnnConnection = null;

	private static Logger log = Logger.getLogger(TouchDataService.class
			.getName());


	// public static void main(String[] args) throws SQLException,
	// ClassNotFoundException {
	// TouchDataModel touchDataModel = new TouchDataModel();
	// touchDataModel.setId(1);
	// touchDataModel.setCurrent_activity("current_activity");
	// touchDataModel.setCurrent_app("current_app");
	// touchDataModel.setDevice_id(2);
	// saveTouchData(touchDataModel);
	// }

	/*

	 * 获取数据库连接
	 */
	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DataBaseInfo.JDBC_DRIVER);
		cnnConnection = DriverManager.getConnection(DataBaseInfo.DATABASE_URL,
				DataBaseInfo.DB_UNAME, DataBaseInfo.DB_PWORD);
	}

	/*
	 * 测试WebService连通性
	 */
	public String testConnection() {
		return "Hello,word!";
	}

	/*
	 * 将动作信息中的ArrayList<ArrayList<PointData>>按照格式转为String
	 * 
	 * @para:动作信息对应的trace_detail
	 */
	private String pointDataToString(
			ArrayList<ArrayList<PointData>> trace_detail) {
		StringBuffer resBuffer = new StringBuffer();
		// 清空resBuffer
		resBuffer.setLength(0);
		int fingerCounts = trace_detail.size();
		resBuffer.append(fingerCounts + " ");
		for (int i = 0; i < fingerCounts; i++) {
			resBuffer.append(trace_detail.get(i).size() + " ");
		}
		for (int i = 0; i < fingerCounts; i++) {
			for (int j = 0; j < trace_detail.get(i).size(); j++) {
				PointData pointData = trace_detail.get(i).get(j);
				resBuffer.append(pointData.calendar.getTimeInMillis() + " ");
				resBuffer.append(pointData.x + " ");
				resBuffer.append(pointData.y + " ");
				resBuffer.append(pointData.velocity + " ");
				resBuffer.append(pointData.xVelocity + " ");
				resBuffer.append(pointData.yVelocity + " ");
				resBuffer.append(pointData.pressure + " ");
				resBuffer.append(pointData.extimateX + " ");
				resBuffer.append(pointData.extimateY + " ");
			}
		}
		return resBuffer.toString();
	}

	/*
	 * 将动作信息存入动作信息表
	 */
	private int saveTouchData(TouchDataModel touchDataModel)
			throws SQLException, ClassNotFoundException {
		getConnection();
		log.info(touchDataModel.toString());
		String trace_detail = pointDataToString(touchDataModel
				.getTrace_detail());
		String insertTouch = "insert into " + DataBaseInfo.TOUCH_DATA_TABLE
				+ " " + DataBaseInfo.TOUCH_TABLE_FORM + " values ("
				+ touchDataModel.getId() + ",'" + trace_detail + "','"
				+ touchDataModel.getCurrent_app() + "','"
				+ touchDataModel.getCurrent_activity() + "',"
				+ touchDataModel.getDevice_id() + ",'"
				+ touchDataModel.getCurrent_package() + "')";
		Statement statement = cnnConnection.createStatement();
		int ret = statement.executeUpdate(insertTouch);
		System.out.println(insertTouch);
		cnnConnection.close();
		return ret;
	}

	/**
	 * 单个
	 * 
	 * @param touchDataModel
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int saveJsonTouchData(String touchDataModel)
			throws JsonParseException, JsonMappingException, IOException,
			SQLException, ClassNotFoundException {
		System.out.println("input:  " + touchDataModel);
		int ret = 0;
		ObjectMapper mapper = new ObjectMapper();
		TouchDataModel tDataModel = mapper.readValue(touchDataModel,
				TouchDataModel.class);
		ret = saveTouchData(tDataModel);
		return ret;
	}

	/*
	 * 存储一系列的动作到动作信息表
	 * 
	 * @para:动作链表
	 */
	private int saveListTouchData(ArrayList<TouchDataModel> touchDataModelList) {
		int ret = 0;
		try {
			ret = 1;
			for (TouchDataModel touchDataModel : touchDataModelList) {
				if (saveTouchData(touchDataModel) == 0)
					ret = 0;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/*
	 * 存储一系列的动作到动作信息表
	 * 
	 * @para:一序列动作的JSON格式的String
	 */
	public int saveJsonListTouchData(String touchDatalList)
			throws JsonParseException, JsonMappingException, IOException {
		log.info(touchDatalList);
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<TouchDataModel> touchDataModelList = (ArrayList<TouchDataModel>) mapper
				.readValue(
						touchDatalList,
						mapper.getTypeFactory().constructParametricType(
								ArrayList.class, TouchDataModel.class));
		int ret = saveListTouchData(touchDataModelList);
		return ret;
	}
}
