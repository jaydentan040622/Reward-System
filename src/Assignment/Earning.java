
package Assignment;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Earning {

    private String invoiceNo;
    private int value;
    private String memberNo;
    private int oriValue;
    private LocalDate earningDate;
    private LocalDate expiryDate;

    public Earning() {

    }

    public Earning(String invoiceNo, int value, String memberNo, int oriValue, LocalDate earningDate, LocalDate expiryDate) {
        this.invoiceNo = invoiceNo;
        this.value = value;
        this.memberNo = memberNo;
        this.oriValue = oriValue;
        this.earningDate = earningDate;
        this.expiryDate = expiryDate;
    }

    public Earning(String invoiceNo, int value, String memberNo) {
        this.invoiceNo = invoiceNo;
        this.value = value;
        this.memberNo = memberNo;
        this.earningDate = LocalDate.now();
        this.expiryDate = LocalDate.now();
        this.oriValue = value;

        // write to file logic
        writeToFile();

    }

    private void writeToFile() {
        File file = new File("earning.csv");
        if (!(file.exists() && !(file.isDirectory()))) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Earning.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<String[]> dataLines;
            dataLines = new ArrayList<>();
            dataLines.add(new String[]{"Member No.", "Invoice No.", "Current Value", "Original Value", "Earning Date", "Expiry Date"});
            try {
                CSVWrite.givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines, "earning.csv", true);
            } catch (IOException ex) {
                Logger.getLogger(Earning.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        try {
//            CSVWrite csvWrite = new CSVWrite();
            List<String[]> dataLines;
            dataLines = new ArrayList<>();
            dataLines.add(new String[]{this.getMemberNo(), this.getInvoiceNo(), Integer.toString(this.getValue()), String.valueOf(this.getOriValue()), this.getEarningDate().toString(), this.getExpiryDate().toString()});

            CSVWrite.givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines, "earning.csv", true);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void rewriteToFile(List<Earning> earnings) {
        try {
//            CSVWrite csvWrite = new CSVWrite();
            List<String[]> dataLines;
            dataLines = new ArrayList<>();
            dataLines.add(new String[]{"Member No.", "Invoice No.", "Current Value", "Original Value", "Earning Date", "Expiry Date"});
            for (Earning earning : earnings) {
                dataLines.add(new String[]{earning.getMemberNo(), earning.getInvoiceNo(), Integer.toString(earning.getValue()), String.valueOf(earning.getOriValue()), earning.getEarningDate().toString(), earning.getExpiryDate().toString()});
            }
            CSVWrite.givenDataArray_whenConvertToCSV_thenOutputCreated(dataLines, "earning.csv", false);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public int getOriValue() {
        return oriValue;
    }

    public void setOriValue(int oriValue) {
        this.oriValue = oriValue;
    }

    public LocalDate getEarningDate() {
        return earningDate;
    }

    public void setEarningDate(LocalDate earningDate) {
        this.earningDate = earningDate;
    }

}
