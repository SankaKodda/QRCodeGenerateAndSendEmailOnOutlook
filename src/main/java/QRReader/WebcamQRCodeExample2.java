package QRReader;

import com.github.sarxos.webcam.Webcam;
import com.sun.net.httpserver.HttpServer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;


import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class WebcamQRCodeExample2 extends JFrame {
    static WebDriver driver;
    private Webcam webcam = null;
    boolean isClicked = false;
    public static String name;
    public static boolean award;
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
                            String dataList[] = qrData.split("[,]", 0);
//                            System.out.println(dataList);
//                            for (String myStr : dataList) {
//                                System.out.println(myStr);
//                            }
                            name = dataList[0];
                            award = Boolean.parseBoolean(dataList[1]);
                            System.out.println(name);
                            if(award){
                                openBrowser();
                                System.out.println("award Winner");
                                awardWinnerCounter++;

                            }else {
                                openBrowser();
                                System.out.println("Not a award Winner");
                            }
//                            award = dataList[1];
//                            email = dataList[2];
//                            pin = dataList[3];
//                            number = dataList[4];

                            showMessage("QR code text is:\n" + qr.getResult() + "");
//                            Runtime rTime = Runtime.getRuntime();



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
    public static void openBrowser() throws IOException, InterruptedException {

        String url = "F:\\MAS\\BarcodeGen\\Xing\\qrcodegen\\src\\main\\Opening\\OpeningPage\\index.html";
        File htmlFile = new File(url);
        Desktop.getDesktop().browse(htmlFile.toURI());
        Thread.sleep(5000);


       /* Document doc = jsoup.parse(htmlContent);
        Element p = doc.select("p").first();
        p.text("My Example Text");

        Document doc = new SAXBuilder().build(Main.class.getResource("/mailtemplate/DefaultMail.html"));

        // XPath that finds the `p` element with id="first"
        XPathExpression<Element> xpe = XPathFactory.instance().compile(
                "//p[@id='name']", Filters.element());
        Element p = xpe.evaluateFirst(doc);

        p.setText("This is my text");

        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
        xout.output(doc, System.out);*/

    }
    public static void javaAPI() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/participantName", (exchange -> {

            if ("GET".equals(exchange.getRequestMethod())) {
                String responseText = name;
                exchange.sendResponseHeaders(200, responseText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(responseText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));


        server.setExecutor(null); // creates a default executor
        server.start();
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