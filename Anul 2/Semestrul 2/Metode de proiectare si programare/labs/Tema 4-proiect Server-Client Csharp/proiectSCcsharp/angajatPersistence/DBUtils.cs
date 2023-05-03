using System;
using System.Data;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Reflection;

namespace proiectSCcsharp.repository
{
    public static class DBUtils
    {
        private static IDbConnection _instance = null;

        public static IDbConnection getConnection(IDictionary<string, string> props)
        {
            if (_instance != null && _instance.State != ConnectionState.Closed) return _instance;
            _instance = GetNewConnection(props);
            _instance.Open();
            return _instance;
        }

        private static IDbConnection GetNewConnection(IDictionary<string, string> props)
        {
            return ConnectionFactory.GetInstance().CreateConnection(props);
        }
    }

    public abstract class ConnectionFactory
    {
        protected ConnectionFactory() { }

        private static ConnectionFactory _instance;

        public static ConnectionFactory GetInstance()
        {
            if (_instance != null) return _instance;
            var assembly = Assembly.GetExecutingAssembly();
            var types = assembly.GetTypes();
            foreach (var type in types)
            {
                if (type.IsSubclassOf(typeof(ConnectionFactory)))
                    _instance = (ConnectionFactory)Activator.CreateInstance(type);
            }
            return _instance;
        }
        public abstract IDbConnection CreateConnection(IDictionary<string, string> props);
    }

    public class SQLiteConnectionFactory : ConnectionFactory
    {
        public override IDbConnection CreateConnection(IDictionary<string, string> props)
        {
            var connectionString = props["ConnectionString"];
            Console.WriteLine(@"SQLite --- opens a connection to {0}", connectionString);
            return new SQLiteConnection(connectionString);
        }
    }
}
