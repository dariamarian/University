using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.domain
{
    [Serializable]
    public class Spectacol : Entity<long>
    {
        public string artistName { get; set; }
        public string date { get; set; }
        public string time { get; set; }
        public string place { get; set; }
        public int availableSeats { get; set; }
        public int soldSeats { get; set; }

        public Spectacol(long id,string ArtistName, string Date, string Time, string Place, int AvailableSeats, int SoldSeats): base(id)
        {
            artistName = ArtistName;
            date = Date;
            time = Time;
            place = Place;
            availableSeats = AvailableSeats;
            soldSeats = SoldSeats;
        }
        public override string ToString()
        {
            return artistName + " | " + date + " | " + time + " | " + place + " | " + availableSeats + " | " + soldSeats;
        }
    }
}
