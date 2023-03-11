using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BaschetLab8.domain;

namespace BaschetLab8.repository
{
    internal class RepoMeciuri<ID> : AbstractRepo<ID, Meci<ID>>
    {
        private RepoEchipe<ID> repoEchipe;
        public RepoMeciuri(string filePath, RepoEchipe<ID> repoEchipe) : base(filePath)
        {
            this.repoEchipe = repoEchipe;
            base.loadData(filePath);
        }

        public override Meci<ID> extractEntity(string[] values)
        {
            ID id = (ID)Convert.ChangeType(values[0], typeof(ID));
            Echipa<ID> echipa1 = repoEchipe.findByName(values[1]);
            Echipa<ID> echipa2 = repoEchipe.findByName(values[2]);
            Meci<ID> meci = new Meci<ID>(id, echipa1, echipa2, DateTime.Parse(values[3]));
            return meci;
        }
    }
}
