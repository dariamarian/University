using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace proiectSCcsharp.domain.validators
{
    public class BiletValidator : Validator<Bilet> 
    {
        public BiletValidator()
        {
        }

        public void validate(Bilet entity)
        {
            String errors = "";
            if (entity.cumparatorName == "")
                errors += "invalid cumparator name\n";
            if (entity.idSpectacol < 0)
                errors += "invalid id spectacol\n";
            if (entity.nrSeats < 0)
                errors += "invalid nr of seats\n";
            if (errors.Length > 0)
                throw new ValidationException(errors);
        }
    }
}
