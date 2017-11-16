package security;

import java.security.*;

import javax.crypto.Cipher;

public class RSA {
    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException {
        final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt(Key key, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(Key key, byte [] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(encrypted);
    }
}