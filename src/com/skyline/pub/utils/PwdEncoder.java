package com.skyline.pub.utils;

public interface PwdEncoder {
	/**
	 * 编码密码
	 * @param rawPass
	 * @return
	 */
	public String encodePassword(String rawPass);

	/**
	 * 判断密码是否合法
     * @param encPass
	 * @param rawPass
	 * @return
	 */
	public boolean isPasswordValid(String encPass, String rawPass);
}
