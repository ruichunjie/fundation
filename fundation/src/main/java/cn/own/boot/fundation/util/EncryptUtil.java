package cn.own.boot.fundation.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @ClassName: EncryptUtil
 * @Description: 加密算法工具集
 * @Author: xinYue
 * @Date: 2019-10-04 12:32
 * @Version 1.0
 **/
public class EncryptUtil {

    private static final String ENCRYPT_ALGORITHM = "AES";
    private static final String CIPHER_MODE = "AES/ECB/PKCS5Padding";
    private static final String CHARACTER = "UTF-8";
    private static final int PWD_SIZE = 16;

    private static byte[] pwdHandler(String password) throws UnsupportedEncodingException {

        byte[] data;
        if (password == null){
            password = "";
        }
        StringBuffer sb = new StringBuffer(PWD_SIZE);
        sb.append(password);
        while (sb.length() < PWD_SIZE) {
            sb.append("0");
        }
        if (sb.length() > PWD_SIZE){
            sb.setLength(PWD_SIZE);
        }
        data = sb.toString().getBytes(CHARACTER);
        return data;

    }

    public static byte[] encrypt(byte[] clearTextBytes, byte[] pwdBytes) {

        try {
            SecretKeySpec keySpec = new SecretKeySpec(pwdBytes, ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(clearTextBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static byte[] decrypt(byte[] cipherTextBytes, byte[] pwdBytes) {

        try {
            SecretKeySpec keySpec = new SecretKeySpec(pwdBytes, ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_MODE);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(cipherTextBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String encryptBase64(String clearText, String password) {

        try {
            byte[] cipherTextBytes = encrypt(clearText.getBytes(CHARACTER), pwdHandler(password));
            Base64.Encoder encoder = Base64.getEncoder();
            return  encoder.encodeToString(cipherTextBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * BASE64解密
     * @param cipherText 密文，带解密的内容
     * @param password 密码，解密的密码
     * @return 返回明文，解密后得到的内容。解密错误返回null
     */
    public static String decryptBase64(String cipherText, String password) {

        try {
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] result = decoder.decode(cipherText);
            byte[] clearTextBytes = decrypt(result, pwdHandler(password));
            return new String(clearTextBytes, CHARACTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * HEX加密
     * @param clearText 明文，待加密的内容
     * @param password 密码，加密的密码
     * @return 返回密文，加密后得到的内容。加密错误返回null
     */
    public static String encryptHex(String clearText, String password) {

        try {
            byte[] cipherTextBytes = encrypt(clearText.getBytes(CHARACTER), pwdHandler(password));
            String cipherText = byte2hex(cipherTextBytes);
            return cipherText;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * HEX解密
     * @param cipherText 密文，带解密的内容
     * @param password 密码，解密的密码
     * @return 返回明文，解密后得到的内容。解密错误返回null
     */
    public static String decryptHex(String cipherText, String password) {

        try {
            byte[] cipherTextBytes = hex2byte(cipherText);
            byte[] clearTextBytes = decrypt(cipherTextBytes, pwdHandler(password));
            return new String(clearTextBytes, CHARACTER);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static String byte2hex(byte[] bytes) {

        StringBuffer sb = new StringBuffer(bytes.length * 2);
        String tmp = "";
        for (int n = 0; n < bytes.length; n++) {
            // 整数转成十六进制表示
            tmp = (Integer.toHexString(bytes[n] & 0XFF));
            if (tmp.length() == 1) {
                sb.append("0");
            }
            sb.append(tmp);
        }
        return sb.toString().toUpperCase();
    }

    private static byte[] hex2byte(String str) {
        if (str == null || str.length() < 2) {
            return new byte[0];
        }
        str = str.toLowerCase();
        int l = str.length() / 2;
        byte[] result = new byte[l];
        for (int i = 0; i < l; ++i) {
            String tmp = str.substring(2 * i, 2 * i + 2);
            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
        }
        return result;
    }

}
