package utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Security {

    private static int iterationCount = 10;
    private static String secretKey = "3st03SuN4c0ntr4s3NYa";
    private static String instance = "PBEWithMD5AndDES";
    private static byte[] salt = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };

    public static String encrypt(String plainText) throws Exception {
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance(instance).generateSecret(keySpec);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

        byte[] in = plainText.getBytes("UTF-8");
        byte[] out = cipher.doFinal(in);

        return new String(Base64.getEncoder().encode(out));
    }

    public static String decrypt(String encryptedText) throws Exception {
        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
        SecretKey key = SecretKeyFactory.getInstance(instance).generateSecret(keySpec);
        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

        Cipher cipher = Cipher.getInstance(key.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        byte[] enc = Base64.getDecoder().decode(encryptedText);
        byte[] utf8 = cipher.doFinal(enc);

        return new String(utf8, "UTF-8");
    }

}
