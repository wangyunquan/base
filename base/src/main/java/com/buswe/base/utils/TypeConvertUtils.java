package com.buswe.base.utils;

public class TypeConvertUtils
{
  public static final String byteToHex(byte[] b)
  {
    StringBuilder stringBuilder = new StringBuilder();
    if ((b == null) || (b.length <= 0)) {
      return null;
    }
    for (int i = 0; i < b.length; i++)
    {
      int v = b[i] & 0xFF;
      String hv = Integer.toHexString(v);
      if (hv.length() < 2) {
        stringBuilder.append(0);
      }
      stringBuilder.append(hv);
    }
    return stringBuilder.toString();
  }
}
