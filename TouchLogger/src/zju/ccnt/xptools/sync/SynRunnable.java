package zju.ccnt.xptools.sync;

public abstract class SynRunnable implements Runnable {
	protected Class model;
	
	
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

	public SynRunnable(Class model) {
		setModelClass(model);
	}

	/*
	 *	Details of reading file and upload to server.
	 */
	@Override
	abstract public void run();
}
