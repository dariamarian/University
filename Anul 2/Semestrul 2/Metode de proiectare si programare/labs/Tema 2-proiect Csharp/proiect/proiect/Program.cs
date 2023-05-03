using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using proiect.repository;
using proiect.model;
using proiect.service;
using log4net.Config;
using System.Configuration;
using Microsoft.VisualBasic.ApplicationServices;
using static System.Windows.Forms.VisualStyles.VisualStyleElement;

[assembly: log4net.Config.XmlConfigurator(ConfigFile = "Log4Net.config", Watch = true)]

namespace proiect
{
    class Program
    {
        [STAThread]

        static void Main(string[] args)
        {
            Console.WriteLine("Configuration Settings for DB {0}", GetConnectionStringByName("spectacoleDB"));
            IDictionary<String, string> props = new SortedList<String, String>();
            props.Add("ConnectionString", GetConnectionStringByName("spectacoleDB"));

            Console.WriteLine("Repository DB ...");
            RepoAngajat repoAngajat = new RepoAngajat(props);
            RepoSpectacol repoSpectacol = new RepoSpectacol(props);
            RepoBilet repoBilet = new RepoBilet(props);

            Service service = new Service(repoAngajat, repoSpectacol, repoBilet);

            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LogInForm(service));
        }

        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings = ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }
}
