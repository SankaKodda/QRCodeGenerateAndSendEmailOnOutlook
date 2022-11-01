package QRReader;

import com.github.sarxos.webcam.Webcam;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class WebcamQRCodeExample2 extends JFrame {
    private Webcam webcam = null;
    boolean isClicked = false;
    public String name;
    public String award;
    public String email;
    public String pin;
    public String number;
    public int awardWinnerCounter = 0;
    public boolean isAward;

    public WebcamQRCodeExample2() {

        setTitle("Main frame");
        setLayout(new FlowLayout(0, 100, 100));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        add(new JButton(new AbstractAction("CAPTURE QR") {

            @Override
            public void actionPerformed(ActionEvent e) {
                isClicked = true;
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try (QrCapture qr = new QrCapture()) {
                            String qrData = qr.getResult();
                            System.out.println(qrData);
                            String[] dataList = qrData.split("[,]", 0);
                            System.out.println(dataList);
                            for (String myStr : dataList) {
                                System.out.println(myStr);
                            }
                            name = dataList[0];
                            award = dataList[1];
                            email = dataList[2];
                            pin = dataList[3];
                            number = dataList[4];
                            isAward = Boolean.parseBoolean(award);
                            showMessage("QR code text is:\n" + qr.getResult() + "");
                            isClicked = false;
                            if (isAward) {
                                awardWinnerCounter++;
                            }
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

    public static void main(String[] args) throws IOException {
        generateExcelFile();
        new WebcamQRCodeExample2();

    }
    public static void writeDataExcel(String name, Boolean awarded,String email,String pin){

    }
    @Before
    public static void generateExcelFile() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "Attends.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        Sheet sheet = workbook.createSheet("Awarded Participants");
        Sheet sheet1 = workbook.createSheet("Present Participants");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        sheet1.setColumnWidth(0, 6000);
        sheet1.setColumnWidth(1, 4000);

        Row header = sheet.createRow(0);
        Row header1 = sheet1.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
//        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
//        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Award");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Email");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("PIN");
        headerCell.setCellStyle(headerStyle);

        Cell headerCell1 = header1.createCell(0);
        headerCell1.setCellValue("Name");
        headerCell1.setCellStyle(headerStyle);

        headerCell1 = header1.createCell(1);
        headerCell1.setCellValue("Award");
        headerCell1.setCellStyle(headerStyle);

        headerCell1 = header1.createCell(2);
        headerCell1.setCellValue("Email");
        headerCell1.setCellStyle(headerStyle);

        headerCell1 = header1.createCell(3);
        headerCell1.setCellValue("PIN");
        headerCell1.setCellStyle(headerStyle);

        /*int rows = sheet.getRows();
        int columns = sheet.getColumns();

        for (int i = 0; i < rows; i++) {
            data.put(i, new ArrayList<String>());
            for (int j = 0; j < columns; j++) {
                data.get(i)
                        .add(sheet.getCell(j, i)
                                .getContents());
            }
        }
        return data;*/
        workbook.write(outputStream);
        workbook.close();

    }



}