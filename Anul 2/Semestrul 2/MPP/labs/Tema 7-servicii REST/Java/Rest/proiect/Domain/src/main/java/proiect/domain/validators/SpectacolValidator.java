package proiect.domain.validators;

import proiect.domain.Spectacol;

import java.util.Objects;

public class SpectacolValidator implements Validator<Spectacol> {
    private static final SpectacolValidator instance = new SpectacolValidator();

    public static SpectacolValidator getInstance() {
        return instance;
    }

    public SpectacolValidator() {
    }

    @Override
    public void validate(Spectacol entity) throws ValidationException {
        String errors = "";
        if (Objects.equals(entity.getArtistName(), ""))
            errors += "invalid artist name\n";
        if (Objects.equals(entity.getDate(), ""))
            errors += "invalid date\n";
        if (Objects.equals(entity.getPlace(), ""))
            errors += "invalid place\n";
        if (entity.getAvailableSeats()<0)
            errors += "invalid available seats\n";
        if (entity.getSoldSeats()<0)
            errors += "invalid sold seats\n";
        if (errors.length() > 0)
            throw new ValidationException(errors);
    }
}
