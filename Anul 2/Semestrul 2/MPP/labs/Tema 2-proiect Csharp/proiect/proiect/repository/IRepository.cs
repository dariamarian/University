using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using proiect.model;

namespace proiect.repository
{
    interface IRepository <ID, T> where T : Entity<ID>
    {
        T add(T entity);
        T remove (ID id);
        T findElement(ID id);
        IEnumerable<T> getAll();
    }
}
