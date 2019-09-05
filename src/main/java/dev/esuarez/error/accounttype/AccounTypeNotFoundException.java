package dev.esuarez.error.accounttype;

public class AccounTypeNotFoundException extends RuntimeException {
    public AccounTypeNotFoundException(Long id) {
        super("AccountType id not found " + id);
    }
}
