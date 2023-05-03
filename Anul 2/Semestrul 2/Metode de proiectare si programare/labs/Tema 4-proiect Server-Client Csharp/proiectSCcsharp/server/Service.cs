using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;
using System.Xml.Linq;
using System.Data.Entity.Core.Objects;
using proiectSCcsharp.domain.validators;
using proiectSCcsharp.domain;
using proiectSCcsharp.repository;
using proiectSCcsharp.services;

namespace proiectSCcsharp.server
{
    public class Service : IService
    {
        private readonly Validator<Angajat> angajatValidator;
        private readonly Validator<Spectacol> spectacolValidator;
        private readonly Validator<Bilet> biletValidator;
        private readonly RepoAngajat angajatRepo;
        private readonly RepoSpectacol spectacolRepo;
        private readonly RepoBilet biletRepo;
        private readonly IDictionary<long, IObserver> loggedAngajati;

        public Service(Validator<Angajat> angajatValidator, Validator<Spectacol> spectacolValidator, Validator<Bilet> biletValidator, RepoAngajat repoAngajat, RepoSpectacol repoSpectacol, RepoBilet repoBilet)
        {
            this.angajatValidator = angajatValidator;
            this.biletValidator = biletValidator;
            this.spectacolValidator = spectacolValidator;
            this.angajatRepo = repoAngajat;
            this.spectacolRepo = repoSpectacol;
            this.biletRepo = repoBilet;
            loggedAngajati = new Dictionary<long, IObserver>();
        }
        public Angajat getAngajatByUsername(String username)
        {
            IEnumerable<Angajat> angajati = getAllAngajati();
            foreach (Angajat angajat in angajati)
            {
                String username2 = angajat.username;
                if (username == username2)
                    return angajat;
            }
            return null;
        }

        public List<Angajat> getAllAngajati()
        {
            return angajatRepo.getAll();
        }

        public Spectacol getSpectacol(long id)
        {
            return spectacolRepo.findElement(id);
        }
        public Spectacol getSpectacolByAll(String artistName, String time, String place)
        {
            List <Spectacol> spectacole = getAllSpectacole();
            foreach (Spectacol spectacol in spectacole)
            {
                String artistName2 = spectacol.artistName;
                String time2 = spectacol.time;
                String place2 = spectacol.place;
                if (artistName==artistName2 && time ==time2 && place==place2)
                    return spectacol;
            }
            return null;
        }
        public List<Spectacol> getAllSpectacole()
        {
            return spectacolRepo.getAll();
        }
        
        public List<SpectacolDTO> getAllSpectacoleCautate(string date)
        {
            List<SpectacolDTO> temp=new List<SpectacolDTO> { };
            foreach(Spectacol spectacol in getAllSpectacole())
            {
                if(spectacol.date==date)
                {
                    SpectacolDTO spectacolDTO = new SpectacolDTO(spectacol.artistName,spectacol.time, spectacol.place, spectacol.availableSeats);
                    temp.Add(spectacolDTO);
                }
            }
            return temp;
        }
        public void addBilet(String cumparatorName, long idSpectacol, int nrSeats)
        {
            if (getSpectacol(idSpectacol).availableSeats>=nrSeats)
            {
                Bilet toAdd = new Bilet(cumparatorName, idSpectacol, nrSeats);
                Spectacol spectacol = getSpectacol(idSpectacol);
                biletRepo.add(toAdd);
                spectacolRepo.update(spectacol, nrSeats);
                foreach (var employeeO in loggedAngajati.Values)
                {
                    Task.Run(() => employeeO.BiletAdded(toAdd));
                }
            }
            else
            {
                throw new MyException("Nu exista atatea locuri disponibile.");
            }
        }

        public Angajat login(Angajat employee, IObserver employeeObserver)
        {
            var authenticateAngajat = angajatRepo.AuthenticateAngajat(employee.username, employee.password);
            if (authenticateAngajat != null)
            {
                if (loggedAngajati.ContainsKey(authenticateAngajat.id))
                    throw new MyException("User already logged in.");
                loggedAngajati[authenticateAngajat.id] = employeeObserver;
            }
            else
                throw new MyException("Authentication failed.");
            return authenticateAngajat;
        }

        public void logout(Angajat employee)
        {
            var localClient = loggedAngajati[employee.id];
            if (localClient == null)
                throw new MyException("User " + employee.id + " is not logged in.");
            loggedAngajati.Remove(employee.id);
        }
    }
}