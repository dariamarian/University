using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BaschetLab8.domain;

namespace BaschetLab8.repository
{
    internal interface Repository<ID, E> where E : Entity<ID>
    {
        IEnumerable<E> GetAll();
    }
}
