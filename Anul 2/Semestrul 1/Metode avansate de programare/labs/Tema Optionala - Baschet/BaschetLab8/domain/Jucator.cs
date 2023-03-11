using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BaschetLab8.domain
{
    internal class Jucator<ID> : Elev<ID>
    {
        private Echipa<ID> team;
        public Echipa<ID> Team { get { return team; } set { team = value; } }
        public Jucator(ID id, string nume, string scoala, Echipa<ID> echipa) : base(id, nume, scoala)
        {
            this.team = echipa;
        }

        public override string? ToString()
        {
            return base.ToString() + " " + team.ToString() + "\n";
        }
    }
}
