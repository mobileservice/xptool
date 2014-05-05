package zju.ccnt.xptools.util;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 
 * @author zhongjinwen
 *
 */
public class FileBasedObjectOutputStream extends ObjectOutputStream {
	public FileBasedObjectOutputStream() throws IOException {
		super();
	}

	public FileBasedObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		super.writeStreamHeader();
	}
}
