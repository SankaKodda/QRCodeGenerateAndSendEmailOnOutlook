import java.util.ArrayList;

public class Participants  {
    private String name ;
    private String pin ;
    private String designation ;
    private String division;
    private int mobile;
    private int whatsApp;
    private String email ;
    private int tableNo ;
    private int seatNo ;
    private boolean award;
    private String awardName;
    private String awardCategory;
    private String awardDistributor;

    public Participants(String name, String pin, String designation, String division, int mobile, int whatsApp,
                        String email, int tableNo, int seatNo, boolean award, String awardName, String awardCategory,
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
//    public Participants(){
//        super();
//    }

    public Participants(String name, String pin, int mobile, int tableNo, int seatNo) {
        this.name = name;
        this.pin = pin;
        this.mobile = mobile;
        this.tableNo = tableNo;
        this.seatNo = seatNo;
    }

    public Participants(ArrayList<Participants> participantsArrayList) {

    }


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

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(int whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public boolean isAward() {
        return award;
    }

    public void setAward(boolean award) {
        this.award = award;
    }

    public String getAwardName() {
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
    }
}
