using System;
using System.Collections.Generic;
using client;
using proiectSCcsharp.domain;
using proiectSCcsharp.services;
using proiectSCcsharp.server;

namespace proiectSCcsharp.client
{
    public class ClientController : IObserver
    {
        public event EventHandler<EmployeeEventArgs> UpdateEvent;

        public ClientController(IService service)
        {
            this.Service = service;
            Employee = null;
        }

        public IService Service { get; }
        public Angajat Employee { get; private set; }

        public void login(string username, string password)
        {
            var tryEmployee = new Angajat(1,"", username, password);
            var employee = Service.login(tryEmployee, this);
            Console.WriteLine(@"Login succeeded ....");
            Employee = employee;
            Console.WriteLine(@"Current employee {0}", employee);
        }

        public void logout()
        {
            Console.WriteLine(@"Ctrl logout");
            Service.logout(Employee);
            Employee = null;
        }

        protected virtual void OnUserEvent(EmployeeEventArgs e)
        {
            if (UpdateEvent == null) return;
            Console.WriteLine(@"Update Event called");
            UpdateEvent(this, e);
        }

        public void BiletAdded(Bilet ticket)
        {
            Console.WriteLine(@"Ticket bought " + ticket);
            var userArgs = new EmployeeEventArgs(EmployeeEvent.BILET_CUMPARAT, ticket);
            OnUserEvent(userArgs);
        }

        public List<Spectacol> getAllSpectacole()
        {
            return Service.getAllSpectacole();

        }
        

        public void addBilet(Bilet bilet)
        {
            Service.addBilet(bilet);
        }
    }
}