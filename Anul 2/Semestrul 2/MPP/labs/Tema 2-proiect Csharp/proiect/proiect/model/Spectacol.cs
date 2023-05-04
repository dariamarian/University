using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiect.model
{
    public class Spectacol : Entity<long>
    {
        public string artistName { get; set; }
        public DateTime date { get; set; }
        public string place { get; set; }
        public int availableSeats { get; set; }
        public int soldSeats { get; set; }

        public Spectacol(string ArtistName, string Place, int AvailableSeats, int SoldSeats)
        {
            artistName = ArtistName;
            //date = Date;
            place = Place;
            availableSeats = AvailableSeats;
            soldSeats = SoldSeats;
        }
        public string getDateString() 
        { 
            return date.ToString("dd-MM-yyyy HH:mm"); 
        }
        public void setDate(String datee)
        {
            date = DateTime.ParseExact(datee, "dd-MM-yyyy HH:mm",null);
        }
        public override string ToString()
        {
            return artistName + " | " + date + " | " + place + " | " + availableSeats + " | " + soldSeats;
        }
    }
}
