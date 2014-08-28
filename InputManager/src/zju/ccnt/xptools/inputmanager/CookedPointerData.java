package zju.ccnt.xptools.inputmanager;

import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;

public class CookedPointerData {
	int pointerCount;
	MotionEvent.PointerProperties[] pointerProperties;
	MotionEvent.PointerCoords[] pointerCoords;
	BitSet hoveringIdBits,touchingIdBits;
	int idToIndex[];
	
	//Calibrations
	
	
	public CookedPointerData() {
		pointerCoords=new PointerCoords[InputManagerConstants.MAX_POINTERS];
		for(int i=0;i<pointerCoords.length;i++){
			pointerCoords[i]=new PointerCoords();
		}
		pointerProperties=new PointerProperties[InputManagerConstants.MAX_POINTERS];
		for(int i=0;i<pointerProperties.length;i++){
			pointerProperties[i]=new PointerProperties();
		}
		hoveringIdBits=new BitSet();
		touchingIdBits=new BitSet();
		idToIndex=new int[InputManagerConstants.MAX_POINTER_ID+1];
	}
	
	void clear(){
		pointerCount=0;
		hoveringIdBits.clear();
		touchingIdBits.clear();
	}
	
	boolean isHovering(int pointerIndex){
		return hoveringIdBits.hasBit(pointerProperties[pointerIndex].id);
		
//		pointerProperties[0]
//		pointerCoords[0].
	}
	
	void copyFrom(CookedPointerData other){
		pointerCount=other.pointerCount;
		hoveringIdBits=new BitSet(other.hoveringIdBits.value);
		touchingIdBits=new BitSet(other.touchingIdBits.value);
		for(int i=0;i<pointerCount;i++){
			pointerProperties[i].copyFrom(other.pointerProperties[i]);
			pointerCoords[i].copyFrom(other.pointerCoords[i]);
			int id=pointerProperties[i].id;
			idToIndex[id]=other.idToIndex[id];
		}
	}
	
	public static class Calibrations{
		
//		public Calibration(){
//			
//		}
	}
}
