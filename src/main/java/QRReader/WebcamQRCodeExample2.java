package QRReader;

import com.github.sarxos.webcam.Webcam;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class WebcamQRCodeExample2 extends JFrame {
    private Webcam webcam = null;
    boolean isClicked = false;

    public WebcamQRCodeExample2() {

        setTitle("Main frame");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        add(new JButton(new AbstractAction("CAPTURE QR") {

            @Override
            public void actionPerformed(ActionEvent e) {
                isClicked = true;
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try (QrCapture qr = new QrCapture()) {
                            System.out.println(qr.getResult());
                            String data = qr.getResult();
                            showMessage("QR code text is:\n" + qr.getResult() + "");
                            isClicked = false;
                            

                        } catch (InterruptedException | IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                thread.setDaemon(true);
                thread.start();
            }
        }));
        pack();
        setVisible(true);
    }

    public void close() throws IOException {
        webcam.close();
    }

    private void showMessage(String text) {
        JOptionPane.showMessageDialog(null, text);
    }

    public static void main(String[] args) {
        new WebcamQRCodeExample2();
    }
}