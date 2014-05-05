package zju.ccnt.xptools.sync;

import java.io.InputStream;

import zju.ccnt.xptools.sync.SyncManager.SyncComponent;

public abstract class SyncRunnable implements Runnable {
	protected Class model;
	
	/**
	 * This only set when register to the SyncManager
	 */
	protected SyncComponent syncComponent;

	public SyncComponent getSyncComponent() {
		return syncComponent;
	}

	public void setSyncComponent(SyncComponent syncComponent) {
		this.syncComponent = syncComponent;
	}

	/*
	 * Get the Class presentation of the model whose
	 * instance is to be stored.
	 */
	public Class getModelClass() {
		return model;
	}

	/*
	 * Set the Class presentation of the model whose
	 * instance is to be stored.
	 */
	public void setModelClass(Class model) {
		this.model=model;
	}

	public SyncRunnable(Class model) {
		setModelClass(model);
	}

	/*
	 *	Details of reading file and upload to server.
	 */
	@Override
	abstract public void run();
}
