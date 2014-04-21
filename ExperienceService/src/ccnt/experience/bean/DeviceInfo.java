package ccnt.experience.bean;

public class DeviceInfo {
	// 数据库表头部分
	public static String TABLE = "device_info";// 表名
	public static String ID = "id";// 设备ID
	public static String X_SIZE = "xSize";// 分辨率X方向
	public static String Y_SIZE = "ySize";// 分辨率y方向
	public static String SIZE = "size";// 分辨率
	public static String STORAGE = "storage";// 内存
	public static String CPU_STYLE = "CPU_style";// CPU型号
	public static String GPU_STYLE = "GPU_style";// GPU型号
	public static String MODEL = "model";// 型号
	public static String SYS_VERSION = "sys_version";// 系统版本
	public static String OUT_STORAGE = "out_storage";// 硬盘

	// 数据部分
	private int id;
	private int xSize;
	private int ySIze;
	private int size;
	private int storage;
	private String CPU_style;
	private String GPU_style;
	private String model;
	private String sys_version;
	private int out_storage;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getxSize() {
		return xSize;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public int getySIze() {
		return ySIze;
	}

	public void setySIze(int ySIze) {
		this.ySIze = ySIze;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public String getCPU_style() {
		return CPU_style;
	}

	public void setCPU_style(String cPU_style) {
		CPU_style = cPU_style;
	}

	public String getGPU_style() {
		return GPU_style;
	}

	public void setGPU_style(String gPU_style) {
		GPU_style = gPU_style;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSys_version() {
		return sys_version;
	}

	public void setSys_version(String sys_version) {
		this.sys_version = sys_version;
	}

	public int getOut_storage() {
		return out_storage;
	}

	public void setOut_storage(int out_storage) {
		this.out_storage = out_storage;
	}

}
