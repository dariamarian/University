using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BaschetLab8.domain
{
    public enum Tip { Rezerva, Participant };
    internal class JucatorActiv<ID> : Entity<ID>
    {

        private ID idJucator;
        private ID idMeci;
        private int nrPuncteInscrise;
        private Tip tip;

        public JucatorActiv(ID id, ID idJucator, ID idMeci, int nrPuncteInscrise) : base(id)
        {
            this.idJucator = idJucator;
            this.idMeci = idMeci;
            this.nrPuncteInscrise = nrPuncteInscrise;
        }

        public ID IdJucator { get { return idJucator; } set { idJucator = value; } }
        public ID IdMeci { get { return idMeci; } set { idMeci = value; } }
        public int NrPuncteInscrise { get { return nrPuncteInscrise; } set { nrPuncteInscrise = value; } }
        public string Tip
        {
            get
            {
                return tip.ToString();
            }
            set
            {
                if (value.Equals("Rezerva"))
                    tip = domain.Tip.Rezerva;
                else tip = domain.Tip.Participant;
            }
        }

        public override string? ToString()
        {
            return base.ToString() + "ID jucator:" + idJucator.ToString() + " ID meci: " + idMeci.ToString() + " Nr. inscrieri: " + nrPuncteInscrise.ToString() + " Tip: " + Tip + "\n";

        }
    }
}
