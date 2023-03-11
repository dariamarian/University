package domain.validators;

import domain.Utilizator;
import exceptions.ValidationException;

import java.util.Objects;

public class UtilizatorValidator implements Validator<Utilizator> {
    private static final UtilizatorValidator instance = new UtilizatorValidator();

    public static UtilizatorValidator getInstance() {
        return instance;
    }

    private UtilizatorValidator() {
    }

    @Override
    public void validate(Utilizator entity) throws ValidationException {
        String errors = "";
        if (Objects.equals(entity.getFirstName(), ""))
            errors += "invalid first name\n";
        if (Objects.equals(entity.getLastName(), ""))
            errors += "invalid last name\n";
        if (Objects.equals(entity.getEmail(), ""))
            errors += "invalid email\n";
        if (Objects.equals(entity.getPassword(), ""))
            errors += "invalid password\n";
        if (errors.length() > 0)
            throw new ValidationException(errors);
    }
}
