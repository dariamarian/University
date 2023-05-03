using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace proiectSCcsharp.domain
{
    [Serializable]
    public class Angajat : Entity<long>
    {
        public string name { get; set; }
        public string username { get; set; }
        public string password { get; set; }

        public Angajat(string Name, string Username, string Password)
        {
            name = Name;
            username = Username;
            password = Password;
        }
        public override string ToString()
        {
            return name + " | " + username + " | " + password;
        }
    }
}
