package ceshi.handover.scinan.com.huishoubaobigrecycling.utils;

import java.text.DecimalFormat;

/**
 * 数据解析
 */
public class DataConvertUtilMy {

    public DataConvertUtilMy() {
    }

    public static byte[] intToBytes(int n) {
        String s = String.valueOf(n);
        return s.getBytes();
    }

    public static int bytesToInt(byte[] b) {
        String s = new String(b);
        return Integer.parseInt(s);
    }

    public static int bytesToInt(byte b1, byte b2) {
        return b1 & 255 | (b2 & 255) << 8;
    }

    public static int byte2int(byte[] res) {
        int targets = res[0] & 255 | res[1] << 8 & '\uff00' | res[2] << 24 >>> 8 | res[3] << 24;
        return targets;
    }

    public static String byte2HexStr(byte[] btyes) {
        String temp = "";
        StringBuilder sb = new StringBuilder("");

        for (int n = 0; n < btyes.length; ++n) {
            temp = Integer.toHexString(btyes[n] & 255);
            sb.append(temp.length() == 1 ? "0" + temp : temp);
            sb.append(" ");
        }

        return sb.toString().toUpperCase().trim();
    }

    public static byte[] hexStrtoBytes(String str) {
        if (str != null && !str.trim().equals("")) {
            str = str.trim();
            byte[] bytes = new byte[str.length() / 2];

            for (int i = 0; i < str.length() / 2; ++i) {
                String subStr = str.substring(i * 2, i * 2 + 2);
                bytes[i] = (byte) Integer.parseInt(subStr, 16);
            }

            return bytes;
        } else {
            return new byte[0];
        }
    }

    public static byte[] getCopyByte(byte[] bytes) {
        if (null != bytes && 0 != bytes.length) {
            int length = getValidLength(bytes);
            byte[] bb = new byte[length];
            System.arraycopy(bytes, 0, bb, 0, length);
            return bb;
        } else {
            return new byte[1];
        }
    }

    public static int getValidLength(byte[] bytes) {
        int i = 0;
        if (null != bytes && 0 != bytes.length) {
            while (i < bytes.length && bytes[i] != 0) {
                ++i;
            }

            return i + 1;
        } else {
            return i;
        }
    }

    public static String hexStringToString(String s) {
        if (s != null && !s.equals("")) {
            s = s.replace(" ", "");
            byte[] baKeyword = new byte[s.length() / 2];

            for (int i = 0; i < baKeyword.length; ++i) {
                try {
                    baKeyword[i] = (byte) (255 & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
                } catch (Exception var5) {
                    var5.printStackTrace();
                }
            }

            try {
                s = new String(baKeyword, "gbk");
                new String();
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return s;
        } else {
            return null;
        }
    }

    public static String formatPrice(int a, int b) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format((double) ((float) a / (float) b));
    }

    public static String formatAmount(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(amount);
    }

    public static byte[] intToBytesBig(int value) {
        byte[] src = new byte[]{(byte) (value >> 24 & 255), (byte) (value >> 16 & 255), (byte) (value >> 8 & 255), (byte) (value & 255)};
        return src;
    }

    public static byte[] intToBytesLittle(int value) {
        byte[] src = new byte[]{(byte) (value & 255), (byte) (value >> 8 & 255), (byte) (value >> 16 & 255), (byte) (value >> 24 & 255)};
        return src;
    }

    public static int bytesToIntBig(byte[] src, int offset) {
        int value = (src[offset] & 255) << 24 | (src[offset + 1] & 255) << 16 | (src[offset + 2] & 255) << 8 | src[offset + 3] & 255;
        return value;
    }

    /**
     * 测距模块
     *
     * @param src
     * @param offset
     * @return
     */
    public static int bytesToIntLittle(byte[] src, int offset) {
//        int value = src[offset] & 255 | (src[offset + 1] & 255) << 8 | (src[offset + 2] & 255) << 16 | (src[offset + 3] & 255) << 24;
        int value = src[offset] & 255 << 24 | (src[offset + 1] & 255) << 16 | (src[offset + 2] & 255) << 8 | src[offset + 3] & 255;
        return value;
    }

    /**
     * 解析计数
     *
     * @param src
     * @param offset
     * @return
     */
    public static int bytesToIntLittleCopy(byte[] src, int offset) {
        int value = (src[offset] & 255) << 24 | (src[offset + 1] & 255) << 16 | (src[offset + 2] & 255) << 8 | (src[offset + 3] & 255) << 0;
        return value;
    }

    /**
     * 称重
     *
     * @param src
     * @param offset
     * @return
     */

    public static int bytesToIntLittleCopy1(byte[] src, int offset) {
        //72 0E 00 4F CF 00 00 04 74 1F 00 00 1B 9D
        int value = 0x00 << 24 | (src[offset] & 255) << 16 | (src[offset + 1] & 255) << 8 | (src[offset + 2] & 255) << 0;
        return value;
    }

    /**
     * 门类型
     */
    public static int bytesToIntLittlType(byte[] src, int offset) {
        int value = 0x00 << 24 | (src[offset] & 255);
        return value;
    }

    /**
     * 固件版本
     *
     * @param src
     * @param offset
     * @return
     */
    public static String bytesToIntLittleCopy2(byte[] src, int offset) {
        String a = src[0] + ".";
        String b = src[1] + ".";
        String c = src[2] + ".";
        String d = src[3] + "";
        String value = a + b + c + d;
        return value;
    }

    public static int bytesToIntLittleX(byte[] src, int offset) {
        int value = src[offset] & 255 | (src[offset + 1] & 255) << 8 | (src[offset + 2] & 255) << 16 | (src[offset + 3] & 127) << 24;
        if ((src[offset + 3] & 128) != 0) {
            value = -value;
        }

        return value;
    }
}
