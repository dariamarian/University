using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BaschetLab8.domain;

namespace BaschetLab8.repository
{
    internal abstract class AbstractRepo<ID, E> : Repository<ID, E> where E : Entity<ID>
    {
        private string filePath;
        protected List<E> entities = new List<E>();
        public AbstractRepo(string filePath)
        {
            this.filePath = filePath;

        }

        public IEnumerable<E> GetAll()
        {
            loadData(filePath);
            return entities;
        }

        public abstract E extractEntity(string[] values);
        protected void loadData(string filePath)
        {
            entities.Clear();
            using (StreamReader reader = new StreamReader(filePath))
            {
                while (!reader.EndOfStream)
                {
                    string line = reader.ReadLine();
                    string[] values = line.Split(',');
                    E entity = extractEntity(values);
                    entities.Add(entity);
                }
            }
        }
        public E findByID(ID id)
        {
            return entities.Find(entity => entity.Id.Equals(id));
        }

        public string testFunction() { return filePath; }

    }
}
