using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;
using proiectSCcsharp.repository.interfaces;
using proiectSCcsharp.domain;

namespace proiectSCcsharp.repository
{
    public class RepoBilet : IRepoBilet
    {
        private static readonly ILog logger = LogManager.GetLogger("RepoBilet");
        IDictionary<String, string> props;

        public RepoBilet(IDictionary<string, string> props)
        {
            logger.Info("Initializing RepoBilet class");
            this.props = props;
        }

        public Bilet add(Bilet entity)
        {
            logger.InfoFormat("Saving Bilet...", entity);

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "INSERT INTO Bilete(id_bilet, cumparator_name, id_spectacol, nr_seats) VALUES (@i, @c, @ids, @n)";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@i";
                paramId.Value = null;
                command.Parameters.Add(paramId);

                var nameParameter = command.CreateParameter();
                nameParameter.ParameterName = "@c";
                nameParameter.Value = entity.cumparatorName;
                command.Parameters.Add(nameParameter);

                var idsParameter = command.CreateParameter();
                idsParameter.ParameterName = "@ids";
                idsParameter.Value = entity.idSpectacol;
                command.Parameters.Add(idsParameter);

                var seatsParameter = command.CreateParameter();
                seatsParameter.ParameterName = "@n";
                seatsParameter.Value = entity.nrSeats;
                command.Parameters.Add(seatsParameter);

                var result = command.ExecuteNonQuery();

                return entity;
            }
        }
        public Bilet remove(long aLong)
        {
            logger.InfoFormat("Deleting Bilet...", aLong);

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "DELETE FROM Bilete WHERE id_bilet=@i";

                var idParameter = command.CreateParameter();
                idParameter.ParameterName = "@i";
                idParameter.Value = aLong;
                command.Parameters.Add(idParameter);

                var result = command.ExecuteNonQuery();

                return findElement(aLong);
            }
        }
        public List<Bilet> getAll()
        {
            logger.InfoFormat("Finding all Bilete...");

            List<Bilet> bilete = new List<Bilet>();

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Bilete";

                using (var dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long id = dataReader.GetInt64(0);
                        string name = dataReader.GetString(1);
                        long ids = dataReader.GetInt64(2);
                        int seats = dataReader.GetInt32(3);
                        Bilet bilet = new Bilet(id, name, ids, seats);
                        bilete.Add(bilet);
                    }
                }
            }

            return bilete;
        }

        public Bilet findElement(long id)
        {
            logger.InfoFormat("Entering findElement with value {0}", id);

            Bilet bilet = null;

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Bilete WHERE id_bilet = @i";

                var idParameter = command.CreateParameter();
                idParameter.ParameterName = "@i";
                idParameter.Value = id;
                command.Parameters.Add(idParameter);

                using (var dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long id2 = dataReader.GetInt64(0);
                        string name = dataReader.GetString(1);
                        long ids = dataReader.GetInt64(2);
                        int seats = dataReader.GetInt32(3);
                        bilet = new Bilet(id2, name, ids, seats);
                        logger.InfoFormat("Found bilet: {0}", bilet);
                        return bilet;
                    }
                }
            }
            return null;
        }
    }
}
