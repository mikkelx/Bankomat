import java.util.ArrayList;
import java.util.Scanner;

public class Konto {
    private int accountID;
    private String firstName;
    private String secondName;
    private String accountType;

    private String bank;
    private double moneyAmount;
    private Karta card;
    ArrayList<Transaction> transactionsList;

    public Konto() {
        this.accountID = 0;
        this.firstName = "Jan";
        this.secondName = "Kowalski";
        this.accountType = "standard";
        this.bank = "PKO BP";
        this.moneyAmount = 300;

        initializeDefaultCard();
        transactionsList = new ArrayList<>(0);
    }
    public Konto(int accountID, String firstName, String secondName, String accountType) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.accountType = accountType;
        this.moneyAmount = 0;

        initializeDefaultCard();
        transactionsList = new ArrayList<>(0);
    }

    public Konto(int accountID, String firstName, String secondName, String accountType, String bank, double moneyAmount) {
        this.accountID = accountID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.accountType = accountType;
        this.bank = bank;
        this.moneyAmount = moneyAmount;

        //this.addCardToAccount();
        transactionsList = new ArrayList<>(0);
    }

    public void initializeExampleCard() {
        long cardNumber = 123457;
        int PIN = 1234;
        int cvv = 126;
        this.card = new Karta(cardNumber, PIN, cvv, firstName, secondName, this.bank);
    }
    public void addCardToAccount() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj kolejno numer karty, PIN oraz CVV: ");
        long cardNumber = scan.nextLong();
        int PIN = scan.nextInt();
        int cvv = scan.nextInt();
        this.card = new Karta(cardNumber, PIN, cvv, firstName, secondName, this.bank);
    }

    public long getCardNumber() {
        return this.card.getCardNumber();
    }

    private void initializeDefaultCard() {
        this.card = new Karta(123456, 1234, 123, firstName, secondName, "PKO BP");
    }

    public void addMoneyToAccount(double moneyAmount) {
        this.moneyAmount += moneyAmount;
    }

    public boolean isEnoughMoney(double moneyToWithDrawal) {
        if(moneyToWithDrawal <= this.moneyAmount)
            return true;
        else return false;
    }

    public String getBank() {
        return bank;
    }

    public double getMoneyAmount() {
        return moneyAmount;
    }

    public void addTransactionToAccount(Transaction newTransaction) {
        this.transactionsList.add(newTransaction);
    }

    public int getAccountID() {
        return accountID;
    }

    public void printTransactions() {
        for(int i=0; i<this.transactionsList.size(); i++) {
            System.out.println(transactionsList.get(i));
        }
    }

    public String toString() {
        String toReturn = "ID: " + this.accountID + " : " + this.firstName + " " + this.secondName + " : " + this.accountType;
        toReturn += " : " + this.bank + " : " + this.moneyAmount + "\n";
        return toReturn;
    }
}
