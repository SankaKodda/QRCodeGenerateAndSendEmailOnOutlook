import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Watermark {
    public static void main(String[] args) throws IOException {
        File file = new File("F:\\MAS\\BarcodeGen\\Xing\\qrcodegen\\src\\main\\img\\Frame1.png");
        BufferedImage im = ImageIO.read(file);
        File file1 = new File("F:\\MAS\\BarcodeGen\\Xing\\qrcodegen\\src\\main\\java\\QRCodes\\454578.png");
        BufferedImage im2 = ImageIO.read(file1);
        Graphics2D g = im.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        g.drawImage(im2, (im.getWidth()-im2.getWidth())/2, 750, null);
        g.dispose();

        display(im);
        ImageIO.write(im, "jpeg", new File("sample_output.jpeg"));
    }

    public static void display(BufferedImage image) {
        JFrame f = new JFrame("WaterMark");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new JLabel(new ImageIcon(image)));
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}