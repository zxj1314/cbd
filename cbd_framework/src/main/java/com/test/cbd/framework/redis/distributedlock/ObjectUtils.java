package com.test.cbd.framework.redis.distributedlock;

/**
 * @author lixiaohui
 * @date 2016年9月19日 下午9:53:16
 * 
 */
public class ObjectUtils {
	
	public static void requireNonNull(Object... obj) {
		if (obj.length % 2 != 0) {
			throw new IllegalArgumentException("arguments's length mod 2 must be 0.");
		}
		
		for (int i = 0; i < obj.length; i += 2) {
			if (obj[i] == null) {
				throw new NullPointerException(obj[i + 1].toString());
			}
		}
	}
	
	public static <T>  T requiredNonNull(T object) {
		if (object == null) {
			throw new NullPointerException();
		}
		return object;
	}
	
}
