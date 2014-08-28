package zju.ccnt.xptools.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BaseFileUtil {
	/**
	 * �����ļ�
	 * 
	 * @param @param fileName
	 * @param @throws IOException �趨�ļ�
	 * @return File ��������
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
	 * ����ļ�����
	 * 
	 * @param fileName
	 * @return void ��������
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
