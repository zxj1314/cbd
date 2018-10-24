package com.test.cbd.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Random;

/**
 * Created by Toby on 2017/3/2 0002.
 */

public class AesTool {
    private static AlgorithmParameterSpec iv;
    private  Key key;
    private static int SALT_LENGTH = 16;

    private AesTool() {
    }

    public static AesTool getInstance(String desKey) throws Exception {
        return new AesTool(desKey);
    }

    public AesTool(String desKey) throws Exception {
        key = new SecretKeySpec(desKey.getBytes("UTF-8"), "AES");
    }

    /**
     * @Description  key与salt结合进行AES加密
     * @author       huangjunjie
     * @date         2017/7/20
     */
    public String encrypt(String data) throws Exception {
        Cipher enCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        byte[] salt = new byte[SALT_LENGTH];
        new Random().nextBytes(salt);
        iv = new IvParameterSpec(salt);
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] pasByte = enCipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        byte[] pasInDB = new byte[pasByte.length + salt.length];
        System.arraycopy(salt, 0, pasInDB, 0, SALT_LENGTH);
        //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
        System.arraycopy(pasByte, 0, pasInDB, SALT_LENGTH, pasByte.length);
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(pasInDB);
    }



    /**
     * @Description  key与salt结合进行AES解密
     * @author       huangjunjie
     * @date         2017/7/20
     */
    public String decrypt(String data) throws Exception {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] pasInDB = base64Decoder.decodeBuffer(data);
        byte[] salt = new byte[SALT_LENGTH];
        byte[] pwd = new byte[pasInDB.length - SALT_LENGTH];
        System.arraycopy(pasInDB, 0, salt, 0, SALT_LENGTH);
        System.arraycopy(pasInDB, SALT_LENGTH, pwd, 0, pwd.length);
        Cipher deCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        iv = new IvParameterSpec(salt);
        deCipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] pasByte = deCipher.doFinal(pwd);
        return new String(pasByte, StandardCharsets.UTF_8);
    }
}
