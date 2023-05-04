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
        Angajat getAngajatByUsername(String username);
        List<Angajat> getAllAngajati();
        Spectacol getSpectacol(long id);
        Spectacol getSpectacolByAll(String artistName, String time, String place);
        List<Spectacol> getAllSpectacole();
        List<SpectacolDTO> getAllSpectacoleCautate(String date);
        void addBilet(String cumparatorName, long idSpectacol, int nrSeats);

    }
}
