package zju.ccnt.xptools.inputmanager;

public class BitSet {
	int value;
	BitSet(){value=0;}
	BitSet(int value){this.value=value;}
	public static int valueForBit(int n){return 0x80000000>>n;}
	public void clear(){value=0;}
	int count(){return BitOptUtils.__builtin_popcount(value);}
	boolean isEmpty(){return value==0?true:false;}
	boolean isFull(){return value==0xfffffff?true:false;}
	boolean hasBit(int n){return (value&valueForBit(n))==0?false:true;}
	void markBit(int n){value|=valueForBit(n);}
	void clearBit(int n){value&=~valueForBit(n);}
	int firstMarkedBit(){return BitOptUtils.__builtin_clz(value);}
	int firstUnmarkedBit(){return BitOptUtils.__builtin_clz(~value);}
	int lastMarkedBit(){return 31-BitOptUtils.__builtin_ctz(value);}
	int clearFirstMarkedBit(){
		int n=firstMarkedBit();
		clearBit(n);
		return n;
	}
	int markFirstUnmarkedBit(){
		int n=firstUnmarkedBit();
		markBit(n);
		return n;
	}
	
	int clearLastMarkedBit(){
		int n=lastMarkedBit();
		clearBit(n);
		return n;
	}
	
	int getIndexOfBit(int n){
		return BitOptUtils.__builtin_popcount(value&(0x80000000>>(n-1)));
	}
	
	public boolean equals(BitSet bs){
		return value==bs.value;
	}
	
	public boolean nequals(BitSet bs){
		return value!=bs.value;
	}
}
