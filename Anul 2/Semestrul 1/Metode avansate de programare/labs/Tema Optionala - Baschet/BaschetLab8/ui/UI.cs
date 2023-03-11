using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BaschetLab8.domain;
using BaschetLab8.service;

namespace BaschetLab8.ui
{
    internal class UI<ID>
    {
        private Service<ID> service;
        public UI(Service<ID> service)
        {
            this.service = service;
            run();
        }
        private delegate void executeTaskDelegate();

        private void run()
        {
            executeTaskDelegate taskExecuter;
            while (true)
            {
                showMenu();
                Console.Write("Introduceti optiunea:");
                string option = Console.ReadLine();

                switch (option)
                {
                    case "1":
                        taskExecuter = new executeTaskDelegate(task1);
                        break;
                    case "2":
                        taskExecuter = new executeTaskDelegate(task2);
                        break;
                    case "3":
                        taskExecuter = new executeTaskDelegate(task3);
                        break;
                    case "4":
                        taskExecuter = new executeTaskDelegate(task4);
                        break;
                    case "0":
                        Environment.Exit(0);
                        return;
                        break;
                    default:
                        taskExecuter = new executeTaskDelegate(showMenu);
                        break;
                }
                taskExecuter();

            }
        }
        private void showMenu()
        {
            Console.WriteLine();
            Console.WriteLine("1. Sa se afiseze toti jucatorii unei echipe dată");
            Console.WriteLine("2. Sa se afiseze toti jucatorii activi ai unei echipe de la un anumit meci");
            Console.WriteLine("3. Sa se afiseze toate meciurile dintr-o anumita perioada calendaristica");
            Console.WriteLine("4. Sa se determine si sa se afiseze scorul de la un anumit meci");
            Console.WriteLine("0. Exit ");
        }
        private void showTeams()
        {
            List<Echipa<ID>> echipe = service.getEchipe();
            foreach (Echipa<ID> echipa in echipe)
            {
                Console.Write(echipa);
            }
        }
        private void showMeciuri()
        {
            List<Meci<ID>> meciuri = service.getMeciuri();
            foreach (Meci<ID> meci in meciuri)
            {
                Console.WriteLine(meci);
            }
        }
        private void task1()
        {
            showTeams();
            Console.Write("Introduceti numele echipei: ");
            string echipa = Console.ReadLine();
            List<Jucator<ID>> jucatoriEchipa = service.getJucatoriPentruEchipa(echipa);
            foreach (var jucator in jucatoriEchipa)
            {
                Console.Write(jucator);
            }
        }
        private void task2()
        {
            showMeciuri();
            Console.WriteLine("Introduceti id-ul meciului: ");
            string idMeciS = Console.ReadLine();
            //showTeams();
            Console.WriteLine("Introduceti numele echipei: ");
            string echipa = Console.ReadLine();
            try
            {
                ID idMeci = (ID)Convert.ChangeType(idMeciS, typeof(ID));
                List<JucatorActiv<ID>> jucatoriActiviPtMeci = service.getJucatoriActiviPentruEchipaLaMeci(echipa, idMeci);
                foreach (var jucator in jucatoriActiviPtMeci)
                {
                    Console.Write(jucator);
                }
            }
            catch (Exception ex) { Console.WriteLine("Input invalid!\n"); }
        }

        private void task3()
        {

            Console.WriteLine("Introduceti doua date calendaristice (DD.MM.YYYY HH:MM:SS)");
            Console.WriteLine("Data 1: ");
            string data1 = Console.ReadLine();
            Console.WriteLine("Data 2: ");
            string data2 = Console.ReadLine();

            try
            {
                List<Meci<ID>> meciuriIntreDate = service.getMeciuriBetweenDates(data1, data2);
                foreach (var meci in meciuriIntreDate)
                {
                    Console.WriteLine(meci);
                }

            }
            catch (Exception ex)
            {
                Console.WriteLine("Input invalid!\n");
            }
        }
        private void task4()
        {
            showMeciuri();
            Console.WriteLine("Introduceti id-ul meciului: ");
            string idMeciS = Console.ReadLine();
            ID idMeci = (ID)Convert.ChangeType(idMeciS, typeof(ID));
            string scor = service.getScorPentruMeci(idMeci);
            Console.WriteLine(scor);
        }

    }
}
