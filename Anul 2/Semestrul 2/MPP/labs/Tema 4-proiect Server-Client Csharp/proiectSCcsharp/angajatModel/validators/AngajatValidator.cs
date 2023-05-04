using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.domain.validators
{
    public class AngajatValidator : Validator<Angajat>
    {
        public AngajatValidator()
        {
        }

        public void validate(Angajat entity)
        {
            String errors = "";
            if (entity.name == "")
                errors += "invalid name\n";
            if (entity.username == "")
                errors += "invalid username\n";
            if (entity.password == "")
                errors += "invalid password\n";
            if (errors.Length > 0)
                throw new ValidationException(errors);
        }
    }
}