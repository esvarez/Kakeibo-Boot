package dev.esuarez.error.user;

import java.util.Set;

public class UserUnsupportedFieldPatchException extends RuntimeException {
    public UserUnsupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }
}
