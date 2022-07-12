import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BankomatImp implements Bankomat{
    private final int accessCode = 123456789;
    private ArrayList<Integer> billsList;
    private ArrayList<Transaction> transactionsList;
    private ArrayList<Konto> accountsList;
    private final int[] correctBills = new int[] {10, 20, 50, 100, 200, 500};
    private final Scanner scan = new Scanner(System.in);

    BankomatImp() {
        this .billsList = Bills.initiaizeBills();
        this.transactionsList = new ArrayList<>(0);
        this.accountsList = new ArrayList<>(0);
        this.initializeExampleAccounts();
    }

    public void startOperation() {

        System.out.println("Włóż kartę (Wprowadz numer karty oraz PIN):  ");

        Karta insertedCard = new Karta(scan.nextLong(), scan.nextInt());

        Konto matchedAccount;
        try {
            matchedAccount = isCardValid(insertedCard);  //nie znaleziono konta z taka karta po dwoch probach
        } catch (NullPointerException e) {
            return;
        }

        boolean czyPrzerwacOperacje = false;
        while(!czyPrzerwacOperacje) {
            System.out.println("1)Wplac  2) Wyplac 3) Wyswietl stan konta 4) Wyswietl transakcje na koncie 5) Zakończ");
            int operationChoice = scan.nextInt();

            switch (operationChoice){
                case 1:
                    try {
                        this.startCashDeposit(matchedAccount);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    break;
                case 2:
                    try {
                        this.startCashWithdrawal(matchedAccount);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Aktualny stan konta: " + matchedAccount.getMoneyAmount());
                    break;
                case 4:
                    matchedAccount.printTransactions();
                    break;
                default: czyPrzerwacOperacje = true;
            }
        }

    }

    private Konto isCardValid(Karta insertedCard) throws NullPointerException{

        Konto matchedAccount;
        try{
            matchedAccount = searchInAccountList(insertedCard.getCardNumber());
        } catch (Exception e) {
            System.out.println("Bledny numer karty, wprowadz ponownie numer oraz PIN");
            insertedCard.setCardNumber(scan.nextLong());
            insertedCard.setPin(scan.nextInt());
        }

        try{
            matchedAccount = searchInAccountList(insertedCard.getCardNumber());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new NullPointerException("Bledny numer karty lub PIN");
        }

        return matchedAccount;
    }

    public Konto searchInAccountList(long cardNumber) throws CardNotFoundException{
        for(int i = 0; i<this.accountsList.size(); i++) {
            if(cardNumber == accountsList.get(i).getCardNumber())
                return accountsList.get(i);
        }
        throw new CardNotFoundException("Błędny numer karty");
    }

    private void startCashDeposit(Konto account) throws InputMismatchException{
        Scanner scan = new Scanner(System.in);
        System.out.println("Ile banknotów chcesz wplacic: ");

        int billsToDeposit = scan.nextInt();

        ArrayList<Integer> newBillsList = new ArrayList<>(0);
        for(int i=0; i<billsToDeposit; i++) {
            try {
                int depositedBill = scan.nextInt();
                if(isCorrectBillInput(depositedBill))
                    newBillsList.add(depositedBill);
                else throw new InputMismatchException("Nieprawidłowa wartosc!");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        //dodanie pieniedzy do konta po mozliwym odtraceniu prowizji
        double depositedMoneyInTotal = Bills.CalculateBillSum(newBillsList);
        double commision = calculateCommision(account, depositedMoneyInTotal);
        account.addMoneyToAccount((depositedMoneyInTotal - commision));

        //utworzenie nowej transakcji oraz dodanie jej do konta i do calego bankomatu
        this.createTransaction(account.getAccountID(), "wpłata", (depositedMoneyInTotal - commision));
        //dodajemy do konta ostatnio dodana transakcje do bankoatu
        this.addTransactionToAccount(account.getAccountID(), this.transactionsList.get(this.transactionsList.size()-1));

        //dodanie nowych baknnotów do biletomatu
        Bills.addBillsToList(this.billsList, newBillsList);

        System.out.println(this.transactionsList.get(this.transactionsList.size() - 1));
    }

    private void createTransaction(int accountID, String transactionType, double transactionAmount) {
        Transaction newTransaction =  new Transaction(accountID, transactionType, transactionAmount);
        this.transactionsList.add(newTransaction);
    }

    private void initializeExampleAccounts() {
        accountsList.add(new Konto());
        Konto exampleAccount2 = new Konto(this.getNewAccountID(), "Adam", "Nowak", "standard", "ING", 1000);
        exampleAccount2.initializeExampleCard();
        accountsList.add(exampleAccount2);
    }

    private void addTransactionToAccount(int accountID, Transaction newTransaction) {
        for(int i=0; i<this.accountsList.size(); i++) {
            if(accountsList.get(i).getAccountID() == accountID) {
                Konto matchedAccount = accountsList.get(i);
                matchedAccount.addTransactionToAccount(newTransaction);
            }
        }
    }
    public double calculateCommision(Konto account, double depositedMoney) {
        double commision = 0;
        if(!account.getBank().equals(this.bank)) {
            commision = 0.05 * depositedMoney;
            if(commision < 10)
                commision = 10;
        }

        return commision;
    }

    private boolean isCorrectBillInput(int inputBill) {
        boolean flag = false;
        for(int i : correctBills) {
            if(inputBill == i)
                flag = true;
        }
        return flag;
    }

    private void startCashWithdrawal(Konto account) throws NotEnoughMoneyException{
        Scanner scan = new Scanner(System.in);
        System.out.println("Ile pieniedzy chcesz wyplacic: ");
        int moneyToWithdrawal = scan.nextInt();

        if(!account.isEnoughMoney(moneyToWithdrawal))
            throw new NotEnoughMoneyException("Zbyt malo pieniedzy na koncie!");


        ArrayList<Integer> billsTOWithdrawalList = chooseBillToWithdrawal(moneyToWithdrawal);

        if(billsTOWithdrawalList == null)
            throw new NotEnoughMoneyException("Zbyt malo pieniedzy w bankomacie!");
        else {
            double commision = calculateCommision(account, moneyToWithdrawal);
            account.addMoneyToAccount(-(moneyToWithdrawal + commision));

            //utworzenie nowej transakcji oraz dodanie jej do konta i do calego bankomatu
            this.createTransaction(account.getAccountID(), "wpłata", (-moneyToWithdrawal));
            //dodajemy do konta ostatnio dodana transakcje do bankoatu
            this.addTransactionToAccount(account.getAccountID(), this.transactionsList.get(this.transactionsList.size()-1));
            System.out.println(this.transactionsList.get(this.transactionsList.size() - 1));
        }

        Bills.printBills(billsTOWithdrawalList);
    }

    private ArrayList<Integer> chooseBillToWithdrawal(double moneyAmount){
        ArrayList<Integer> billToWithdrawalList = new ArrayList<Integer>(0);
        double moneyAmountCopy = moneyAmount;
        for (int i = 0; ((i < this.billsList.size()) && (moneyAmount >= 0.00)); i++) {
            if (moneyAmount >= this.billsList.get(i)) {
                int temp = (int)Math.floor(moneyAmount/this.billsList.get(i));

                //dodanie banknotu na liste do wyplaty
                billToWithdrawalList.add(this.billsList.get(i));

                moneyAmount -= this.billsList.get(i);
                //moneyAmount = (double) Math.round(100*(moneyAmount-(temp*this.billsList.get(i))))/100;

                //usuniecie banknotu z listy w bankomacie
                this.billsList.remove(i);
                i--;
            }
        }

        //zwroc null jesli nie ma tyle pieniedzy na wyplate kwoty
        if(Bills.CalculateBillSum(billToWithdrawalList) < moneyAmountCopy)
            return null;

        return billToWithdrawalList;
    }

    public void printBills() {
        Bills.printBills(this.billsList);
    }

    private int getNewAccountID() {
        int lastAccountIndex =  this.accountsList.size() - 1;
        return this.accountsList.get(lastAccountIndex).getAccountID() + 1;
    }

    public String toString() {
        String torRetun = "";
        for(int i=0; i<this.transactionsList.size(); i++) {
            torRetun += (transactionsList.get(i) + "\n");
        }
        return torRetun;
    }

    /**
     * Metoda służąca do wydrukowania wszystkich transakcji przed podana w argumencie data
     * @param date - parametr typu LocalDate w formacie "YYYY-MM-DD"
     */
    public void printTransactionToDate(BankomatImp bankomat, LocalDate date) {
        int i = 0;
        LocalDate tempDate = convertToLocalDateViaInstant(bankomat.transactionsList.get(i).getTransactionDate());

        while(tempDate.isBefore(date) ) {
            System.out.println(transactionsList.get(i).toString());

            i++;
            if((i) == bankomat.transactionsList.size())
                break;
            tempDate = convertToLocalDateViaInstant(bankomat.transactionsList.get(i).getTransactionDate());
        }
    }

     public boolean checkAccessCode(int insertedCode) {
        if(this.accessCode == insertedCode)
            return true;
        else return false;
    }

    public void deleteAllTransactions() {
        this.transactionsList.clear();
    }

    /**
     * Konwerscja typu daty Date do LocalDate
     * @param dateToConvert
     * @return
     */
    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
