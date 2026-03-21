package br.com.tastemanager.shared.exception;

public class UserTypeNotFoundException extends RuntimeException {
    private final Object options;
    public UserTypeNotFoundException(String message, Object options) {
        super(message);
        this.options = options;
    }
    public Object getOptions() {
        return options;
    }
}
