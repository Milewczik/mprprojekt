package pl.pjatk.mprProject.Exception;

public class CarAlreadyExistsException extends RuntimeException{
    public CarAlreadyExistsException(String registration) {
        super("Samochód o rejestracji "+registration+" już istnieje.");
    }
}
