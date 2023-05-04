using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.domain
{
    [Serializable]

    public class Bilet : Entity<long>
    {
        public string cumparatorName { get; set; } 
        public long idSpectacol { get; set; }  
        public int nrSeats { get; set; }

        public Bilet(long id, string CumparatorName, long Id_spectacol, int NrSeats):base(id)
        {
            cumparatorName = CumparatorName;
            idSpectacol = Id_spectacol;
            nrSeats = NrSeats;
        }
        public override string ToString()
        {
            return cumparatorName + " | " + idSpectacol + " | " + nrSeats ;
        }
    }
}
