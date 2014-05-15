package zju.ccnt.xptools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import zju.ccnt.xptools.mode.DeviceInfo;
import zju.ccnt.xptools.mode.DeviceInfo.CpuInfo;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class DeviceInfoUtil {
	Context mContext;

	public DeviceInfoUtil(Context mContext) {
		super();
		this.mContext = mContext;
	}

	/**
	 * 
	 * @Title: getDeviceId
	 * @Description: 获取设备ID
	 * @return String 返回类型
	 * @throws
	 */
	public String getDeviceId() {
		String deviceId = null;
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = tm.getDeviceId();
		if (deviceId == null || deviceId.equals("")) {
			deviceId = android.os.Build.SERIAL;
		}
		return deviceId;
	}

	/**
	 * 
	 * @Title: getXSize
	 * @Description: 获取X方向分辨率
	 * @return int 返回类型
	 * @throws
	 */
	public int getXSize() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getResources().getDisplayMetrics();
		int xSize;
		xSize = dm.widthPixels;
		return xSize;
	}

	/**
	 * 
	 * @Title: getYSize
	 * @Description: 获取Y方向分辨率
	 * @return int 返回类型
	 * @throws
	 */
	public int getYSize() {
		DisplayMetrics dm = new DisplayMetrics();
		dm = mContext.getResources().getDisplayMetrics();
		int ySize;
		ySize = dm.heightPixels;
		return ySize;
	}

	/**
	 * 
	 * @Title: getStorage
	 * @Description: 获取内存
	 * @return long 返回类型
	 * @throws
	 */
	public long getStorage() {
		long storage;
		String path = "/proc/meminfo";
		String content = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path), 8);
			String line;
			if ((line = br.readLine()) != null) {
				content = line;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// beginIndex
		int begin = content.indexOf(':');
		// endIndex
		int end = content.indexOf('k');

		content = content.substring(begin + 1, end).trim();
		storage = Integer.parseInt(content);
		return storage;
	}

	/**
	 * 
	 * @Title: getCpuInfo
	 * @Description: 获取CPU信息
	 * @return CpuInfo 返回类型
	 * @throws
	 */
	public CpuInfo getCpuInfo() {
		CpuInfo info = new DeviceInfo.CpuInfo();
		info.setCpuName(getCpuName());
		info.setMaxCpuFreq(getMaxCpuFreq());
		info.setMinCpuFreq(getMinCpuFreq());
		return info;
	}

	/**
	 * 
	 * @Title: getMaxCpuFreq
	 * @Description: 获取CPU最大频率
	 * @return int 返回类型
	 * @throws
	 */
	public int getMaxCpuFreq() {
		int result = 0;
		FileReader fr = null;
		BufferedReader br = null;
		final String kCpuInfoMaxFreqFilePath = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
		try {
			fr = new FileReader(kCpuInfoMaxFreqFilePath);
			br = new BufferedReader(fr);
			String text = br.readLine();
			result = Integer.parseInt(text.trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	/**
	 * 
	 * @Title: getMinCpuFreq
	 * @Description: 获取CPU最小频率
	 * @return int 返回类型
	 * @throws
	 */
	public int getMinCpuFreq() {
		int result = 0;
		FileReader fr = null;
		BufferedReader br = null;
		final String kCpuInfoMinFreqFilePath = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq";
		try {
			fr = new FileReader(kCpuInfoMinFreqFilePath);
			br = new BufferedReader(fr);
			String text = br.readLine();
			result = Integer.parseInt(text.trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	/**
	 * 
	 * @Title: getCpuName
	 * @Description: 获取CPU名称
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String getCpuName() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader("/proc/cpuinfo");
			br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+", 2);
			for (int i = 0; i < array.length; i++) {
			}
			return array[1];
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fr != null)
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

	/**
	 * 
	 * @Title: getModel
	 * @Description: 获取收集型号
	 * @return String 返回类型
	 * @throws
	 */
	public String getModel() {
		String model = null;
		Build bd = new Build();
		model = bd.MODEL;
		return model;
	}

	/**
	 * 
	 * @Title: getSysVersion
	 * @Description: 获取系统版本
	 * @return String 返回类型
	 * @throws
	 */
	public String getSysVersion() {
		String sys_Version = null;
		sys_Version = android.os.Build.VERSION.SDK;
		return sys_Version;
	}

	/**
	 * 
	 * @Title: getOut_Storage
	 * @Description: 获取外存大小
	 * @return long 返回类型
	 * @throws
	 */
	public long getOut_Storage() {
		long out_Storage;
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize1 = stat.getBlockSize();
		long totalBlocks1 = stat.getBlockCount();
		out_Storage = blockSize1 * totalBlocks1;
		return out_Storage;
	}

}
