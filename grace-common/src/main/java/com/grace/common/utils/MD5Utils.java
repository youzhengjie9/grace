package com.grace.common.utils;

import com.grace.common.constant.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具
 *
 * @author nacos
 */
@SuppressWarnings("PMD.ClassNamingShouldBeCamelRule")
public class MD5Utils {

    private MD5Utils() {
    }
    
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};
    
    private static final ThreadLocal<MessageDigest> MESSAGE_DIGEST_LOCAL = ThreadLocal.withInitial(() -> {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    });

    public static String md5Hex(byte[] bytes) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MESSAGE_DIGEST_LOCAL.get();
            if (messageDigest != null) {
                return encodeHexString(messageDigest.digest(bytes));
            }
            throw new NoSuchAlgorithmException("MessageDigest get MD5 instance error");
        } finally {
            MESSAGE_DIGEST_LOCAL.remove();
        }
    }
    
    /**
     * 将内容先进行md5加密,再转换成16进制字符串（推荐使用这个方法！）
     *
     * @param content  需要加密的内容
     * @param encode 编码（比如 UTF-8 ）
     * @return MD5 hex string of input
     */
    public static String md5Hex(String content, String encode) {
        try {
            return md5Hex(content.getBytes(encode));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Convert a byte array into a visible string.
     */
    public static String encodeHexString(byte[] bytes) {
        int l = bytes.length;
        
        char[] out = new char[l << 1];
        
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & bytes[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & bytes[i]];
        }
        
        return new String(out);
    }

    public static void main(String[] args) {

        String str1 = "a";
        String str2 = "abcde";
        String str3 = "你好asdfghjkqwerty";

        System.out.println(md5Hex(str1, Constants.ENCODE));
        System.out.println(md5Hex(str2, Constants.ENCODE));
        System.out.println(md5Hex(str3, Constants.ENCODE));

    }
    
}
