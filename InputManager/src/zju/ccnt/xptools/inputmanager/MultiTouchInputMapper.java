package zju.ccnt.xptools.inputmanager;

import java.util.concurrent.CountDownLatch;

import android.R.integer;
import android.util.Log;
import zju.ccnt.xptools.inputmanager.MultiTouchMotionAccumulator.Slot;

public class MultiTouchInputMapper extends TouchInputMapper {
	private MultiTouchMotionAccumulator mMultiTouchMotionAccumulator;
	private BitSet mPointerIdBits;
	private int mPointerTrackingIdMap[];//TODO not initialized
	
	public MultiTouchInputMapper(){
		mMultiTouchMotionAccumulator=new MultiTouchMotionAccumulator();
		mPointerIdBits=new BitSet(0);
		//we use 4 slots
		mMultiTouchMotionAccumulator.configure(4, false);
		mPointerTrackingIdMap=new int[InputManagerConstants.MAX_POINTER_ID+1];
	}
	
	@Override
	void process(RawEvent rawEvent){
		if(mMultiTouchMotionAccumulator.isValid==false){
			mMultiTouchMotionAccumulator.isValid=true;
			mMultiTouchMotionAccumulator.finishSync();
		}else{
			super.process(rawEvent);
			mMultiTouchMotionAccumulator.process(rawEvent);
		}
	}
	
	@Override
	boolean syncTouch(long when){
		boolean outHavePointerIds=true;
		boolean res=true;
		int inCount=mMultiTouchMotionAccumulator.getSlotCount();
		int outCount=0;
		BitSet newPointerIdBits=new BitSet();
		
		for(int inIndex=0;inIndex<inCount;inIndex++){
			Slot inSlot=mMultiTouchMotionAccumulator.getSlot(inIndex);
			if(!inSlot.isInUse()){
				continue;
			}
			if(outCount>=InputManagerConstants.MAX_POINTERS){
				break;//too many fingers!
			}
			RawPointerData.Pointer outPointer=mCurrentRawPointerData.pointers[outCount];
			outPointer.x=inSlot.getX();
			outPointer.y=inSlot.getY();
			outPointer.pressure=inSlot.getPressure();
			outPointer.touchMajor=inSlot.getTouchMajor();
			outPointer.touchMinor=inSlot.getTouchMinor();
			outPointer.toolMajor=inSlot.getToolMajor();
			outPointer.toolMinor=inSlot.getToolMinor();
			outPointer.orientation=inSlot.getOrientation();
			outPointer.distance=inSlot.getDistance();
			outPointer.tiltX=0;
			outPointer.tiltY=0;
			
			outPointer.toolType=inSlot.getToolType();
			//TODO set the tooltype to finger directly
			outPointer.toolType=InputManagerConstants.AMOTION_EVENT_TOOL_TYPE_FINGER;
			//TODO set isHovering to false directly
			boolean isHovering=false;
			outPointer.isHovering=isHovering;
			
			//Assign pointer id using tracking id if available
			if(outHavePointerIds){
				int trackingId=inSlot.getTrackingId();
				int id=-1;
				if(trackingId>=0){
					BitSet idBitSet=new BitSet(mPointerIdBits.value);
					for(;!idBitSet.isEmpty();){
						int n=idBitSet.clearFirstMarkedBit();
						if(mPointerTrackingIdMap[n]==trackingId){
							id=n;
						}
					}
					if(id<0&&!mPointerIdBits.isFull()){
						id=mPointerIdBits.markFirstUnmarkedBit();
						mPointerTrackingIdMap[id]=trackingId;
					}
				}
				if(id<0){
					//type A, no id
					res=false;
					mCurrentRawPointerData.clearIdBits();
					newPointerIdBits.clear();
				}else{
					outPointer.id=id;
					mCurrentRawPointerData.idToIndex[id]=outCount;
					mCurrentRawPointerData.markIdBit(id, isHovering);
					newPointerIdBits.markBit(id);
				}
			}
			outCount+=1;
		}
		mCurrentRawPointerData.pointerCount=outCount;

		mPointerIdBits.value=newPointerIdBits.value;
		mMultiTouchMotionAccumulator.finishSync();
		

//		String s="";
//		for(int i=0;i<outCount;i++){
//			int x=mCurrentRawPointerData.pointers[i].x;
//			int y=mCurrentRawPointerData.pointers[i].y;
//			int id=mCurrentRawPointerData.pointers[i].id;
//			s=s+"("+id+","+x+","+y+"),";
//		}
//		Log.d("RAW", s);
		return res;
	}
}
