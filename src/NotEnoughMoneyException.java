public class NotEnoughMoneyException extends Exception{
    NotEnoughMoneyException() {};

    NotEnoughMoneyException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
