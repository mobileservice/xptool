package ccnt.experience.service;


import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import ccnt.experience.bean.DeviceInfo;
import ccnt.experience.var.DataBaseInfo;

public class DeviceInfoService {
	private Connection cnnConnection = null;

	// public static void main(String[] args) throws SQLException,
	// ClassNotFoundException {
	// DeviceInfo deviceInfo = new DeviceInfo();
	// deviceInfo.setId(1);
	// deviceInfo.setxSize(2);
	// deviceInfo.setySIze(3);
	// deviceInfo.setSize(4);
	// deviceInfo.setStorage(5);
	// deviceInfo.setCPU_style("CPU");
	// deviceInfo.setGPU_style("GPU");
	// deviceInfo.setModel("model");
	// deviceInfo.setSys_version("sys_version");
	// deviceInfo.setOut_storage(6);
	// System.out.println(saveDeviceInfo(deviceInfo));
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
	 * 
	 * 测试WebService连通性
	 */
	public String testConnection() {
		return "Hello,word!";
	}

	/*
	 * 将设备信息存入设备表
	 * 
	 * @para:设备信息 将设备信息存入设备表
	 * 
	 * @return:(1) the row count for SQL Data Manipulation Language (DML)
	 * statements (2) 0 for SQL statements that return nothing
	 */
	public int saveDeviceInfo(DeviceInfo deviceInfo) throws SQLException,
			ClassNotFoundException {
		getConnection();
		String insertDevice = "insert into " + DataBaseInfo.DEVICE_INFO_TABLE
				+ " " + DataBaseInfo.DEVICE_TABLE_FORM + " values ("
				+ deviceInfo.getId() + "," + deviceInfo.getxSize() + ","
				+ deviceInfo.getySIze() + "," + deviceInfo.getSize() + ","
				+ deviceInfo.getStorage() + ",'" + deviceInfo.getCPU_style()
				+ "','" + deviceInfo.getGPU_style() + "','"
				+ deviceInfo.getModel() + "','" + deviceInfo.getSys_version()
				+ "'," + deviceInfo.getOut_storage() + ")";
		Statement statement = cnnConnection.createStatement();
		int ret = statement.executeUpdate(insertDevice);
		cnnConnection.close();
		return ret;
	}

	/*
	 * 将设备信息存入设备表
	 * 
	 * @para:参数为设备信息的JSON格式的字符串
	 */

	public int saveDeviceInfoJson(String deviceInfo) throws JsonParseException,
			JsonMappingException, IOException, SQLException,
			ClassNotFoundException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		DeviceInfo deviceInfoData = mapper.readValue(deviceInfo,
				DeviceInfo.class);
		return saveDeviceInfo(deviceInfoData);
	}
}
