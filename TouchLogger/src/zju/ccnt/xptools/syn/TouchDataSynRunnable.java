package zju.ccnt.xptools.syn;

import java.util.List;

import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.util.FileUtil;

public class TouchDataSynRunnable extends SynRunnable{

	public TouchDataSynRunnable(Class model) {
		super(model);
	}

	@Override
	public void run() {
		List<TouchDataModel> dataList=FileUtil.readFile(model.getName());
		//TODO 上传服务器
	}

}
