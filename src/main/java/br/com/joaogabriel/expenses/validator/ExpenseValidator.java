package br.com.joaogabriel.expenses.validator;

import br.com.joaogabriel.expenses.payload.request.ExpensePostRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExpenseValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ExpensePostRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ExpensePostRequest request = (ExpensePostRequest) target;
        if (request.name().isBlank() || request.description().isBlank()
        || request.amount() != null || request.paidIn() != null) {
            errors.rejectValue("", "");
        }
    }

}
