using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace proiectSCcsharp.domain.validators
{
    public class SpectacolValidator : Validator<Spectacol>
    {
        public SpectacolValidator()
        {
        }

        public void validate(Spectacol entity)
        {
            String errors = "";
            if (entity.artistName == "")
                errors += "invalid artist name\n";
            if (entity.place == "")
                errors += "invalid place\n";
            if (entity.availableSeats < 0)
                errors += "invalid available seats\n";
            if (entity.soldSeats< 0)
                errors += "invalid sold seats\n";
            if (errors.Length > 0)
                throw new ValidationException(errors);
        }
    }
}
