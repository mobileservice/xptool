package zju.ccnt.xptools.mode;

import java.io.Serializable;

public class DeviceInfo implements Serializable {
	public static String TABLE = "device_info";
	public static String ID = "id";
	public static String X_SIZE = "xSize";
	public static String Y_SIZE = "ySize";
	public static String STORAGE = "storage";
	public static String CPU_INFO = "CPU_info";
	public static String GPU_INFO = "GPU_info";
	public static String MODEL = "model";
	public static String SYS_VERSION = "sys_version";
	public static String OUT_STORAGE = "out_storage";

	private String id;
	private int xSize;
	private int ySize;
	private long storage;
	private CpuInfo CPU_info;
	private GpuInfo GPU_info;
	private String model;
	private String sys_Version;
	private long out_Storage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getxSize() {
		return xSize;
	}

	public void setxSize(int xSize) {
		this.xSize = xSize;
	}

	public int getySize() {
		return ySize;
	}

	public void setySize(int ySize) {
		this.ySize = ySize;
	}

	public long getStorage() {
		return storage;
	}

	public void setStorage(long storage) {
		this.storage = storage;
	}

	public CpuInfo getCPU_info() {
		return CPU_info;
	}

	public void setCPU_info(CpuInfo cPU_info) {
		CPU_info = cPU_info;
	}

	public GpuInfo getGPU_info() {
		return GPU_info;
	}

	public void setGPU_info(GpuInfo gPU_info) {
		GPU_info = gPU_info;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSys_Version() {
		return sys_Version;
	}

	public void setSys_Version(String sys_Version) {
		this.sys_Version = sys_Version;
	}

	public long getOut_Storage() {
		return out_Storage;
	}

	public void setOut_Storage(long out_Storage) {
		this.out_Storage = out_Storage;
	}

	public class CpuInfo implements Serializable {
		private String cpuName;
		private int maxCpuFreq;
		private int minCpuFreq;

		public CpuInfo() {
			super();
		}

		public CpuInfo(String cpuName, int maxCpuFreq, int minCpuFreq) {
			super();
			this.cpuName = cpuName;
			this.maxCpuFreq = maxCpuFreq;
			this.minCpuFreq = minCpuFreq;
		}
		
		public CpuInfo(CpuInfo info) {
			super();
			this.cpuName = info.getCpuName();
			this.maxCpuFreq = info.getMaxCpuFreq();
			this.minCpuFreq = info.getMinCpuFreq();
		}

		public String getCpuName() {
			return cpuName;
		}

		public void setCpuName(String cpuName) {
			this.cpuName = cpuName;
		}

		public int getMaxCpuFreq() {
			return maxCpuFreq;
		}

		public void setMaxCpuFreq(int maxCpuFreq) {
			this.maxCpuFreq = maxCpuFreq;
		}

		public int getMinCpuFreq() {
			return minCpuFreq;
		}

		public void setMinCpuFreq(int minCpuFreq) {
			this.minCpuFreq = minCpuFreq;
		}

		@Override
		public String toString() {
			return "CpuInfo [cpuName=" + cpuName + ", maxCpuFreq=" + maxCpuFreq
					+ ", minCpuFreq=" + minCpuFreq + "]";
		}
	}

	public class GpuInfo implements Serializable {

	}

}
