package dev.esuarez.error.accounttype;

import java.util.Set;

public class AccountTypeUnsupportedFieldPatchException extends RuntimeException {

    public AccountTypeUnsupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }
}
