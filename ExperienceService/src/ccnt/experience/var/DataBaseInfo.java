package ccnt.experience.var;

public final class DataBaseInfo {
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; // 数据库驱动
	public static final String DATABASE_URL = "jdbc:mysql://192.168.0.96:3306/experience_computing"; // 数据
	public static final String DEVICE_INFO_TABLE = "device_info";// 设备表名
	public static final String TOUCH_DATA_TABLE = "touch_data";// 数据表名
	public static final String DEVICE_TABLE_FORM = "(id,xSize,ySize,size,storage,CPU_style,GPU_style,model,sys_version,out_storage)";// 设备表属性
	public static final String TOUCH_TABLE_FORM = "(ID,trace_detail,current_app,current_activity,device_Id)";// 数据表属性
	public static final String DB_UNAME = "root"; // 数据库用户名
	public static final String DB_PWORD = "12345678"; // 数据库用户密码
}
