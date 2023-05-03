using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.domain.validators
{
    public class ValidationException : Exception
    {
        public ValidationException() { }
        public ValidationException(string message) : base(message) { }
        public ValidationException(string message, Exception cause) : base(message, cause) { }

        public string getMessage()
        {
            return base.Message;
        }

    }
}
