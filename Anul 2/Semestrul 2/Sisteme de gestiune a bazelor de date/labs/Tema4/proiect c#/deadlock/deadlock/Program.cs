using System;
using System.Data.SqlClient;
using System.Threading;

namespace deadlock
{
    class Program
    {
        static string connectionString = "Server=LAPTOP-DARIA;Database=Restaurant;Integrated Security = true; TrustServerCertificate=true;";
        static int nrRetries = 2;

        static void Main(string[] args)
        {
            Thread t1 = new Thread(new ThreadStart(Transaction1));
            Thread t2 = new Thread(new ThreadStart(Transaction2));

            t1.Start(); 
            t2.Start();
            t1.Join(); 
            t2.Join();

            Console.WriteLine("Press ENTER to quit...");
            Console.ReadKey();
        }

        static void Transaction1()
        {
            int nrTries = 0;
            while (!Transaction1_Run())
            {
                nrTries++;
                if (nrTries >= nrRetries)
                    break;
            }
            if (nrTries == nrRetries)
                Console.WriteLine("Transaction 1 aborted.");
        }

        static void Transaction2()
        {
            int nrTries = 0;
            while (!Transaction2_Run())
            {
                nrTries++;
                if (nrTries >= nrRetries)
                    break;
            }
            if (nrTries == nrRetries)
                Console.WriteLine("Transaction 2 aborted.");
        }

        static bool Transaction1_Run()
        {
            bool success = false;

            Console.WriteLine("Transaction 1 started...");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                SqlCommand command = connection.CreateCommand();
                try
                {
                    connection.Open();
                    command.Connection = connection;
                    command.CommandText = "EXECUTE Deadlock_T1_C#";
                    command.ExecuteNonQuery();
                    success = true;
                    Console.WriteLine("Transaction 1 complete");
                }

                catch (SqlException ex)
                {
                    if (ex.Number == 1205)
                    {
                        Console.WriteLine("Transaction 1: commit exception type: {0}", ex.GetType());
                        Console.WriteLine("Message: {0}", ex.Message);
                    }

                }
                return success;
            }
        }

        static bool Transaction2_Run()
        {
            bool success = false;

            Console.WriteLine("Transaction 2 started...");

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                SqlCommand command = connection.CreateCommand();
                try
                {
                    connection.Open();
                    command.Connection = connection;
                    command.CommandText = "EXECUTE Deadlock_T2_C#";
                    command.ExecuteNonQuery();
                    success = true;
                    Console.WriteLine("Transaction 2 complete");
                }

                catch (SqlException ex)
                {
                    if (ex.Number == 1205)
                    {
                        Console.WriteLine("Transaction 2: commit exception type: {0}", ex.GetType());
                        Console.WriteLine("Message: {0}", ex.Message);
                    }

                }
                return success;
            }
        }
    }
}
