package QrGenerate;

import java.util.ArrayList;

public class Participants  {
    private String name ;
    private String pin ;
    private String designation ;
    private String division;
    private String mobile;
    private String whatsApp;
    private String email ;
    private String tableNo ;
    private String seatNo ;
    private boolean award;
    private String awardName;
    private String awardCategory;
    private String awardDistributor;

    public Participants(String name, String pin, String designation, String division, String mobile, String whatsApp,
                        String email, String tableNo, String seatNo, boolean award, String awardName, String awardCategory,
                        String awardDistributor) {
        super();
        this.name = name;
        this.pin = pin;
        this.designation = designation;
        this.division = division;
        this.mobile = mobile;
        this.whatsApp = whatsApp;
        this.email = email;
        this.tableNo = tableNo;
        this.seatNo = seatNo;
        this.award = award;
        this.awardName = awardName;
        this.awardCategory = awardCategory;
        this.awardDistributor = awardDistributor;
    }

    public Participants(String name, String pin, String division, String mobile, String whatsApp, String email, Boolean award) {
        this.name = name;
        this.pin = pin;
        this.division = division;
        this.mobile = mobile;
        this.whatsApp = whatsApp;
        this.email = email;
        this.award = award;
    }
//    public QrGenerate.Participants(){
//        super();
//    }

 /*   public QrGenerate.Participants(String name, String pin, int mobile, int tableNo, int seatNo) {
        this.name = name;
        this.pin = pin;
        this.mobile = mobile;
        this.tableNo = tableNo;
        this.seatNo = seatNo;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTableNo() {
        return tableNo;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public boolean isAward() {
        return award;
    }

    public void setAward(boolean award) {
        this.award = award;
    }

    /*public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public String getAwardCategory() {
        return awardCategory;
    }

    public void setAwardCategory(String awardCategory) {
        this.awardCategory = awardCategory;
    }

    public String getAwardDistributor() {
        return awardDistributor;
    }

    public void setAwardDistributor(String awardDistributor) {
        this.awardDistributor = awardDistributor;
    }*/
}
