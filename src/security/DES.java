package security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;

public class DES {
    public static  String encrypt(String keyString,String str) throws Exception {
        Cipher cipher=Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE,getSecretKeyFromStringKey(keyString));
        byte[] utf8 = str.getBytes("UTF8");
        byte[] enc = cipher.doFinal(utf8);
        return new sun.misc.BASE64Encoder().encode(enc);
    }
    public static  String decrypt(String keyString,String str) throws Exception {
        Cipher cipher=Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE,getSecretKeyFromStringKey(keyString));
        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
        byte[] utf8 = cipher.doFinal(dec);
        return new String(utf8, "UTF8");
    }
    private static SecretKey getSecretKeyFromStringKey(String keyString) throws Exception {
        DESKeySpec key = new DESKeySpec(keyString.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        return keyFactory.generateSecret(key);
    }
}
