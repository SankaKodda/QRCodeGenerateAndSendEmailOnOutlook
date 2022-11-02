package QrGenerate;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Hashtable;

public class QRCodeGenerator extends GetParticipants {
public static String filePath;
   /* public static void main(String[] args) throws Exception {
        *//*excelReader();
        QrGenerate.QRCodeGenerator codeGenerator = new QrGenerate.QRCodeGenerator();*//*
//        System.out.println(codeGenerator.writeQRCode(new QrGenerate.Participants("Sanka","1234",077777777,02,05)));


    }*/

    public String writeQRCode(Participants participants) throws Exception{
        String fileName= participants.getPin()+".png";
        filePath = "src/main/java/QRCodes/"+fileName;
        String fileType = "png";
        File qrFile = new File(filePath);



        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter writer = new QRCodeWriter();
        String dataUser =(participants.getName()+","+
                participants.isAward()+","+participants.getEmail()+","+participants.getPin()+","+participants.getMobile());
        /*String encryptData = encrypt("AES",dataUser,generateKey(256),generateIv());
        System.out.println(encryptData);*/


        BitMatrix bitMatrix = writer.encode(dataUser
                , BarcodeFormat.QR_CODE,450,450,hintMap);
        // Make the BufferedImage that are to hold the QRCode
        int matrixWidth = bitMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();



        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // Paint and save the image using the ByteMatrix
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        File file = new File("F:\\MAS\\BarcodeGen\\Xing\\qrcodegen\\src\\main\\img\\Frame1.png");
        BufferedImage frame = ImageIO.read(file);
        Graphics2D g = frame.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(image, 188, 710, null);
        g.dispose();
        ImageIO.write(frame, fileType,qrFile);
        return null;
    }
    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }
    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
    public static String decrypt(String algorithm, String cipherText, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

}
