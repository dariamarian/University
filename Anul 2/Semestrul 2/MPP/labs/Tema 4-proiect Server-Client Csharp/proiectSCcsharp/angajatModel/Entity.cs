using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.domain
{
    [Serializable]
    public class Entity <ID>
    {
        public ID id { get; set; }
    }
}
