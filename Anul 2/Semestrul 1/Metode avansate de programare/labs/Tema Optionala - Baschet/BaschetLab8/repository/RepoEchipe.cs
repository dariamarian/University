using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BaschetLab8.domain;

namespace BaschetLab8.repository
{
    internal class RepoEchipe<ID> : AbstractRepo<ID, Echipa<ID>>
    {
        public RepoEchipe(string filePath) : base(filePath) { base.loadData(filePath); }
        public override Echipa<ID> extractEntity(string[] values)
        {
            ID id = (ID)Convert.ChangeType(values[0], typeof(ID));
            Echipa<ID> echipa = new Echipa<ID>(id, values[1]);
            return echipa;
        }

        public Echipa<ID> findByName(string nume)
        {
            return entities.Find(x => x.Nume.Equals(nume));
        }
    }
}
