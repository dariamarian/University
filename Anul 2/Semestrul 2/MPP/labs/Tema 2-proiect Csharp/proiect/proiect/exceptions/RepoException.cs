using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiect.exceptions
{
    class RepoException:Exception
    {
        public RepoException() { }
        public RepoException(string message) : base(message) { }
        public RepoException(string message,Exception cause) : base(message,cause) { }

        public string getMessage()
        {
            return base.Message;
        }
    }
}
