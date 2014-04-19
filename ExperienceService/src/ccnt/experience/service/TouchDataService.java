package ccnt.experience.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import ccnt.experience.bean.DeviceInfo;
import ccnt.experience.bean.TouchDataModel;
import ccnt.experience.var.DataBaseInfo;

public class TouchDataService {
	private Connection cnnConnection = null;

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
	 * 获取连接
	 */
	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DataBaseInfo.JDBC_DRIVER);
		cnnConnection = DriverManager.getConnection(DataBaseInfo.DATABASE_URL,
				DataBaseInfo.DB_UNAME, DataBaseInfo.DB_PWORD);
	}

	/*
	 * 将设备信息存入设备表
	 */
	public String saveTouchData(TouchDataModel touchDataModel)
			throws SQLException, ClassNotFoundException {
		getConnection();
		// touchDataModel.getTrace_detail().toString() 暂时以"ahha"代替
		String insertTouch = "insert into " + DataBaseInfo.TOUCH_DATA_TABLE
				+ " " + DataBaseInfo.TOUCH_TABLE_FORM + " values ("
				+ touchDataModel.getId() + ",'" + "ahha" + "','"
				+ touchDataModel.getCurrent_app() + "','"
				+ touchDataModel.getCurrent_activity() + "',"
				+ touchDataModel.getDevice_id() + ")";
		Statement statement = cnnConnection.createStatement();
		statement.executeUpdate(insertTouch);
		System.out.println(insertTouch);
		cnnConnection.close();
		return insertTouch;
	}
}
