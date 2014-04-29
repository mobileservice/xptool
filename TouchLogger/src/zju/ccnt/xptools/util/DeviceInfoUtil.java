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

/**
 * 
 * @ClassName: DeviceInfoUtil
 * @Description: 设备信息工具类
 * @author Zhouqj
 * @date 2014-4-29 上午10:34:50
 * 
 */
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
	 * @return String deviceId
	 * @throws
	 */
	public String getDeviceId() {
		String deviceId = null;
		TelephonyManager tm = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = tm.getDeviceId();
		return deviceId;
	}

	/**
	 * 
	 * @Title: getXSize
	 * @Description: 获取X方向像素点数
	 * @return int xSize
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
	 * @Description: 获取Y方向像素点数
	 * @return int ySize
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
	 * @Description: 获得内存大小
	 * @return long storage
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
		// 截取字符串信息

		content = content.substring(begin + 1, end).trim();
		storage = Integer.parseInt(content);
		return storage;
	}

	/**
	 * 
	 * @Title: getCpuInfo
	 * @Description: 获得CPU信息
	 * @return CpuInfo 返回类型
	 * @throws
	 */
	public CpuInfo getCpuInfo() {
		CpuInfo info = new DeviceInfo().new CpuInfo();
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
	 * @Description: 获取CPU名字
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
	 * @Description: 获取设备型号
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
	 * @Description: 获取系统SDK版本
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
	 * @Description: 获取存储器容量
	 * @return long    返回类型 
	 * @throws
	 */
	public long getOut_Storage() {
		long out_Storage;
		File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        //文件系统的块的大小（byte）
        long blockSize1 = stat.getBlockSize();
        //文件系统的总的块数
        long totalBlocks1 = stat.getBlockCount();       
        //总的容量
        out_Storage = blockSize1*totalBlocks1;
		return out_Storage;
	}
}
