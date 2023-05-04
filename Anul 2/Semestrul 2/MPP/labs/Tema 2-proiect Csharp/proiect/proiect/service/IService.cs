using proiect.exceptions;
using proiect.model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiect.service
{
    interface IService
    {
        Angajat getAngajatByUsername(String username);
        IEnumerable<Angajat> getAllAngajati();
        Spectacol getSpectacol(long id);
        Spectacol getSpectacolByAll(String artistName, TimeOnly time, String place);
        IEnumerable<Spectacol> getAllSpectacole();
        void addBilet(String cumparatorName, long idSpectacol, int nrSeats);

    }
}
