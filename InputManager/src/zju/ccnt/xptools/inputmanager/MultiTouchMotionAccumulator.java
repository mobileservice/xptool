package zju.ccnt.xptools.inputmanager;



/**
 * Keeps track of the state of multi-touch protocol.
 * Reference file platform/base/services/input/InputReader.h,InputReader.cpp
 * 
 * @author zhongjinwen 
 *
 */
public class MultiTouchMotionAccumulator {
	public MultiTouchMotionAccumulator(){
		mCurrentSlot=-1;
		mSlotCount=0;
		mUsingSlotsProtocol=false;
		mHaveStylus=false;
	}
	
	public void configure(int slotCount,boolean usingSlotsProtocol){
		mSlotCount=slotCount;
//		mUsingSlotsProtocol=usingSlotsProtocol;
		mUsingSlotsProtocol=false;
		mHaveStylus=false;
		mSlots=new Slot[slotCount];
		//new slotCount Slot
		for(int i=0;i<slotCount;i++){
			mSlots[i]=new Slot();
		}
	}
	
	void reset(){
		clearSlots(-1);
	}
	
	void clearSlots(int initialSlot){
		if(mSlots!=null){
			for(int i=0;i<mSlotCount;i++){
				mSlots[i].clear();
			}
		}
		mCurrentSlot=initialSlot;
	}
	
	void process(RawEvent rawEvent){
		//most events are EV_ABS here
		if(rawEvent.type==InputManagerConstants.EV_ABS){
			boolean newSlot=false;
			if(mUsingSlotsProtocol){
				
			}else if(mCurrentSlot<0){
				mCurrentSlot=0;
			}
			if(mCurrentSlot<0||mCurrentSlot>=mSlotCount){
				if(newSlot){
					
				}
			}else {
//				Slot slot=new Slot();
				Slot slot=mSlots[mCurrentSlot];
				switch (rawEvent.code) {
				case InputManagerConstants.ABS_MT_POSITION_X:
					slot.mInUse=true;
					slot.mAbsMTPositionX=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_POSITION_Y:
					slot.mInUse=true;
					slot.mAbsMTPositionY=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_TOUCH_MAJOR:
					slot.mInUse=true;
					slot.mAbsMTTouchMajor=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_TOUCH_MINOR:
					slot.mInUse=true;
					slot.mAbsMTTouchMinor=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_WIDTH_MAJOR:
					slot.mInUse=true;
					slot.mAbsMTWidthMajor=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_WIDTH_MINOR:
					slot.mInUse=true;
					slot.mAbsMTWidthMinor=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_ORIENTATION:
					slot.mInUse=true;
					slot.mAbsMTOrientation=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_TRACKING_ID:
					if(mUsingSlotsProtocol&&rawEvent.value<0){
						slot.mInUse=false;
					}else{
						slot.mInUse=true;
						slot.mAbsMTTrackingId=rawEvent.value;
					}
					break;
				case InputManagerConstants.ABS_MT_PRESSURE:
					slot.mInUse=true;
					slot.mAbsMTPressure=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_DISTANCE:
					slot.mInUse=true;
					slot.mAbsMTDistance=rawEvent.value;
					break;
				case InputManagerConstants.ABS_MT_TOOL_TYPE:
					slot.mInUse=true;
					slot.mAbsMTToolType=rawEvent.value;
					slot.mHaveAbsMTToolType=true;
					break;
				default:
					break;
				}
			}
			checkValid();
		}else if(rawEvent.type==InputManagerConstants.EV_SYN&&rawEvent.code==InputManagerConstants.SYN_MT_REPORT){
			mCurrentSlot+=1;
		}
			
	}
	
	private void checkValid(){
		if(mSlots[mCurrentSlot].mAbsMTTouchMajor==0){
			isValid=false;
		}
	}
	
	void finishSync(){
		if(!mUsingSlotsProtocol){
			clearSlots(-1);
		}
	}
	
	boolean hasStylus(){
		return mHaveStylus;
	}
	
	public int getSlotCount(){ return mSlotCount;}
	public final Slot getSlot(int index){
		return mSlots[index];
	}
	private int mCurrentSlot;
	Slot[] mSlots;
	int mSlotCount;	//it indicates the maximum touch pointers the screen support
	boolean mUsingSlotsProtocol;
	boolean mHaveStylus;
	/**
	 * Some device will report some in-complete information,
	 * this should be checked in accumulated and processed 
	 * int process() function. 
	 */
	boolean isValid=true;
	
			
	
	/**
	 * Each Slot contains complete information of a touc event.
	 * 
	 * @author zhongjinwen
	 */
	public static class Slot{
		private boolean mInUse;
		private boolean mHaveAbsMTTouchMinor;
		private boolean mHaveAbsMTWidthMinor;
		private boolean mHaveAbsMTToolType;
		
		private int mAbsMTPositionX;
		private int mAbsMTPositionY;
		private int mAbsMTTouchMajor;
		private int mAbsMTTouchMinor;
		private int mAbsMTWidthMajor;
		private int mAbsMTWidthMinor;
		private int mAbsMTOrientation;
		private int mAbsMTTrackingId;
		private int mAbsMTPressure;
		private int mAbsMTDistance;
		private int mAbsMTToolType;
		
		public boolean isInUse(){return mInUse;}
		public int getX(){return mAbsMTPositionX;}
		public int getY(){return mAbsMTPositionY;}
		public int getTouchMajor(){return mAbsMTTouchMajor;}
		public int getTouchMinor(){return mHaveAbsMTTouchMinor?mAbsMTTouchMinor:mAbsMTTouchMajor;}
		public int getToolMajor(){return mAbsMTWidthMajor;}
		public int getToolMinor(){return mHaveAbsMTWidthMinor?mAbsMTWidthMinor:mAbsMTWidthMajor;}
		public int getOrientation(){return mAbsMTOrientation;}
		public int getTrackingId(){return mAbsMTTrackingId;}
		public int getPressure(){return mAbsMTPressure;}
		public int getDistance(){return mAbsMTDistance;}
		public int getToolType(){
			if(mHaveAbsMTToolType){
				switch (mAbsMTToolType) {
				case InputManagerConstants.MT_TOOL_FINGER:
					return InputManagerConstants.AMOTION_EVENT_TOOL_TYPE_FINGER;
				case InputManagerConstants.MT_TOOL_PEN:
					return InputManagerConstants.AMOTION_EVENT_TOOL_TYPE_STYLUS;
				default:
					break;
				}
			}
			return InputManagerConstants.AMOTION_EVENT_TOOL_TYPE_UNKNOW;
			
//			return mAbsMTToolType;
		}
		
		private Slot(){
			clear();
		}
		void clear(){
			mInUse=false;
			mHaveAbsMTTouchMinor=false;
			mHaveAbsMTWidthMinor=false;
			mHaveAbsMTToolType=false;
			mAbsMTPositionX=0;
			mAbsMTPositionY=0;
			mAbsMTTouchMajor=0;
			mAbsMTTouchMinor=0;
			mAbsMTWidthMajor=0;
			mAbsMTWidthMinor=0;
			mAbsMTOrientation=0;
			mAbsMTTrackingId=-1;
			mAbsMTPressure=0;
			mAbsMTDistance=0;
			mAbsMTToolType=0;
		}
	}//end of Slot
}
