package com.github.androidtools;

import java.security.MessageDigest;

/**
 * Created by Administrator on 2017/6/23.
 */

public class MD5 {
    public static final String getMessageDigest(byte[] buffer) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(buffer);
            byte[] md = e.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;

            for(int i = 0; i < j; ++i) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }
            return new String(str);
        } catch (Exception var9) {
            return null;
        }
    }
}
