package com.example.proiect.model.validators;

import com.example.proiect.exceptions.ValidationException;
import com.example.proiect.model.Bilet;

import java.util.Objects;

public class BiletValidator implements Validator<Bilet> {
    private static final BiletValidator instance = new BiletValidator();

    public static BiletValidator getInstance() {
        return instance;
    }

    private BiletValidator() {
    }

    @Override
    public void validate(Bilet entity) throws ValidationException {
        String errors = "";
        if (Objects.equals(entity.getCumparatorName(), ""))
            errors += "invalid cumparator name\n";
        if (entity.getIdSpectacol()<0)
            errors += "invalid id spectacol\n";
        if (entity.getNrSeats()<0)
            errors += "invalid nr of seats\n";
        if (errors.length() > 0)
            throw new ValidationException(errors);
    }
}
