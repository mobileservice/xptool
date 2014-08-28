//package zju.ccnt.xptools.inputmanager;
//
//public class PointerCoords {
//	private static final int INITIAL_PACKED_AXIS_VALUES=8;
//	private long mPackedAxisBits;
//	private float[] mPackedAxisValues;
//	public static final int AXIS_X=0;
//	public static final int AXIS_Y=1;
//	public static final int AXIS_PRESSURE=2;
//	public static final int AXIS_SIZE=3;
//	public static final int AXIS_TOUCH_MAJOR=4;
//	public static final int AXIS_TOUCH_MINOR=5;
//	public static final int AXIS_TOOL_MAJOR=6;
//	public static final int AXIS_TOOL_MINOR=7;
//	public static final int AXIS_ORIENTATION=8;
////	public static final int AXIS_VSCROLL=9;
////	public static final int AXIS_HSCROLL=10;
////	public static final int AXIS_ORIENTATION=8;
//	
//	public PointerCoords(){
//	}
//	
//	public PointerCoords(PointerCoords other){
//		copyFrom(other);
//	}
//	
//	public static PointerCoords[] createArray(int size){
//		PointerCoords[] array=new PointerCoords[size];
//		for(int i=0;i<size;i++){
//			array[i]=new PointerCoords();
//		}
//		return array;
//	}
//	
//
//	public float x;
//	public float y;
//	public float pressure;
//	public float size;
//	public float touchMajor;
//	public float touchMinor;
//	public float toolMajor;
//	public float toolMinor;
//	public float orientation;
//	
//	public void clear(){
//		mPackedAxisBits=0;
//		x=0;
//		pressure=0;
//		size=0;
//		touchMajor=0;
//		touchMinor=0;
//		toolMajor=0;
//		toolMinor=0;
//		orientation=0;
//	}
//	
//	public void copyFrom(PointerCoords other){
//		final long bits=other.mPackedAxisBits;
//		mPackedAxisBits=bits;
//		if(bits!=0){
//			final float[] otherValues=other.mPackedAxisValues;
//			final int count=Long.bitCount(bits);
//			float[] values=mPackedAxisValues;
//			if(values==null||count>values.length){
//				values=new float[otherValues.length];
//				mPackedAxisValues=values;
//			}
//			System.arraycopy(other, 0, values, 0, count);
//		}
//		x=other.x;
//		y=other.y;
//		pressure=other.pressure;
//		size=other.size;
//		touchMajor=other.touchMajor;
//		touchMinor=other.touchMinor;
//		toolMajor=other.toolMajor;
//	}
//	
//	public float getAxisValue(int axis) {
//		switch (axis) {
//		case AXIS_X:
//			return x;
//		case AXIS_Y:
//			return y;
//		case AXIS_PRESSURE:
//			return pressure;
//		case AXIS_SIZE:
//			return size;
//		case AXIS_TOUCH_MAJOR:
//			return toolMajor;
//		case AXIS_TOUCH_MINOR:
//			return toolMinor;
//		case AXIS_TOOL_MAJOR:
//			return toolMajor;
//		case AXIS_TOOL_MINOR:
//			return toolMinor;
//		case AXIS_ORIENTATION:
//			return orientation;
//		default: {
//			if (axis < 0 || axis > 63) {
//				throw new IllegalArgumentException("Axis out of range.");
//			}
//			final long bits = mPackedAxisBits;
//			final long axisBit = 1L << axis;
//			if ((bits & axisBit) == 0) {
//				return 0;
//			}
//			final int index = Long.bitCount(bits & (axisBit - 1L));
//			return mPackedAxisValues[index];
//		}
//		}
//	}
//	
//	public void setAxisValue(int axis,float value){
//		switch(axis){
//		case AXIS_X:
//			x=value;
//			break;
//		case AXIS_Y:
//			y=value;
//			break;
//		case AXIS_PRESSURE:
//			pressure=value;
//			break;
//		case AXIS_SIZE:
//			size=value;
//			break;
//		case AXIS_TOUCH_MAJOR:
//			touchMajor=value;
//			break;
//		case AXIS_TOUCH_MINOR:
//			touchMinor=value;
//			break;
//		case AXIS_TOOL_MAJOR:
//			toolMajor=value;
//			break;
//		case AXIS_TOOL_MINOR:
//			toolMinor=value;
//			break;
//		case AXIS_ORIENTATION:
//			orientation=value;
//			break;
//		default:{
//			if (axis < 0 || axis > 63) {
//				throw new IllegalArgumentException("Axis out of range.");
//			}
//			final long bits=mPackedAxisBits;
//			final long axisBit=1L<<axis;
//			final int index = Long.bitCount(bits & (axisBit - 1L));
//			float[] values=mPackedAxisValues;
//			if((bits&axisBit)==0){
//				if(values==null){
//					values=new float[INITIAL_PACKED_AXIS_VALUES];
//					mPackedAxisValues=values;
//				}else{
//					final int count=Long.bitCount(bits);
//					if(count<values.length){
//						if(index!=count){
//							System.arraycopy(values, index, values, index+1, count-index);
//						}
//					}else{
//						float[] newValues=new float[count*2];
//						System.arraycopy(values, 0, newValues, 0, index);
//						System.arraycopy(values, index, newValues, index+1, count-index);
//						values=newValues;
//						mPackedAxisValues=values;
//					}
//				}
//				mPackedAxisBits=bits|axisBit;
//			}
//			values[index]=value;
//		}
//		}
//	}
//	
//}
