using System;
using System.Collections.Generic;
using System.Configuration;
using proiectSCcsharp.repository;
using proiectSCcsharp.repository.interfaces;
using proiectSCcsharp.services;
using proiectSCcsharp.networking.utils;
using proiectSCcsharp.domain;
using proiectSCcsharp.server;
using proiectSCcsharp.domain.validators;

namespace proiectSCcharp.server
{
    public class StartRPCServer
    {
        [STAThread]
        private static string GetConnectionStringByName(string name)
        {
            string returnValue = null;
            var settings = ConfigurationManager.ConnectionStrings[name];
            if (settings != null)
                returnValue = settings.ConnectionString;
            return returnValue;
        }

        private static void Main(string[] args)
        {

            IDictionary<string, string> props = new SortedList<string, string>();
            props.Add("ConnectionString", GetConnectionStringByName("spectacoleDB"));
            Validator<Angajat> angajatValidator = new AngajatValidator();
            Validator<Spectacol> spectacolValidator = new SpectacolValidator();
            Validator<Bilet> biletValidator = new BiletValidator();
            RepoAngajat angajatRepository = new RepoAngajat(props);
            RepoSpectacol spectacolRepository = new RepoSpectacol(props);
            RepoBilet biletRepository = new RepoBilet(props);
            IService service = new Service(angajatValidator,spectacolValidator,biletValidator,angajatRepository,spectacolRepository,biletRepository);
            var server = new RpcConcurrentServer("127.0.0.1", 55556, service);
            try
            {
                server.Start();
                Console.WriteLine(@"Server started ...");
                Console.ReadLine();
            }
            catch (ServerException e)
            {
                Console.Error.WriteLine("Error starting the server" + e.Message);
            }
            finally
            {
                try
                {
                    server.Stop();
                }
                catch (ServerException e)
                {
                    Console.Error.WriteLine("Error stopping server " + e.Message);
                }
            }

        }
    }

}