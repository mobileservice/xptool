/********************************************************************************
 * Copyright (c) 2014 CCNT
 * Version: 1.0
 * Author:zhongjinwen
 * Modified Time:2014-6-5 
 *******************************************************************************/
package zju.ccnt.xptools.inputmanager;

/**
 * The Class BitOptUtils. 
 */
public class BitOptUtils {
	
	public static int numbits_lookup_table[]={
			    0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2,
			    3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3,
			    3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3,
			    4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4,
			    3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5,
			    6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4,
			    4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5,
			    6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5,
			    3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3,
			    4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6,
			    6, 7, 6, 7, 7, 8
	};
	
	
	/**
	 * Caculate the number of bit '1' in the int value
	 *
	 * @param value 
	 * @return the number of bit '1'
	 */
	public static int __builtin_popcount(int value){
		int res=0;
		res=numbits_lookup_table[value&0xff];
		res+=numbits_lookup_table[value>>8&0xff];
		res+=numbits_lookup_table[value>>16&0xff];
		res+=numbits_lookup_table[value>>24&0xff];
		return res;
	}
	
	/**
	 * Caculate the leading count of bit '0'
	 *
	 * @param value 
	 * @return the number of leading '0'
	 */
	public static int __builtin_clz(int value){
		//TODO not a good implementataion
		for(int i=0;i<32;i++){
			if((value&0x80000000)!=0){
				return i;
			}
			value=value<<1;
		}
		return 32;
	}
	
	/**
	 * Caculate the count of bit '0' of the tail
	 *
	 * @param value 
	 * @return the number of tailing '0'
	 */
	public static int __builtin_ctz(int value){
		//TODO not a good implementataion
		for(int i=0;i<32;i++){
			if((value&1)!=0)
				return i;
			value=value>>1;
		}
		return 32;
	}
	
	
	
	
}
