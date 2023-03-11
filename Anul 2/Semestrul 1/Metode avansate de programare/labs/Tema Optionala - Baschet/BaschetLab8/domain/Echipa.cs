using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BaschetLab8.domain
{
    internal class Echipa<ID> : Entity<ID>
    {
        private string nume;
        public string Nume { get { return nume; } set { nume = value; } }

        public Echipa(ID id, string nume) : base(id)
        {
            this.nume = nume;
        }

        public override string? ToString()
        {
            return base.ToString() + " " + nume + "\n";
        }
    }
}

    
