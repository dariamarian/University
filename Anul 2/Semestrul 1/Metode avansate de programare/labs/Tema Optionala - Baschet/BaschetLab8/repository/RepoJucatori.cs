using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BaschetLab8.domain;

namespace BaschetLab8.repository
{
    internal class RepoJucatori<ID> : AbstractRepo<ID, Jucator<ID>>
    {
        private RepoEchipe<ID> repoEchipe;
        public RepoJucatori(string filePath, RepoEchipe<ID> repoEchipe) : base(filePath)
        {
            this.repoEchipe = repoEchipe;
            base.loadData(filePath);
        }

        public override Jucator<ID> extractEntity(string[] values)
        {
            ID id = (ID)Convert.ChangeType(values[0], typeof(ID));
            Echipa<ID> echipa = repoEchipe.findByName(values[3]);
            Jucator<ID> jucator = new Jucator<ID>(id, values[1], values[2], echipa);
            return jucator;
        }
    }
}
