using proiect.model;
using proiect.repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.StartPanel;
using System.Xml.Linq;
using System.Data.Entity.Core.Objects;
using proiect.exceptions;

namespace proiect.service
{
    public class Service : IService
    {
        private RepoAngajat angajatRepo;
        private RepoSpectacol spectacolRepo;
        private RepoBilet biletRepo;

        public Service(RepoAngajat repoAngajat, RepoSpectacol repoSpectacol, RepoBilet repoBilet)
        {
            this.angajatRepo = repoAngajat;
            this.spectacolRepo = repoSpectacol;
            this.biletRepo = repoBilet;
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

        public IEnumerable<Angajat> getAllAngajati()
        {
            return angajatRepo.getAll();
        }

        public Spectacol getSpectacol(long id)
        {
            return spectacolRepo.findElement(id);
        }
        public Spectacol getSpectacolByAll(String artistName, TimeOnly time, String place)
        {
            IEnumerable<Spectacol> spectacole = getAllSpectacole();
            foreach (Spectacol spectacol in spectacole)
            {
                String artistName2 = spectacol.artistName;
                TimeOnly time2 = TimeOnly.FromDateTime(spectacol.date);
                String place2 = spectacol.place;
                if (artistName==artistName2 && time==time2 && place==place2)
                    return spectacol;
            }
            return null;
        }
        public IEnumerable<Spectacol> getAllSpectacole()
        {
            return spectacolRepo.getAll();
        }
        
        public IEnumerable<SpectacolDTO> getAllSpectacoleCautate(DateOnly date)
        {
            IList<SpectacolDTO> temp=new List<SpectacolDTO> { };
            foreach(Spectacol spectacol in getAllSpectacole())
            {
                if(DateOnly.FromDateTime(spectacol.date)==date)
                {
                    SpectacolDTO spectacolDTO = new SpectacolDTO(spectacol.artistName,TimeOnly.FromDateTime(spectacol.date), spectacol.place, spectacol.availableSeats);
                    temp.Add(spectacolDTO);
                }
            }
            return temp;
        }
        public void addBilet(String cumparatorName, long idSpectacol, int nrSeats)
        {
            if(getSpectacol(idSpectacol).availableSeats>=nrSeats)
            {
                Bilet toAdd = new Bilet(cumparatorName, idSpectacol, nrSeats);
                Spectacol spectacol = getSpectacol(idSpectacol);
                biletRepo.add(toAdd);
                spectacolRepo.update(spectacol, nrSeats);
            }
            else
            {
                throw new RepoException("Nu exista atatea locuri disponibile.");
            }
        }
    }
}