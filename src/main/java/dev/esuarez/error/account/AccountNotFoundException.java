package dev.esuarez.error.account;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(Long id) {
        super("Account id not found: " + id);
    }
}
