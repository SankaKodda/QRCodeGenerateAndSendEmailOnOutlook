package QrGenerate;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class QRCodeGenerator extends GetParticipants {
    public static String key = "Bar12345Bar12345"; // 128 bit key
    public static String initVector = "RandomInitVector"; // 16 bytes IV
    public String encryptedDataParticipant;
public static String filePath;
   /* public static void main(String[] args) throws Exception {
        *//*excelReader();
        QrGenerate.QRCodeGenerator codeGenerator = new QrGenerate.QRCodeGenerator();*//*
//        System.out.println(codeGenerator.writeQRCode(new QrGenerate.Participants("Sanka","1234",077777777,02,05)));


    }*/
  /* File xlsxFile = new File("I:\\MAS\\QR\\QRCodeGenerateAndSendEmailOnOutlook\\data\\Participants1.xlsx");

    //Creating input stream
   FileInputStream inputStream = new FileInputStream(xlsxFile);

    //Creating workbook from input stream
    Workbook workbook = WorkbookFactory.create(inputStream);*/

    public QRCodeGenerator() throws IOException {

    }

    public String writeQRCode(Participants participants) throws Exception{
        String fileName= participants.getPin()+".png";
        filePath = "src/main/java/QRCodes/"+fileName;
        String fileType = "png";
        File qrFile = new File(filePath);



        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter writer = new QRCodeWriter();
        String dataUser =(participants.getName()+","+
                participants.isAward()+","+participants.getEmail()+","+participants.getPin()+","
                +participants.getMobile())+participants.getDivision()+",";
        encryptedDataParticipant = encrypt(key, initVector, dataUser);
        System.out.println("EncryptedData :  "+encryptedDataParticipant);
        String decryptedDataParticipant = decrypt(key, initVector,encryptedDataParticipant);
//        excelWriter();
//        insertNewColumnBeforeWithData(11);
        System.out.println("DecryptedData :  "+decryptedDataParticipant);
        /*String encryptData = encrypt("AES",dataUser,generateKey(256),generateIv());
        System.out.println(encryptData);*/


        /*BitMatrix bitMatrix = writer.encode(encryptedDataParticipant
                , BarcodeFormat.QR_CODE,450,450,hintMap);*/
        BitMatrix bitMatrix = writer.encode(encryptedDataParticipant
                , BarcodeFormat.QR_CODE,230,230,hintMap);
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

        File file = new File("I:\\MAS\\QR\\QRCodeGenerateAndSendEmailOnOutlook\\src\\main\\img\\Frame3-01.png");
        BufferedImage frame = ImageIO.read(file);
        Graphics2D g = frame.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//        g.drawImage(image, 600, 2400, null);
        g.drawImage(image, 84, 512, null);
        g.dispose();
        ImageIO.write(frame, fileType,qrFile);
        return null;
    }
    public static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            System.out.println("encrypted string: "
                    + Base64.encodeBase64String(encrypted));

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    /*
     * This method generate random id
     */
    private static int generateId() {
        return (int) (Math.random() * 100000);
    }
    /*public static void excelWriter() throws IOException {
        // Creating file object of existing excel file

        File xlsxFile = new File("I:\\MAS\\QR\\QRCodeGenerateAndSendEmailOnOutlook\\data\\Participants1.xlsx");

//Creating input stream
        FileInputStream inputStream = new FileInputStream(xlsxFile);

        //Creating workbook from input stream
        Workbook workbook = WorkbookFactory.create(inputStream);

        // Getting the first sheet from workbook
        Sheet sheet = workbook.getSheetAt(0);

        int colIndex=1;
        int startColumn = colIndex;
        int endColumn = sheet.getRow(0).getLastCellNum();

        // to insert only one column
        int newColCount = 1;

        *//*
         * Shifts columns between startColumn and endColumn, newColCount number of
         * columns. Code ensures that columns don't wrap around
         *//*
        sheet.shiftColumns(startColumn, endColumn, newColCount);

        // Add the data
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (i == 0) {
                row.createCell(colIndex).setCellValue("Encrypted Code");
            } else {
                row.createCell(colIndex).setCellValue(encryptedDataParticipant);
            }
        }

    }*/

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
    public static void encrpted1(){
        String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector"; // 16 bytes IV

        System.out.println(decrypt(key, initVector,
                encrypt(key, initVector, "Hello World")));
    }

    /*public static String encrypt(String algorithm, String input, SecretKey key,
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
    }*/

}
