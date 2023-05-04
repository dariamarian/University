using System;
using System.Data;
using System.Collections.Generic;
using System.Data.SQLite;

namespace ConnectionUtils
{
	public class SqliteConnectionFactory : ConnectionFactory
	{
		public override IDbConnection createConnection(IDictionary<string,string> props)
		{
			//Mono Sqlite Connection

			//String connectionString = "URI=C:\\Users\\daria\\OneDrive\\Desktop\\Faculta\\Anul 2\\sem 2\\Medii de proiectare si programare\\databases\\Spectacole.db,Version=3";
			String connectionString = props["ConnectionString"];
			Console.WriteLine("SQLite ---Se deschide o conexiune la  ... {0}", connectionString);
			return new SQLiteConnection(connectionString);

			// Windows SQLite Connection, fisierul .db ar trebuie sa fie in directorul debug/bin
			//String connectionString = "Data Source=Spectacole.db;Version=3";
			//return new SQLiteConnection(connectionString);
		}
	}
}
