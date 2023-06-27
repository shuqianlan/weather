//package com.dahua.opendms.filter;
package com.ilifesmart.utils;
//import com.dahua.opendms.util.Base64;

import android.util.Base64;
import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author 308581
 */
public class LeCameraUtils {
    private static final Logger log = Logger.getLogger(LeCameraUtils.class.getName());
    private static final String deviceId = "9B02B1EYAZ99660";
    private static final String appsecret = "011f6e7ee22841438c4c54f353040a";
    private static byte[] hex = new byte[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static void main(String[] args) throws UnsupportedEncodingException {
        check();
    }

//    {SN:9B02B1EYAZ99660,SC:L227757A,PID:qvznHjxj}
    public static void check() throws UnsupportedEncodingException {
        checkIsMatch("L227757A", "XL9jg9suUiTtReth4DzHgg==");
    }

    public static void checkIsMatch(final String srcPassword, final String devicePassword) throws UnsupportedEncodingException {
        String md5A = md5(appsecret);
        log.info(md5A);
        String kdf1 = base64Encode(kdf(md5A)).toUpperCase();
        log.info(kdf1);
        String key = md5Encode(kdf1).toLowerCase();
        byte[] iv = toBinaryArray(md5A);

        try {
            byte[] _final = encryptCBC(srcPassword, key, iv);
            String encryptStr = new String(_final);
            String encryPassword = base64Encode(_final);
            System.out.println(encryPassword);

            Log.d("BBBB", "checkIsMatch: isMatch " + encryPassword.equals(devicePassword));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static byte[] getIv(byte[] key) throws Exception {
        byte[] bytes = key;
        int l = 16;
        if (l == bytes.length) {
            return bytes;
        }
        return Arrays.copyOf(bytes, l);
    }

    private static SecretKey getSecretKey(String key) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        keyGenerator.init(128, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }

    public static byte[] encryptCBC(String text, String key, byte[] iv) throws Exception {
        // SecretKey secretKey = getSecretKey(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipher.doFinal(text.getBytes("UTF-8"));
    }

    public static byte[] toBinaryArray(final String kk) throws UnsupportedEncodingException {
        return kk.getBytes("ISO-8859-1");
    }

    public static String base64Encode(final byte[] src) {
        return new String(com.ilifesmart.utils.Base64.encode(src));
    }

    public static String md5(final String src) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bs = digest.digest(src.getBytes());

            return new String(bs,"ISO-8859-1");
        } catch (Exception e) {
        }

        return sb.toString();
    }

    public static String md5Encode(final String src) {
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bs = digest.digest(src.getBytes());
            return convertToHexString(bs);
        } catch (Exception e) {
        }

        return sb.toString();
    }

    public static byte[] kdf(final String md5AppSecret) throws UnsupportedEncodingException {
        KeySpec keySpec = new PBEKeySpec(deviceId.toCharArray(), md5AppSecret.getBytes("ISO-8859-1"), 1200, 256);

        byte[] result = "".getBytes();
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] key = factory.generateSecret(keySpec).getEncoded();
            result = key;
        } catch (Exception ex) {
        }

        return result;
    }

    private static String convertToHexString(byte[] digests) {
        byte[] md5String = new byte[digests.length * 2];

        int index = 0;
        for (byte digest : digests) {
            md5String[index] = hex[(digest >> 4) & 0x0F];
            md5String[index + 1] = hex[digest & 0x0F];
            index += 2;
        }

        return new String(md5String);
    }


}
