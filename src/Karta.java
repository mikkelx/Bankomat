import java.util.Scanner;

public class Karta {
    private long cardNumber;
    private int pin;
    private int cvv;
    private String firstName;
    private String secondName;

    private String bank;


    public Karta(long cardNumber, int pin, int cvv, String firstName, String secondName, String bank) {
        this.cardNumber = cardNumber;
        this.pin = pin;
        this.cvv = cvv;
        this.firstName = firstName;
        this.secondName = secondName;
        this.bank = bank;
    }

    public Karta(long cardNumber, int pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getSecondName() {
        return secondName;
    }

    public long getCardNumber() {
        return this.cardNumber;
    }

    public boolean checkPIN() {
        Scanner scan = new Scanner(System.in);

        int pin = scan.nextInt();
        if(pin == this.pin)
            return true;
        else return false;
    }

    private void setNumberAndPIN() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj kolejno numer karty, PIN: ");
        this.cardNumber = scan.nextLong();
        this.pin =scan.nextInt();
    }
}
