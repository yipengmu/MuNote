/**
 * 
 */
package com.laomu.note.ui.util;

/**
 * @author luoyuan.myp
 * 
 *         2014-4-22
 */
public class Base64 {
	public static final String BASE64CODE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
	public static final int SPLIT_LINES_AT = 76;
	private static byte[] arrayOfByte2;

	public static String encode(String paramString)
  {
    String str = "";
    int i = 0;
    try
    {
      byte[] arrayOfByte1 = paramString.getBytes("UTF-8");
      arrayOfByte2 = arrayOfByte1;
      i = (3 - arrayOfByte2.length % 3) % 3;
      arrayOfByte2 = zeroPad(i + arrayOfByte2.length, arrayOfByte2);
      for (int k = 0; k < arrayOfByte2.length; k += 3)
      {
        int j = (arrayOfByte2[k] << 16) + (arrayOfByte2[(k + 1)] << 8) + arrayOfByte2[(k + 2)];
        str = str + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(0x3F & j >> 18) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(0x3F & j >> 12) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(0x3F & j >> 6) + "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(j & 0x3F);
      }
    }
    catch (Exception localException)
    {
      arrayOfByte2 = paramString.getBytes();
    }
    return splitLines(str.substring(0, str.length() - i) + "==".substring(0, i)).trim();
  }

	public static String splitLines(String paramString) {
		String str = "";
		for (int i = 0;; i += 76) {
			if (i >= paramString.length())
				return str;
			str = str + paramString.substring(i, Math.min(paramString.length(), i + 76));
			str = str + "\r\n";
		}
	}

	public static byte[] zeroPad(int paramInt, byte[] paramArrayOfByte) {
		byte[] arrayOfByte = new byte[paramInt];
		System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
		return arrayOfByte;
	}
}