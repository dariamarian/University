using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using log4net;
using proiect.model;
using tasks.repository;

namespace proiect.repository
{
    public class RepoSpectacol : IRepoSpectacol
    {
        private static readonly ILog logger = LogManager.GetLogger("RepoSpectacol");
        IDictionary<String, string> props;

        public RepoSpectacol(IDictionary<String, string> props)
        {
            logger.Info("Initializing RepoSpectacol class");
            this.props = props;
        }

        public Spectacol add(Spectacol entity)
        {
            logger.InfoFormat("Saving Spectacol...", entity);

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "INSERT INTO Spectacole(id_spectacol, artist_name, date, place, available_seats, sold_seats) VALUES (@i, @a, @d, @p, @as, @ss)";

                var paramId = command.CreateParameter();
                paramId.ParameterName = "@i";
                paramId.Value = entity.id;
                command.Parameters.Add(paramId);

                var nameParameter = command.CreateParameter();
                nameParameter.ParameterName = "@a";
                nameParameter.Value = entity.artistName;
                command.Parameters.Add(nameParameter);

                var dateParameter = command.CreateParameter();
                dateParameter.ParameterName = "@d";
                dateParameter.Value = entity.date;
                command.Parameters.Add(dateParameter);

                var placeParameter = command.CreateParameter();
                placeParameter.ParameterName = "@p";
                placeParameter.Value = entity.place;
                command.Parameters.Add(placeParameter);
                
                var availableParameter = command.CreateParameter();
                availableParameter.ParameterName = "@as";
                availableParameter.Value = entity.availableSeats;
                command.Parameters.Add(availableParameter);
                
                var soldParameter = command.CreateParameter();
                soldParameter.ParameterName = "@ss";
                soldParameter.Value = entity.soldSeats;
                command.Parameters.Add(soldParameter);

                var result = command.ExecuteNonQuery();

                return entity;
            }
        }
        public Spectacol remove(long aLong)
        {
            logger.InfoFormat("Deleting Spectacol...", aLong);

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "DELETE FROM Spectacole WHERE id_spectacol=@i";

                var idParameter = command.CreateParameter();
                idParameter.ParameterName = "@i";
                idParameter.Value = aLong;
                command.Parameters.Add(idParameter);

                var result = command.ExecuteNonQuery();

                return findElement(aLong);
            }
        }
        public void update(Spectacol spectacol, int nrSeats)
        {
            logger.InfoFormat("Updating Spectacol...", spectacol);
            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "UPDATE Spectacole SET available_seats=@a, sold_seats=@s WHERE id_spectacol = @i";

                var idParameter = command.CreateParameter();
                idParameter.ParameterName = "@i";
                idParameter.Value = spectacol.id;
                command.Parameters.Add(idParameter);

                var availableParameter = command.CreateParameter();
                availableParameter.ParameterName = "@a";
                availableParameter.Value = spectacol.availableSeats-nrSeats;
                command.Parameters.Add(availableParameter);

                var soldParameter = command.CreateParameter();
                soldParameter.ParameterName = "@s";
                soldParameter.Value = spectacol.soldSeats + nrSeats;
                command.Parameters.Add(soldParameter);

                var result= command.ExecuteNonQuery();
            }
        }
        public IEnumerable<Spectacol> getAll()
        {
            logger.InfoFormat("Finding all Spectacole...");

            IList<Spectacol> spectacole = new List<Spectacol>();

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Spectacole";

                using (var dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long id = dataReader.GetInt32(0);
                        string name = dataReader.GetString(1);
                        string date= dataReader.GetString(2);
                        string place = dataReader.GetString(3);
                        int available = dataReader.GetInt32(4);
                        int sold = dataReader.GetInt32(5);
                        Spectacol spectacol = new Spectacol(name, place,available,sold);
                        spectacol.id= id;
                        spectacol.setDate(date);
                        spectacole.Add(spectacol);
                    }
                }
            }
            return spectacole;
        }

        public Spectacol findElement(long id)
        {
            logger.InfoFormat("Entering findElement with value {0}", id);

            Spectacol spectacol = null;

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Spectacole WHERE id_spectacol = @i";

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
                        string date = dataReader.GetString(2);
                        string place = dataReader.GetString(3);
                        int available = dataReader.GetInt32(4);
                        int sold = dataReader.GetInt32(5);
                        spectacol = new Spectacol(name, place, available, sold);
                        spectacol.id = id2;
                        spectacol.setDate(date);
                        logger.InfoFormat("Found spectacol: {0}", spectacol);
                        return spectacol;
                    }
                }
            }

            return null;
        }
    }
}
