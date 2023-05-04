using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.domain
{
    [Serializable]
    public class SpectacolDTO
    {
        public string artistName { get; set; } 
        public string time { get; set; }
        public string place { get; set; }
        public int availableSeats { get; set; }

        public SpectacolDTO(string ArtistName, string Time, string Place, int AvailableSeats)
        {
            this.artistName = ArtistName;
            this.time = Time;
            this.place = Place;
            this.availableSeats = AvailableSeats;
        }
    }
}
