package zju.ccnt.xptools.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import zju.ccnt.xptools.mode.TouchDataModel;


/**
 * 
 * @ClassName: FileUtil
 * @Description: 与文件相关工具类
 * @author Zhouqj
 * @date 2014-4-22 下午2:11:01
 * 
 */
public class FileUtil {
	/*private Context context;
	// SD卡是否存在
	private boolean hasSD = false;
	// SD卡的路径
	private String SDPATH;
	// 当前程序包的路径
	private String FILESPATH;

	public FileUtil(Context context) {
		this.context = context;
		this.hasSD = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		this.SDPATH = Environment.getExternalStorageDirectory().getPath();
		this.FILESPATH = this.context.getFilesDir().getPath();
	}
*/
	/**
	 * 
	 * 创建文件
	 * 
	 * @param @param fileName
	 * @param @throws IOException 设定文件
	 * @return File 返回类型
	 */
	public static File createFile(String fileName) throws IOException {
//		File file = new File(SDPATH + "//" + fileName);
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return file;
	}

	/**
	 * 
	 * 清空文件内容
	 * 
	 * @param fileName
	 * @return void 返回类型
	 * @throws
	 */
	public static void clearFile(String fileName) {
//		File file = new File(SDPATH + "//" + fileName);
		File file = new File(fileName);
		if (!file.exists() || file.isDirectory()) {
			return;
		}
		try {
			FileWriter writer = new FileWriter(file);
			writer.write("");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 以追加形式将TouchDataModel对象写入文件
	 * 
	 * @param fileName
	 * @param content
	 * @return void
	 */
	public static void writeFile(String fileName, TouchDataModel data) {	
		try {
			File file=new File(fileName);
			FileOutputStream fos = new FileOutputStream(file, true);
			ObjectOutputStream oos = null;
			if (file.length() < 1) {
				oos = new ObjectOutputStream(fos);
			}else {
				oos = new MyObjectOutputStream(fos);
			}
			oos.writeObject(data);
			oos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 从文件中读取TouchDataModel对象
	 * 
	 * @param fileName
	 * @return List<TouchDataModel> 返回类型
	 * @throws
	 */
	public static List<TouchDataModel> readFile(String fileName) {
		/*StringBuffer sb = new StringBuffer();
//		File file = new File(SDPATH + "//" + fileName);
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			int c;
			while ((c = fis.read()) != -1) {
				sb.append((char) c);
			}
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();*/
		List<TouchDataModel> list = new ArrayList<TouchDataModel>();
		try {
			File file = new File(fileName);
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object object = null;
			while (fis.available() > 0) {
				object = ois.readObject();
				if (object instanceof TouchDataModel) {
					list.add((TouchDataModel)object);
				}
			}
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		return list;
	}

}
