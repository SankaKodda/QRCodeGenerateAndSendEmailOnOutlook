import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.stream.IntStream;
public class GetParticipants {
    public static int rowCount = 0;

    public static ArrayList<Participants> participantsArrayList = new ArrayList<>();

    public static void main(String[] args) throws IOException, Exception {
        excelReader();
    }
    public static void excelReader() throws IOException {


        // Read Excel file path
        String excelPath = "./data/Participants.xlsx";
        // Excel Sheet Name
//        String sheetName = "OldUserName_ReplacedNewProducts";
        //Excel sheet for new Order
        String sheetName = "Sheet1";

        // Create object from AddNewCartDetails Class



        // Read Excel file
        XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
        QRCodeGenerator qrcode1 = new QRCodeGenerator();

        // Select the related sheet
        XSSFSheet sheet = workbook.getSheet(sheetName);
        // get the count of rows in the sheet
        rowCount = sheet.getPhysicalNumberOfRows();
        // loop all rows using rowCount
        // rowCount --> count of the rows
        // x --> index of the iteration
        IntStream.range(1, rowCount).forEach(
                rowNum -> {
                    try {
                        // Excel sheet formatter
                        DataFormatter formatter = new DataFormatter();
                        String name = formatter.formatCellValue(sheet.getRow(rowNum).getCell(0)).toString();
                        String pin = formatter.formatCellValue(sheet.getRow(rowNum).getCell(1)).toString();
                        String designation = formatter.formatCellValue(sheet.getRow(rowNum).getCell(2)).toString();
                        String division = formatter.formatCellValue(sheet.getRow(rowNum).getCell(3)).toString();
                        String plant = formatter.formatCellValue(sheet.getRow(rowNum).getCell(4)).toString();
                        String mobile = formatter.formatCellValue(sheet.getRow(rowNum).getCell(5)).toString();;
                        String  whatsApp = formatter.formatCellValue(sheet.getRow(rowNum).getCell(6)).toString();;
                        String email = formatter.formatCellValue(sheet.getRow(rowNum).getCell(7)).toString();
                        String tableNo = formatter.formatCellValue(sheet.getRow(rowNum).getCell(8)).toString();;
                        String seatNo = formatter.formatCellValue(sheet.getRow(rowNum).getCell(9)).toString();;
                        Boolean award = Boolean.valueOf(formatter.formatCellValue(sheet.getRow(rowNum).getCell(10)).toString());
                        String awardName = formatter.formatCellValue(sheet.getRow(rowNum).getCell(11)).toString();
                        String awardCategory = formatter.formatCellValue(sheet.getRow(rowNum).getCell(12)).toString();
                        String awardDistributor = formatter.formatCellValue(sheet.getRow(rowNum).getCell(13)).toString();
                       /* Participants participants = new Participants(name,pin,designation,division,mobile,whatsApp,email,
                                tableNo,seatNo,award,awardName,awardCategory,awardDistributor);*/
                        qrcode1.writeQRCode(new Participants(name,pin,designation,division,mobile,whatsApp,email,
                                tableNo,seatNo,award,awardName,awardCategory,awardDistributor));
//                        sendMails(email);
                        sendOutlookMail(email);
                        /*Participants participants =new Participants(name,pin,designation,division,mobile,whatsApp,email,
                                tableNo,seatNo,award,awardName,awardCategory,awardDistributor);
                        participantsArrayList.add(participants);*/



                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
    public static void sendOutlookMail(String email){
        final String username = "rusanka123@hotmail.com";  // like yourname@outlook.com
        final String password = "Longasspanda16";   // password here

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        session.setDebug(true);

        try {
            Multipart multipart = new MimeMultipart();
            MimeBodyPart attachmentPart = new MimeBodyPart();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));   // like inzi769@gmail.com
            message.setSubject("Test");
            message.setText("HI you have done sending mail with outlook");
            File f =new File(QRCodeGenerator.filePath);
            attachmentPart.attachFile(f);
            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void sendMails(String email){
        // Recipient's email ID needs to be mentioned.
        String to = email;
        System.out.println(to);

        // Sender's email ID needs to be mentioned
        String from = "rusanka123@hotmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        /*// Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");*/


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");

        // Get the Session object.// and pass
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

//                return new PasswordAuthentication("rusirumail@gmail.com", "sanka075");
                return new PasswordAuthentication("rusanka123@hotmail.com", "Longasspanda16");
            }

        });
        //session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setFrom(new InternetAddress("rusanka123@hotmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");

            Multipart multipart = new MimeMultipart();

            MimeBodyPart attachmentPart = new MimeBodyPart();

            MimeBodyPart textPart = new MimeBodyPart();

            try {

                File f =new File(QRCodeGenerator.filePath);

                attachmentPart.attachFile(f);
                textPart.setText("This is text");
                multipart.addBodyPart(textPart);
                multipart.addBodyPart(attachmentPart);

            } catch (IOException e) {

                e.printStackTrace();

            }

            message.setContent(multipart);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            System.out.println("Sent message unsuccessfully....");
            mex.printStackTrace();
        }
    }

}
