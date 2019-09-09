package dev.esuarez.error.accounttype;

public class AccountTypeNotFoundException extends RuntimeException {
    public AccountTypeNotFoundException(Long id) {
        super("AccountType id not found " + id);
    }
}
