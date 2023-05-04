using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using proiectSCcsharp.repository.interfaces;
using proiectSCcsharp.domain;
using log4net;
using log4net.Repository.Hierarchy;
using System.Data.SQLite;

namespace proiectSCcsharp.repository
{
    public class RepoAngajat : IRepoAngajat
    {
        private static readonly ILog logger = LogManager.GetLogger("RepoAngajat");

        IDictionary<String, string> props;

        public RepoAngajat(IDictionary<string, string> props)
        {
            logger.Info("Initializing RepoAngajat class");
            this.props = props;
        }

        public Angajat add(Angajat entity)
        {
            logger.InfoFormat("Saving angajat...", entity);

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "INSERT INTO Angajati(id_angajat, name, username, password) VALUES (@i, @n, @u, @p)";
                
                var paramId = command.CreateParameter();
                paramId.ParameterName = "@i";
                paramId.Value = null;
                command.Parameters.Add(paramId);

                var nameParameter = command.CreateParameter();
                nameParameter.ParameterName = "@n";
                nameParameter.Value = entity.name;
                command.Parameters.Add(nameParameter);

                var usernameParameter = command.CreateParameter();
                usernameParameter.ParameterName = "@u";
                usernameParameter.Value = entity.username;
                command.Parameters.Add(usernameParameter);
                
                var passParameter = command.CreateParameter();
                passParameter.ParameterName = "@p";
                passParameter.Value = entity.password;
                command.Parameters.Add(passParameter);

                var result = command.ExecuteNonQuery();

                return entity;
            }
        }
        public Angajat remove(long aLong)
        {
            logger.InfoFormat("Deleting angajat...", aLong);

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "DELETE FROM Angajati WHERE id_angajat=@i";

                var idParameter = command.CreateParameter();
                idParameter.ParameterName = "@i";
                idParameter.Value = aLong;
                command.Parameters.Add(idParameter);

                var result = command.ExecuteNonQuery();

                return findElement(aLong);
            }
        }
        public List<Angajat> getAll()
        {
            logger.InfoFormat("Finding all angajati...");

            List<Angajat> angajati = new List<Angajat>();

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Angajati";

                using (var dataReader = command.ExecuteReader())
                {
                    while (dataReader.Read())
                    {
                        long id = dataReader.GetInt64(0);
                        string name = dataReader.GetString(1);
                        string username = dataReader.GetString(2);
                        string password = dataReader.GetString(3);
                        Angajat angajat = new Angajat(id,name, username, password);
                        angajati.Add(angajat);
                    }
                }
            }

            return angajati;
        }

        public Angajat findElement(long id)
        {
            logger.InfoFormat("Entering findElement with value {0}", id);

            Angajat angajat = null;

            var connection = DBUtils.getConnection(props);

            using (var command = connection.CreateCommand())
            {
                command.CommandText = "SELECT * FROM Angajati WHERE id_angajat = @i";

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
                        string username = dataReader.GetString(2);
                        string password = dataReader.GetString(3);
                        angajat = new Angajat(id2, name, username, password);
                        logger.InfoFormat("Found angajat: {0}", angajat);
                        return angajat;
                    }
                }
            }
            return null;
        }
        public Angajat AuthenticateAngajat(string username, string password)
        {
            logger.InfoFormat("find a employee by username and password");
            Angajat angajat = null;
            var connection = DBUtils.getConnection(props);
            using (var command = new SQLiteCommand("select * from Angajati where username = @username and password = @password",
                      connection as SQLiteConnection))
            {
                command.Parameters.AddWithValue("@username", username);
                command.Parameters.AddWithValue("@password", password);
                using (var dataReader = command.ExecuteReader())
                    if (dataReader.Read())
                    {
                        long id2 = dataReader.GetInt64(0);
                        string name = dataReader.GetString(1);
                        string usernamee = dataReader.GetString(2);
                        string passwordd = dataReader.GetString(3);
                        angajat = new Angajat(id2,name, usernamee, passwordd);
                        logger.Info(angajat);
                        return angajat;
                    }
            }
            logger.InfoFormat("authentication failed");
            return null;
        }
    }
}
