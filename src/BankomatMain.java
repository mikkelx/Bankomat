import java.time.LocalDate;
import java.util.Scanner;

public class BankomatMain {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        BankomatImp bankomat = new BankomatImp();
        while(true) {
            System.out.println("Bankomat banku " + bankomat.bank);
            System.out.println("1) Usługi bankomatu 2) Serwis ");
            int choice = scan.nextInt();
            switch (choice) {
                case 1:
                    bankomat.startOperation();
                    break;
                case 2:
                    System.out.println("Podaj kod: ");
                    if(bankomat.checkAccessCode(scan.nextInt())) {
                        System.out.println("1) Wszystkie transakcje 2) Transakcje z okrelonej daty 3) Wymaz transakcje");
                        int serwisChoice = scan.nextInt();
                        switch (serwisChoice) {
                            case 1:
                                System.out.println(bankomat);
                                break;
                            case 2:
                                System.out.print("Podaj date w formacie YYYY-MM-DD: ");
                                String dateInString = scan.next();
                                LocalDate newDate = LocalDate.parse(dateInString);
                                bankomat.printTransactionToDate(bankomat, newDate);
                                break;
                            case 3:
                                bankomat.deleteAllTransactions();
                                break;
                        }
                    } else
                        System.out.println("Błędny kod dostępu!");
                    break;
            }


        }

    }
}

//dane do przykladowych kart
//123456 - 1234
//123457 - 1234

//kod serwisowy
//123456789