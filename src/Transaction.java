import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Transaction {
    private int accountID;
    private Date transactionDate;
    private String transactionType;
    private double transactionAmount;

    public Transaction(int accountID, String transactionType, double transactionAmount) {
        this.accountID = accountID;
        transactionDate = new Date();
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
    }

    /**
     * Metoda zwracajaca date w Stringu w formacie "yyyy-MM-dd HH:mm:ss"
     */
    public String getDate() {
        Date tempDate = transactionDate;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateInString = sdf1.format(tempDate);
        return dateInString;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String toString() {
        String toReturn = "";
        toReturn += (this.accountID + " : " + this.transactionDate + " : " + this.transactionType + " : " + this.transactionAmount);
        return toReturn;
    }

}
