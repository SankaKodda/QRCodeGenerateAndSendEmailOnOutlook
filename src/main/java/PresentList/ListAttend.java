package PresentList;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class ListAttend {
    public static String key = "Bar12345Bar12345"; // 128 bit key
    public static String initVector = "RandomInitVector"; // 16 bytes IV
    public static String encryptedDetailAttend = "gfMGiDfRZEzv6QZQiQJ/sM8VWJA2po9ThXRdyV1DHUIGaW+0zZeJEn6udbUqIVC24PyiyrA+egltWlfRCwZ0Kw==";
    public static void main(String[] args) {
        String dataAttend = decrypt(key,initVector,encryptedDetailAttend);
        System.out.println("Data "+dataAttend);
    }
    //gfMGiDfRZEzv6QZQiQJ/sM8VWJA2po9ThXRdyV1DHUIGaW+0zZeJEn6udbUqIVC24PyiyrA+egltWlfRCwZ0Kw==
    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
