import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import java.util.concurrent.Exchanger;

public class QrCapture extends JFrame implements Closeable {
    /*public static void main(String[] args) throws Exception{
        try {
            Webcam webcam = Webcam.getDefault();
            webcam.open();
            String path = "F:\\MAS\\BarcodeGen\\Xing\\qrcodegen\\src\\main\\QRCodes5457.png";
            BufferedImage bf = ImageIO.read(new FileInputStream(path));
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
            Result result = new MultiFormatReader().decode(bitmap);
            System.out.println(result.getText());
        }catch (Exception e){

        }
    }*/
    private static final long serialVersionUID = 1L;

    private Webcam webcam = null;
    private BufferedImage image = null;
    private Result result = null;
    private Exchanger<String> exchanger = new Exchanger<String>();

    public QrCapture() {

        super();

        setLayout(new FlowLayout());
        setTitle("Capture");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        webcam = Webcam.getWebcamByName("1080P Web Camera 1");
        webcam.setViewSize(WebcamResolution.QVGA.getSize());
        webcam.open();

        add(new WebcamPanel(webcam));

        pack();
        setVisible(true);

        final Thread daemon = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isVisible()) {
                    try {
                        read();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        daemon.setDaemon(true);
        daemon.start();
    }

    private static BinaryBitmap toBinaryBitmap(BufferedImage image) {
        return new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
    }

    private void read() throws IOException {

        if (!webcam.isOpen()) {
            return;
        }
        if ((image = webcam.getImage()) == null) {
            return;
        }

        try {
            result = new MultiFormatReader().decode(toBinaryBitmap(image));
        } catch (NotFoundException e) {
            return; // fall thru, it means there is no QR code in image
        }

        if (result != null) {
            try {
                exchanger.exchange(result.getText());
            } catch (InterruptedException e) {
                return;
            } finally {
                dispose();
                webcam.close();
            }
        }
    }

    public String getResult() throws InterruptedException {
        return exchanger.exchange(null);
    }

    @Override
    public void close() throws IOException {
        webcam.close();
    }
}
