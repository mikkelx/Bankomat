public class CardNotFoundException extends Exception{
    CardNotFoundException() {};

    CardNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
