package domain.validators;

import domain.Prietenie;
import exceptions.ValidationException;

public class PrietenieValidator implements Validator<Prietenie> {
    private static final PrietenieValidator instance = new PrietenieValidator();

    public static PrietenieValidator getInstance() {
        return instance;
    }

    private PrietenieValidator() {
    }

    @Override
    public void validate(Prietenie entity) throws ValidationException {
        String errors = "";
        if (entity.getIdUser1() <= 0) {
            errors += "invalid first id for friendship.\n";
        }
        if (entity.getIdUser2() <= 0) {
            errors += "invalid second id for friendship.\n";
        }
        if (errors.length() > 0)
            throw new ValidationException(errors);
    }
}
