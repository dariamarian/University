using proiectSCcsharp.domain;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.services
{
    public interface IService
    {
        Angajat login(Angajat employee, IObserver employeeObserver);
        void logout(Angajat employee);
        Spectacol getSpectacol(long id);
        List<Spectacol> getAllSpectacole();
        void addBilet(Bilet bilet);

    }
}
