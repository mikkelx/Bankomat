import java.time.LocalDate;

public interface Bankomat {
    String localization = "Kwiatowa 1, Kraków";
    String bank = "PKO BP";

    void startOperation();

    double calculateCommision(Konto account, double depositedMoney);

    public void printBills();

    void printTransactionToDate(BankomatImp bankomat, LocalDate date);

    boolean checkAccessCode(int insertedCode);


}
