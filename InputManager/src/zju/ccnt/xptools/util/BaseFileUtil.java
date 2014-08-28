package zju.ccnt.xptools.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BaseFileUtil {
	/**
	 * 创建文件
	 * 
	 * @param @param fileName
	 * @param @throws IOException 设定文件
	 * @return File 返回类型
	 */
	public static File createFile(String fileName) throws IOException {
//		File file = new File(SDPATH + "//" + fileName);
		File file = new File(ConfData.FILE_PATH+fileName);
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
		File file = new File(ConfData.FILE_PATH+fileName);
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
}
