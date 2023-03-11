using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BaschetLab8.domain;

namespace BaschetLab8.repository
{
    internal class RepoJucatoriActivi<ID> : AbstractRepo<ID, JucatorActiv<ID>>
    {
        public RepoJucatoriActivi(string filePath) : base(filePath)
        {
            base.loadData(filePath);
        }

        public override JucatorActiv<ID> extractEntity(string[] values)
        {
            ID id = (ID)Convert.ChangeType(values[0], typeof(ID));
            ID idJucator = (ID)Convert.ChangeType(values[1], typeof(ID));
            ID idMeci = (ID)Convert.ChangeType(values[2], typeof(ID));
            int puncte = Convert.ToInt32(values[3]);
            JucatorActiv<ID> jucatorActiv = new JucatorActiv<ID>(id, idJucator, idMeci, puncte);
            jucatorActiv.Tip = values[4];
            return jucatorActiv;
        }


    }
}
