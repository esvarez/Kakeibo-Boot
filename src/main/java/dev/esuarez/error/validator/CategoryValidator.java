package dev.esuarez.error.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CategoryValidator implements ConstraintValidator<Category, String> {
    List<String> categories = Arrays.asList("Expense", "Income");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return categories.contains(value);
    }

}
