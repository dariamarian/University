using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BaschetLab8.domain;
using BaschetLab8.repository;

namespace BaschetLab8.service
{
    internal class Service<ID>
    {
        private RepoMeciuri<ID> repoMeciuri;
        private RepoJucatori<ID> repoJucatori;
        private RepoEchipe<ID> repoEchipe;
        private RepoJucatoriActivi<ID> repoJucatoriActivi;

        public Service(RepoMeciuri<ID> repoMeciuri, RepoJucatori<ID> repoJucatori, RepoEchipe<ID> repoEchipe, RepoJucatoriActivi<ID> repoJucatoriActivi)
        {
            this.repoMeciuri = repoMeciuri;
            this.repoJucatori = repoJucatori;
            this.repoEchipe = repoEchipe;
            this.repoJucatoriActivi = repoJucatoriActivi;
        }
        public ArrayList testFunction()
        {
            ArrayList filepaths = new ArrayList();
            filepaths.Add(repoMeciuri.testFunction());
            filepaths.Add(repoJucatori.testFunction());
            filepaths.Add(repoEchipe.testFunction());
            filepaths.Add(repoJucatoriActivi.testFunction());

            return filepaths;
        }


        //cerinta 1
        public List<Jucator<ID>> getJucatoriPentruEchipa(string nume_echipa)
        {
            List<Jucator<ID>> jucatori = (List<Jucator<ID>>)repoJucatori.GetAll();
            List<Jucator<ID>> jucatoriPentruEchipa = new List<Jucator<ID>>();
            jucatoriPentruEchipa = jucatori.FindAll(x => x.Team.Nume.Equals(nume_echipa));
            return jucatoriPentruEchipa;

        }

        //cerinta 2

        public List<JucatorActiv<ID>> getJucatoriActiviPentruEchipaLaMeci(string nume_echipa, ID id_meci)
        {

            List<JucatorActiv<ID>> jucatoriActivi = (List<JucatorActiv<ID>>)repoJucatoriActivi.GetAll();
            List<JucatorActiv<ID>> jucatoriPtEchipaLaMeci = new List<JucatorActiv<ID>>();

            Meci<ID> meci = repoMeciuri.findByID(id_meci);

            foreach (var jucator in jucatoriActivi)
            {
                if (jucator.IdMeci.Equals(id_meci) && repoJucatori.findByID(jucator.IdJucator).Team.Nume.Equals(nume_echipa))
                    jucatoriPtEchipaLaMeci.Add(jucator);
            }
            return jucatoriPtEchipaLaMeci;

        }

        //cerinta 3

        public List<Meci<ID>> getMeciuriBetweenDates(string date1string, string date2string)
        {
            DateTime date1 = DateTime.Parse(date1string);
            DateTime date2 = DateTime.Parse(date2string);
            List<Meci<ID>> meciuri = (List<Meci<ID>>)repoMeciuri.GetAll();
            List<Meci<ID>> meciuriInPerioada = new List<Meci<ID>>();

            meciuriInPerioada = meciuri.FindAll(meci => date1.CompareTo(meci.Time) <= 0 && date2.CompareTo(meci.Time) >= 0);

            return meciuriInPerioada;

        }

        //cerinta 4
        public string getScorPentruMeci(ID idMeci)
        {
            Meci<ID> meci = repoMeciuri.findByID(idMeci);
            List<JucatorActiv<ID>> jucatoriActivi = (List<JucatorActiv<ID>>)repoJucatoriActivi.GetAll();
            List<JucatorActiv<ID>> jucatoriInMeci = new List<JucatorActiv<ID>>();
            jucatoriInMeci = jucatoriActivi.FindAll(jucator => jucator.IdMeci.Equals(idMeci));

            Dictionary<string, int> scor = new Dictionary<string, int>();


            scor.Add(meci.Echipa1.Nume, 0);
            scor.Add(meci.Echipa2.Nume, 0);


            foreach (JucatorActiv<ID> jucator in jucatoriInMeci)
            {
                string numeEchipa = repoJucatori.findByID(jucator.IdJucator).Team.Nume;
                scor[numeEchipa] += jucator.NrPuncteInscrise;
            }

            string scor_string = scor.Keys.ToArray()[0] + "-" + scor.Keys.ToArray()[1] + " | ";
            foreach (string key in scor.Keys.ToArray())
            {
                scor_string += scor[key].ToString() + " ";
            }
            scor_string += "\n";
            return scor_string;
        }

        public List<Echipa<ID>> getEchipe()
        {
            return (List<Echipa<ID>>)repoEchipe.GetAll();
        }
        public List<JucatorActiv<ID>> getJucatoriActivi()
        {
            return (List<JucatorActiv<ID>>)repoJucatoriActivi.GetAll();
        }
        public List<Meci<ID>> getMeciuri()
        {
            return (List<Meci<ID>>)repoMeciuri.GetAll();
        }
        public List<Jucator<ID>> getJucatori()
        {
            return (List<Jucator<ID>>)repoJucatori.GetAll();
        }

    }
}
