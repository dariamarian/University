using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BaschetLab8.domain
{
    internal class Elev<ID> : Entity<ID>
    {
        private string nume;
        private string scoala;
        public string Nume { get { return nume; } set { nume = value; } }
        public string Scoala { get { return scoala; } set { scoala = value; } }
        public Elev(ID id, string nume, string scoala) : base(id)
        {
            this.nume = nume;
            this.scoala = scoala;
        }
        public override string? ToString()
        {
            return base.ToString() + " " + nume + " " + scoala + "\n";
        }
    }
}
