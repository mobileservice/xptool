package zju.ccnt.xptools.inputmanager;

import java.security.PublicKey;

import android.R.bool;
import android.R.id;
import android.R.integer;
import android.app.Application;
import android.content.Context;
import android.gesture.Prediction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import zju.ccnt.xptools.TouchDataApplication;
import zju.ccnt.xptools.inputmanager.RawPointerData.Pointer;

public class TouchInputMapper {
	//raw pointer sample data
	RawPointerData mCurrentRawPointerData;
	RawPointerData mLastRawPointerData;
	//cookde pointer sample data
	CookedPointerData mCurrentCookedPointerData;
	CookedPointerData mLastCookedPointerData;
	RawPointerAxes mRawPointerAxes;
	
	
	
	PointerDistanceHeapElement heap[];//TODO not initialized
	
	BitSet matchedLastBits;
	BitSet matchedCurrentBits;
	BitSet usedIdBits;
	
	float mXScale;
	float mYScale;
	float mXPrecision;
	float mYPrecision;
	float mGeometricScale;
	
	Context context;
	
	
	
	
	public TouchInputMapper(){
		configureRawPointerAxes();
		initCalibration();
		matchedLastBits=new BitSet(0);
		matchedCurrentBits=new BitSet(0);
		usedIdBits=new BitSet(0);
		
		mCurrentRawPointerData=new RawPointerData();
		mLastRawPointerData=new RawPointerData();
		mCurrentCookedPointerData=new CookedPointerData();
		mLastCookedPointerData=new CookedPointerData();
		
		int heapSize=InputManagerConstants.MAX_POINTERS*InputManagerConstants.MAX_POINTERS;
		heap=new PointerDistanceHeapElement[heapSize];
		for(int i=0;i<heapSize;i++){
			heap[i]=new PointerDistanceHeapElement();
		}
		
		
	}
	
	public void initCalibration(){
		TouchDataApplication app=TouchDataApplication.getInstance();
		mXScale=app.screenWidth/(mRawPointerAxes.x.maxValue-mRawPointerAxes.x.minValue+1)/app.screenDensity;
		mYScale=app.screenHeight/(mRawPointerAxes.y.maxValue-mRawPointerAxes.y.minValue+1)/app.screenDensity;
		mXPrecision=1.0f/mXScale;
		mYPrecision=1.0f/mYScale;
		//TODO 这不科学
		mGeometricScale=(mXScale+mYScale)/2;
		Log.d("CAL","screen "+app.screenWidth+" "+app.screenHeight);
		Log.d("CAL","x y p g "+mXScale+" "+mYScale+" "+mXPrecision+" "+mYPrecision);
	}
	
	boolean syncTouch(long when){
		return false;
	}
	
	void sync(long when){
		//Sync touch state
		boolean havePointerIds=true;
		mCurrentRawPointerData.clear();
		havePointerIds=syncTouch(when);
		if(!havePointerIds){	//type A
			assignPointerIds();
		}
		//there is no touchpad|pointer devices to be processed here,thus we only have to deal with
		//the touchscreen.
		cookPointerData();
		//print
//		String s="";
//		for(int i=0;i<mCurrentCookedPointerData.pointerCount;i++){
//			float x=mCurrentCookedPointerData.pointerCoords[i].x;
//			float y=mCurrentCookedPointerData.pointerCoords[i].y;
//			int id=mCurrentCookedPointerData.pointerProperties[i].id;
//			s=s+"("+id+","+x+","+y+"),";
//		}
//		Log.d("COOKED", s);
		
		dispatchTouches(when);
	}
	
	void process(RawEvent rawEvent){
		if(rawEvent.type==InputManagerConstants.EV_SYN
				&&rawEvent.code==InputManagerConstants.SYN_REPORT){
			sync(rawEvent.when);
		}
	}
	
	/**
	 * swap the element in the heap
	 * @param i
	 * @param j
	 */
	void swap(int i,int j){
		PointerDistanceHeapElement t=heap[i];
		heap[i]=heap[j];
		heap[j]=t;
	}
	
	void assignPointerIds(){
		int currentPointerCount=mCurrentRawPointerData.pointerCount;
		int lastPointerCount=mLastRawPointerData.pointerCount;
		
		mCurrentRawPointerData.clearIdBits();
		if(currentPointerCount==0){
			//No pointers to assign
			return;
		}
		if(lastPointerCount==0){
			//all pointers are new
			for(int i=0;i<currentPointerCount;i++){
				int id=i;
				mCurrentRawPointerData.pointers[i].id=id;
				mCurrentRawPointerData.idToIndex[id]=i;
				mCurrentRawPointerData.markIdBit(id, mCurrentRawPointerData.isHovering(i));
			}
			return;
		}
		if(currentPointerCount==1&&lastPointerCount==1){
			//Only one pointer and no change in count so it must have
			//the same id as before
			int id=mLastRawPointerData.pointers[0].id;
			mCurrentRawPointerData.pointers[0].id=id;
			mCurrentRawPointerData.idToIndex[id]=0;
			mCurrentRawPointerData.markIdBit(id, mCurrentRawPointerData.isHovering(0));
		}
		
		//General case
		//build a heap
		int heapSize=0;
		for(int currentPointerIndex=0;currentPointerIndex<currentPointerCount;currentPointerIndex++){
			for(int lastPointerIndex=0;lastPointerIndex<lastPointerCount;lastPointerIndex++){
				Pointer currentPointer=mCurrentRawPointerData.pointers[currentPointerIndex];
				Pointer lastPointer=mLastRawPointerData.pointers[lastPointerIndex];
				if(currentPointer.toolType==lastPointer.toolType){
					long deltaX=currentPointer.x-lastPointer.x;
					long deltaY=currentPointer.x-lastPointer.x;
					long distance=deltaX*deltaX+deltaY*deltaY;
					//Insert to the heap
					heap[heapSize].currentPointerIndex=currentPointerIndex;
					heap[heapSize].lastPointerIndex=lastPointerIndex;
					heap[heapSize].distance=distance;
					heapSize+=1;
				}
			}
		}
		//Heapify:adjust the array to be a heap
		for(int startIndex=heapSize/2;startIndex!=0;){
			startIndex-=1;
			for(int parentIndex=startIndex;;){
				int childIndex=parentIndex*2+1;
				if(childIndex>=heapSize){
					break;
				}
				if(childIndex+1<heapSize&&heap[childIndex+1].distance<heap[childIndex].distance){
					childIndex+=1;
				}
				if(heap[parentIndex].distance<=heap[childIndex].distance){
					break;
				}
				swap(parentIndex,childIndex);
				parentIndex=childIndex;
			}
		}
		
		//Pull matches out by increasing order of distance
		matchedLastBits.clear();
		matchedCurrentBits.clear();
		usedIdBits.clear();
		boolean first=true;
		for(int i=Math.min(currentPointerCount, lastPointerCount);heapSize>0&&i>0;i--){
			while(heapSize>0){
				if(first){
					first=false;
				}else{
					// Previous iterations consumed the root element of the heap.
	                // Pop root element off of the heap (sift down).
					heap[0] = heap[heapSize];
					for(int parentIndex=0;;){
						int childIndex=parentIndex*2+1;
						if(childIndex>=heapSize){
							break;
						}
						if(childIndex+1<heapSize
								&&heap[childIndex+1].distance<heap[childIndex].distance){
							childIndex+=1;
						}
						if(heap[parentIndex].distance<=heap[childIndex].distance){
							break;
						}
						swap(parentIndex, childIndex);
						parentIndex=childIndex;
					}
				}
				heapSize-=1;
				int currentPointerIndex=heap[0].currentPointerIndex;
				if(matchedCurrentBits.hasBit(currentPointerIndex)) continue;	//already matched
				
				int lastPointerIndex=heap[0].lastPointerIndex;
				if(matchedLastBits.hasBit(lastPointerCount)) continue; //already matched
				
				matchedCurrentBits.markBit(currentPointerIndex);
				matchedLastBits.markBit(lastPointerIndex);
				
				int id=mLastRawPointerData.pointers[lastPointerIndex].id;
				mCurrentRawPointerData.pointers[currentPointerCount].id=id;
				mCurrentRawPointerData.idToIndex[id] = currentPointerIndex;
				mCurrentRawPointerData.markIdBit(id,
	                    mCurrentRawPointerData.isHovering(currentPointerIndex));
				usedIdBits.markBit(id);
				break;
			}
		}
		
		//Assign fresh ids to pointers that were not matched in the process.
		for(int i=currentPointerCount-matchedCurrentBits.count();i!=0;i--){
			int currentPointerIndex = matchedCurrentBits.markFirstUnmarkedBit();
			int id=usedIdBits.markFirstUnmarkedBit();
			mCurrentRawPointerData.pointers[currentPointerIndex].id=id;
			mCurrentRawPointerData.idToIndex[id] = currentPointerIndex;
			mCurrentRawPointerData.markIdBit(id,
					mCurrentRawPointerData.isHovering(currentPointerIndex));
			
			
		}
	}
	
	void cookPointerData(){
		int currentPointerCount=mCurrentRawPointerData.pointerCount;
		mCurrentCookedPointerData.clear();
		mCurrentCookedPointerData.pointerCount=currentPointerCount;
		
		mCurrentCookedPointerData.hoveringIdBits.value=mCurrentRawPointerData.hoveringIdBits.value;
		mCurrentCookedPointerData.touchingIdBits.value=mCurrentRawPointerData.touchingIdBits.value;
		
		//according to the value of RawPointerAxes, set set the 
		
		for(int i=0;i<currentPointerCount;i++){
			float touchMajor,touchMinor,toolMajor,toolMinor,size,
			x,y,pressure,orientation,tilt,distance;
			Pointer in=mCurrentRawPointerData.pointers[i];
			
			//Size
			touchMajor=in.touchMajor;
			touchMinor=in.touchMinor;
			toolMajor=in.toolMajor;
			toolMinor=in.toolMinor;
			touchMajor*=mGeometricScale;
			touchMinor*=mGeometricScale;
			toolMajor*=mGeometricScale;
			toolMajor*=mGeometricScale;
			size=(touchMajor+touchMinor)/2;
					
			//Pressure
			pressure=1;
			
			//Tilt and Orientation
			tilt=0;
			orientation=0;
			
			//Distance
			distance=0;
			
			//x y
			x=in.x*mXScale;
			y=in.y*mYScale;
			
			//write output coords
			PointerCoords out=mCurrentCookedPointerData.pointerCoords[i];
			out.clear();
			out.x=x;
			out.y=y;
			out.pressure=pressure;
			out.size=size;
			out.touchMajor=touchMajor;
			out.touchMinor=touchMinor;
			out.toolMajor=toolMajor;
			out.toolMinor=toolMinor;
			out.orientation=orientation;
			//TODO tilt distance
			
			PointerProperties properties=mCurrentCookedPointerData.pointerProperties[i];
			int id=in.id;
			properties.id=id;
			properties.toolType=in.toolType;
			mCurrentCookedPointerData.idToIndex[id]=i;
		}
	}
	
	public void dispatchTouches(long when){
		BitSet currentIdBits=mCurrentCookedPointerData.touchingIdBits;
		BitSet lastIdBits=mLastCookedPointerData.touchingIdBits;
		if(currentIdBits.equals(lastIdBits)){
			if(!currentIdBits.isEmpty()){
				//TODO
			}
		}else{
			BitSet upIdBits=new BitSet(lastIdBits.value&~currentIdBits.value);
			BitSet downIdBits=new BitSet(currentIdBits.value&~lastIdBits.value);
			BitSet moveIdBits=new BitSet(lastIdBits.value&currentIdBits.value);
			BitSet dispatchedIdBits=new BitSet(lastIdBits.value);
			
			boolean moveNeeded = updateMovedPointers(
					mCurrentCookedPointerData.pointerProperties,
					mCurrentCookedPointerData.pointerCoords,
					mCurrentCookedPointerData.idToIndex,
					mLastCookedPointerData.pointerProperties,
					mLastCookedPointerData.pointerCoords,
					mLastCookedPointerData.idToIndex, moveIdBits);
			
			// Dispatch pointer up events.
			while(!upIdBits.isEmpty()){
				
			}
			
			// Dispatch move events
			
			// Dispatch pointer down events using the new pointer locations.
		}
		
	}
	
	
	boolean updateMovedPointers(
			PointerProperties inProperties[],
			PointerCoords inCoords[],
			int[] inIdToIndex,
			PointerProperties outProperties[],
			PointerCoords outCoords[],
			int[] outIdToIndex,
			BitSet idBits){
		boolean changed=false;
		while(!idBits.isEmpty()){
			int id=idBits.clearFirstMarkedBit();
			int inIndex=inIdToIndex[id];
			int outIndex=outIdToIndex[id];
			PointerProperties curInProperties=inProperties[inIndex];
			PointerCoords curInCoords=inCoords[inIndex];
			PointerProperties curOutProperties=outProperties[outIndex];
			PointerCoords curOutCoords=outCoords[outIndex];
			if(!curInProperties.equals(curOutProperties)){
				curOutProperties.copyFrom(curInProperties);
				changed = true;
			}
			if (!curInCoords.equals(curOutCoords)) {
	            curOutCoords.copyFrom(curInCoords);
	            changed = true;
	        }
		}
		
		return changed;
	}
	
	public void dispatchMotion(){
		//TODO
	}
	
	public float avg(float x,float y){
		return (x+y)/2;
	}
	
	/*
	 * We set the value by hand now
	 */
	public void configureRawPointerAxes(){
		mRawPointerAxes=new RawPointerAxes();
		mRawPointerAxes.clear();
		
		mRawPointerAxes=new RawPointerAxes();
		
		mRawPointerAxes.trackingId.valid=false;
		
		mRawPointerAxes.touchMajor.valid=true;
		mRawPointerAxes.touchMajor.maxValue=255;
		
		mRawPointerAxes.toolMajor.valid=true;
		mRawPointerAxes.toolMajor.maxValue=20;
		
		mRawPointerAxes.x.valid=true;
		mRawPointerAxes.x.minValue=0;
		mRawPointerAxes.x.maxValue=1023;
		
		mRawPointerAxes.y.valid=true;
		mRawPointerAxes.y.minValue=0;
//		mRawPointerAxes.y.maxValue=946;
		mRawPointerAxes.y.maxValue=599;
		
		mRawPointerAxes.pressure.valid=true;
		mRawPointerAxes.pressure.maxValue=255;
	}
	
	
	public static class PointerDistanceHeapElement{
		public int currentPointerIndex;
		public int lastPointerIndex;
		public long distance;
	}
	
	
	/* Raw axis information from the driver.
	 * We can save these information to a
	 * file and read the datas.
	 **/
	public static class RawPointerAxes{
		RawAbsoluteAxisInfo x;
		RawAbsoluteAxisInfo y;
		RawAbsoluteAxisInfo pressure;
		RawAbsoluteAxisInfo touchMajor;
		RawAbsoluteAxisInfo touchMinor;
		RawAbsoluteAxisInfo toolMajor;
		RawAbsoluteAxisInfo toolMinor;
		RawAbsoluteAxisInfo orientation;
		RawAbsoluteAxisInfo distance;
		RawAbsoluteAxisInfo tiltX;
		RawAbsoluteAxisInfo tiltY;
		RawAbsoluteAxisInfo trackingId;
		RawAbsoluteAxisInfo slot;
		
		public RawPointerAxes(){
			x=new RawAbsoluteAxisInfo();
			y=new RawAbsoluteAxisInfo();
			pressure=new RawAbsoluteAxisInfo();
			touchMajor=new RawAbsoluteAxisInfo();
			touchMinor=new RawAbsoluteAxisInfo();
			toolMajor=new RawAbsoluteAxisInfo();
			toolMinor=new RawAbsoluteAxisInfo();
			orientation=new RawAbsoluteAxisInfo();
			distance=new RawAbsoluteAxisInfo();
			tiltX=new RawAbsoluteAxisInfo();
			tiltY=new RawAbsoluteAxisInfo();
			trackingId=new RawAbsoluteAxisInfo();
			slot=new RawAbsoluteAxisInfo();
		}
		
		void clear(){
			x.clear();
			y.clear();
			pressure.clear();
			touchMajor.clear();
			touchMinor.clear();
			toolMajor.clear();
			toolMinor.clear();
			orientation.clear();
			distance.clear();
			tiltX.clear();
			tiltY.clear();
			trackingId.clear();
			slot.clear();
		}
	}
	
	public static class RawAbsoluteAxisInfo{
		boolean valid;
		int minValue;
		int maxValue;
		int flat;
		int fuzz;
		int resolution;
		
		public void clear(){
			valid=false;
			minValue=0;
			maxValue=0;
			flat=0;
			fuzz=0;
			resolution=0;
		}
	}
	
	public static class Calibration{
		// Size
        enum SizeCalibration {
            SIZE_CALIBRATION_DEFAULT,
            SIZE_CALIBRATION_NONE,
            SIZE_CALIBRATION_GEOMETRIC,
            SIZE_CALIBRATION_DIAMETER,
            SIZE_CALIBRATION_AREA,
        }

        SizeCalibration sizeCalibration;

        bool haveSizeScale;
        float sizeScale;
        bool haveSizeBias;
        float sizeBias;
        bool haveSizeIsSummed;
        bool sizeIsSummed;

        // Pressure
        enum PressureCalibration {
            PRESSURE_CALIBRATION_DEFAULT,
            PRESSURE_CALIBRATION_NONE,
            PRESSURE_CALIBRATION_PHYSICAL,
            PRESSURE_CALIBRATION_AMPLITUDE,
        }

        PressureCalibration pressureCalibration;
        bool havePressureScale;
        float pressureScale;

        // Orientation
        enum OrientationCalibration {
            ORIENTATION_CALIBRATION_DEFAULT,
            ORIENTATION_CALIBRATION_NONE,
            ORIENTATION_CALIBRATION_INTERPOLATED,
            ORIENTATION_CALIBRATION_VECTOR,
        }

        OrientationCalibration orientationCalibration;

        // Distance
        enum DistanceCalibration {
            DISTANCE_CALIBRATION_DEFAULT,
            DISTANCE_CALIBRATION_NONE,
            DISTANCE_CALIBRATION_SCALED,
        }

        DistanceCalibration distanceCalibration;
        bool haveDistanceScale;
        float distanceScale;
	}
	
	
	
}
