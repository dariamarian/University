using client;
using Microsoft.VisualBasic.Logging;
using proiectSCcsharp.networking;
using proiectSCcsharp.services;
using proiectSCcsharp.server;
using System;
using System.Windows.Forms;

[assembly: log4net.Config.XmlConfigurator(ConfigFile = "Log4Net.config", Watch = true)]

namespace proiectSCcsharp.client
{
    internal static class Program
    {
        [STAThread]
        private static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            IService service = new ServicesRpcProxy("127.0.0.1", 55556);
            var ctrl = new ClientController(service);
            var win = new LogInForm(ctrl);
            Application.Run(win);
        }
    }
}