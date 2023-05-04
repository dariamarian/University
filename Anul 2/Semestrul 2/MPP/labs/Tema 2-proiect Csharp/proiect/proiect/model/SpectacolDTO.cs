using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiect.model
{
    public class SpectacolDTO
    {
        public String artistName { get; set; } 
        public TimeOnly time { get; set; }
        public String place { get; set; }
        public int availableSeats { get; set; }

        public SpectacolDTO(string ArtistName, TimeOnly Time, string Place, int AvailableSeats)
        {
            this.artistName = ArtistName;
            this.time = Time;
            this.place = Place;
            this.availableSeats = AvailableSeats;
        }
    }
}
