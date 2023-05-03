package com.example.proiect.model.validators;

import com.example.proiect.model.Angajat;
import com.example.proiect.exceptions.ValidationException;

import java.util.Objects;

public class AngajatValidator implements Validator<Angajat> {
    private static final AngajatValidator instance = new AngajatValidator();

    public static AngajatValidator getInstance() {
        return instance;
    }

    private AngajatValidator() {
    }

    @Override
    public void validate(Angajat entity) throws ValidationException {
        String errors = "";
        if (Objects.equals(entity.getName(), ""))
            errors += "invalid name\n";
        if (Objects.equals(entity.getUsername(), ""))
            errors += "invalid username\n";
        if (Objects.equals(entity.getPassword(), ""))
            errors += "invalid password\n";
        if (errors.length() > 0)
            throw new ValidationException(errors);
    }
}
