package zju.ccnt.xptools.inputmanager;

public class InputManagerConstants {
	public static final int EVENT_BUFFER_SIZE=256;
	public static final int RAW_EVENT_FRAME_SIZE=16;
	
	public static final int EV_SYN=0x00;
	public static final int EV_KEY=0x01;
	public static final int EV_REL=0x02;
	public static final int EV_ABS=0x03;
	public static final int EV_MSC=0x04;
	public static final int EV_SW=0x05;
	public static final int EV_LED=0x11;
	public static final int EV_SND=0x12;
	public static final int EV_REP=0x14;
	public static final int EV_FF=0x15;
	public static final int EV_PWR=0x16;
	public static final int EV_FF_STATUS=0x17;
	public static final int EV_MAX=0x1f;
	public static final int EV_CNT=EV_MAX+1;
	
	public static final int ABS_MT_SLOT=0x2f;
	public static final int ABS_MT_TOUCH_MAJOR=0x30;
	public static final int ABS_MT_TOUCH_MINOR=0x31;
	public static final int ABS_MT_WIDTH_MAJOR=0x32;
	public static final int ABS_MT_WIDTH_MINOR=0x33;
	public static final int ABS_MT_ORIENTATION=0x34;
	public static final int ABS_MT_POSITION_X=0x35;
	public static final int ABS_MT_POSITION_Y=0x36;
	public static final int ABS_MT_TOOL_TYPE=0x37;
	public static final int ABS_MT_BLOB_ID=0x38;
	public static final int ABS_MT_TRACKING_ID=0x39;
	public static final int ABS_MT_PRESSURE=0x3a;
	public static final int ABS_MT_DISTANCE=0x3;
	
	/**
	 * Synchronization events
	 */
	public static final int SYN_REPORT=0;
	public static final int SYN_CONFIG=1;
	public static final int SYN_MT_REPORT=2;
	public static final int SYN_DROPPED=3;
	
	/**
	 * MT_TOOL types
	 */
	public static final int MT_TOOL_FINGER=0;
	public static final int MT_TOOL_PEN=1;
	public static final int MT_TOOL_MAX=1;
	public static final int AMOTION_EVENT_TOOL_TYPE_UNKNOW=0;
	public static final int AMOTION_EVENT_TOOL_TYPE_FINGER=1;
	public static final int AMOTION_EVENT_TOOL_TYPE_STYLUS=2;
	
	//Maximum number of pointers supported per motion event
	public static final int MAX_POINTERS=16;
	//Maximum pointer id value supported in a motion event.
	public static final int MAX_POINTER_ID=31;
	
	
	
	/** linux definition */
}
