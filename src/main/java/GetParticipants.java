import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
        IntStream.range(0, rowCount).forEach(
                rowNum -> {
                    try {
                        // Excel sheet formatter
                        DataFormatter formatter = new DataFormatter();
                        String name = formatter.formatCellValue(sheet.getRow(rowNum).getCell(0)).toString();
                        String pin = formatter.formatCellValue(sheet.getRow(rowNum).getCell(1)).toString();
                        String designation = formatter.formatCellValue(sheet.getRow(rowNum).getCell(2)).toString();
                        String division = formatter.formatCellValue(sheet.getRow(rowNum).getCell(3)).toString();
                        int mobile = Integer.parseInt(formatter.formatCellValue(sheet.getRow(rowNum).getCell(4)).toString());
                        int whatsApp = Integer.parseInt(formatter.formatCellValue(sheet.getRow(rowNum).getCell(5)).toString());
                        String email = formatter.formatCellValue(sheet.getRow(rowNum).getCell(6)).toString();
                        int tableNo = Integer.parseInt(formatter.formatCellValue(sheet.getRow(rowNum).getCell(7)).toString());
                        int seatNo = Integer.parseInt(formatter.formatCellValue(sheet.getRow(rowNum).getCell(8)).toString());
                        Boolean award = Boolean.valueOf(formatter.formatCellValue(sheet.getRow(rowNum).getCell(9)).toString());
                        String awardName = formatter.formatCellValue(sheet.getRow(rowNum).getCell(10)).toString();
                        String awardCategory = formatter.formatCellValue(sheet.getRow(rowNum).getCell(11)).toString();
                        String awardDistributor = formatter.formatCellValue(sheet.getRow(rowNum).getCell(12)).toString();
                       /* Participants participants = new Participants(name,pin,designation,division,mobile,whatsApp,email,
                                tableNo,seatNo,award,awardName,awardCategory,awardDistributor);*/
                        qrcode1.writeQRCode(new Participants(name,pin,designation,division,mobile,whatsApp,email,
                                tableNo,seatNo,award,awardName,awardCategory,awardDistributor));
                        /*Participants participants =new Participants(name,pin,designation,division,mobile,whatsApp,email,
                                tableNo,seatNo,award,awardName,awardCategory,awardDistributor);
                        participantsArrayList.add(participants);*/



                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
