package util;

import org.apache.tomcat.util.codec.binary.Base64;


public class Base64Utils {
    public static String encodeBase64(String input) {
        if(input == null) return null;
        byte[] bytes;
        bytes = Base64.encodeBase64(input.getBytes());
        bytes[bytes.length-1] = (byte)(bytes[bytes.length-1] ^ (int)bytes[bytes.length-2]);
        bytes[bytes.length-2] = (byte)(bytes[bytes.length-1] ^ (int)bytes[bytes.length-2]);
        bytes[bytes.length-1] = (byte)(bytes[bytes.length-1] ^ (int)bytes[bytes.length-2]);
        return new String(bytes);
    }
    public static String decodeBase64(String input) {
        if(input == null) return null;
        byte[] bytes;
        bytes = input.getBytes();
        bytes[bytes.length-1] = (byte)(bytes[bytes.length-1] ^ (int)bytes[bytes.length-2]);
        bytes[bytes.length-2] = (byte)(bytes[bytes.length-1] ^ (int)bytes[bytes.length-2]);
        bytes[bytes.length-1] = (byte)(bytes[bytes.length-1] ^ (int)bytes[bytes.length-2]);
       return new String(Base64.decodeBase64(bytes));
    }
}
