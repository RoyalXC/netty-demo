package com.example.xxc.nettydemo.utils;

/**
 * 功能描述：
 *
 * @author: 薛行晨(RoyalXC)
 * @date: 2020/8/21 17:48
 */
public class ByteUtil {

    public static byte[] intToByte(int data) {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (data >> 24);
        bytes[2] = (byte) (data >> 16);
        bytes[1] = (byte) (data >> 8);
        bytes[0] = (byte) data;

        return bytes;
    }

    public static int byteToInt(byte[] data, int offset) {
        int int1 = data[offset] & 0xff;
        int int2 = (data[1 + offset] & 0xff) << 8;
        int int3 = (data[2 + offset] & 0xff) << 16;
        int int4 = (data[3 + offset] & 0xff) << 24;

        return int1 | int2 | int3 | int4;
    }

    public static byte[] concat(byte[] bytes1, byte[] bytes2) {

        byte[] newBytes = new byte[bytes1.length + bytes2.length];

        int index = 0;

        for (byte b : bytes1) {
            newBytes[index] = b;
            index++;
        }

        for (byte b : bytes2) {
            newBytes[index] = b;
            index++;
        }

        return newBytes;
    }

}
