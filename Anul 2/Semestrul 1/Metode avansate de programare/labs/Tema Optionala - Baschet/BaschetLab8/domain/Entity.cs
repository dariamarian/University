using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BaschetLab8.domain
{
    internal class Entity<ID>
    {

        private ID id;
        public ID Id
        {
            get { return id; }
            set { id = value; }
        }
        public Entity(ID givenId)
        {
            id = givenId;
        }

        public override string? ToString()
        {
            return id.ToString() + " ";
        }
    }

}