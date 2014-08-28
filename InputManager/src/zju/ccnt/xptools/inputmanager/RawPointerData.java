package zju.ccnt.xptools.inputmanager;
/**
 * Raw data for a collection of pointers including a 
 * pointer id mapping table
 * 
 * @author zhongjinwen
 *
 */
public class RawPointerData {
	public static class Cord{
		float x;
		float y;
	}
	public static class Pointer{
		int id;
		int x;
		int y;
		int pressure;
		int touchMajor;
		int touchMinor;
		int toolMajor;
		int toolMinor;
		int orientation;
		int distance;
		int tiltX;
		int tiltY;
		int toolType;
		boolean isHovering;
		Pointer(){
			isHovering=false;
			pressure=0;
		}
		public void copyFrom(Pointer other){
			id=other.id;
			x=other.x;
			y=other.y;
			pressure=other.pressure;
			touchMajor=other.touchMajor;
			touchMinor=other.touchMinor;
			toolMajor=other.toolMajor;
			toolMinor=other.toolMinor;
			orientation=other.orientation;
			distance=other.distance;
			tiltX=other.tiltX;
			tiltY=other.tiltY;
			toolType=other.toolType;
		}
	};
	
	int pointerCount;
	Pointer pointers[];
	BitSet hoveringIdBits;
	BitSet touchingIdBits;
	int idToIndex[];
	
	public RawPointerData() {
		pointers=new Pointer[InputManagerConstants.MAX_POINTERS];
		for(int i=0;i<pointers.length;i++){
			pointers[i]=new Pointer();
		}
		hoveringIdBits=new BitSet();
		touchingIdBits=new BitSet();
		idToIndex=new int[InputManagerConstants.MAX_POINTER_ID+1];
		clear();
	}
	void clear(){
		pointerCount=0;
		clearIdBits();
	}
	void copyFrom(RawPointerData other){
		pointerCount=other.pointerCount;
//		hoveringIdBits=other.hoveringIdBits;
		hoveringIdBits.value=other.hoveringIdBits.value;
		for(int i=0;i<pointerCount;i++){
//			pointers[i]=other.pointers[i];
			pointers[i].copyFrom(other.pointers[i]);
			int id=pointers[i].id;
//			idToIndex[id]=other.idToIndex[id];
			System.arraycopy(other.idToIndex, 0, idToIndex, 0, idToIndex.length);
		}
	}
	/**
	 * Caculate the centroid of touching pointers.
	 * @param c
	 */
	void getCentroidOfTouchingPointers(Cord c){
		float x=0,y=0;
		int count=touchingIdBits.count();
		if(count>0){
			BitSet idBits=new BitSet(touchingIdBits.count());
			for(;!idBits.isEmpty();){
				int id=idBits.clearFirstMarkedBit();
				Pointer pointer=pointerForId(id);
				x+=pointer.x;
				y+=pointer.y;
			}
			x/=count;
			y/=count;
		}
		c.x=x;c.y=y;
	}
	
	void markIdBit(int id,boolean isHovering){
		if(isHovering){
			hoveringIdBits.markBit(id);
		}else {
			touchingIdBits.markBit(id);
		}
	}
	void clearIdBits(){
		hoveringIdBits.clear();
		touchingIdBits.clear();
	}
	Pointer pointerForId(int id){
		return pointers[idToIndex[id]];
	}
	
	boolean isHovering(int pointerIndex){
		return pointers[pointerIndex].isHovering;
	}

}
