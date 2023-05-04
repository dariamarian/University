using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using proiectSCcsharp.domain;

namespace proiectSCcsharp.repository.interfaces
{
    interface IRepository <ID, T> where T : Entity<ID>
    {
        T add(T entity);
        T remove (ID id);
        T findElement(ID id);
        List<T> getAll();
    }
}
