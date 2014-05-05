package zju.ccnt.xptools.sync;

import java.util.List;

import zju.ccnt.xptools.mode.TouchDataModel;
import zju.ccnt.xptools.util.FileUtil;

public class TouchDataSyncRunnable extends SyncRunnable{

	public TouchDataSyncRunnable(Class model) {
		super(model);
	}

	@Override
	public void run() {
		List<TouchDataModel> dataList=FileUtil.readFile(model.getName());
		//TODO 上传服务器
	}

}
