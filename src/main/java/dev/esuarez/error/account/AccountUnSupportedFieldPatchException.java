package dev.esuarez.error.account;

import java.util.Set;

public class AccountUnSupportedFieldPatchException extends RuntimeException {
    public AccountUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }
}
