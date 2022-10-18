import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class QRCodeGenerator extends  GetParticipants{
    public static void main(String[] args) throws Exception {
        excelReader();
        QRCodeGenerator codeGenerator = new QRCodeGenerator();
//        System.out.println(codeGenerator.writeQRCode(new Participants("Sanka","1234",077777777,02,05)));
        codeGenerator.writeQRCode(new Participants(participantsArrayList));

    }

    public String writeQRCode(Participants participants) throws Exception{
        String fileName= participants.getPin()+".png";
        String filePath = "F:\\MAS\\BarcodeGen\\Xing\\qrcodegen\\src\\main\\QRCodes"+fileName;
        String fileType = "png";
        File qrFile = new File(filePath);
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(participants.getName()+"\n"+participants.getPin()+"\n"+participants.getMobile()+"\n"+
                        participants.getTableNo()+"\n"+participants.getSeatNo()
                , BarcodeFormat.QR_CODE,350,350,hintMap);
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
        ImageIO.write(image, fileType,qrFile);

        return null;
    }

}
