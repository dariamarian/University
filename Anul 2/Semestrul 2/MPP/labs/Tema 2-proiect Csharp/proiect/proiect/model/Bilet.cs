using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiect.model
{
    public class Bilet : Entity<long>
    {
        public string cumparatorName { get; set; } 
        public long id_spectacol { get; set; }  
        public int nrSeats { get; set; }

        public Bilet(string CumparatorName, long Id_spectacol, int NrSeats)
        {
            cumparatorName = CumparatorName;
            id_spectacol = Id_spectacol;
            nrSeats = NrSeats;
        }
        public override string ToString()
        {
            return cumparatorName + " | " + id_spectacol + " | " + nrSeats ;
        }
    }
}
