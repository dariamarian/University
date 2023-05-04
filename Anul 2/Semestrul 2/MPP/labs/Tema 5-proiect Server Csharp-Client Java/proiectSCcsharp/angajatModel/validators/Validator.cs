using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.domain.validators
{
    public interface Validator <T>
    {
        void validate(T entity);
    }
}
