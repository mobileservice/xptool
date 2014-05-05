package zju.ccnt.xptools.syn;

import java.io.InputStream;

public abstract class SynRunnable implements Runnable {
	protected Class model;

	protected InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/*
	 * Get the Class presentation of the model whose instance is to be stored.
	 */
	public Class getModelClass() {
		return model;
	}

	/*
	 * Set the Class presentation of the model whose instance is to be stored.
	 */
	public void setModelClass(Class model) {
		this.model = model;
	}

	public SynRunnable(Class model) {
		setModelClass(model);
	}

	/*
	 * Details of reading file and upload to server.
	 */
	@Override
	abstract public void run();
}
