package br.com.tastemanager.exception;

public class ItemMenuNotFoundException extends RuntimeException {

    public ItemMenuNotFoundException(String message) {
        super(message);
    }

    public ItemMenuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
