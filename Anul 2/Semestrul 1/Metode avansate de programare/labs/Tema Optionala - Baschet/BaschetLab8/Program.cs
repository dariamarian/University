using BaschetLab8.domain;
using BaschetLab8.repository;
using BaschetLab8.service;
using BaschetLab8.ui;
using System;

class Application
{
    public static void Main(String[] args)
    {
        string filepathJucatori = "C:\\Users\\daria\\OneDrive\\Desktop\\Faculta\\Anul 2\\sem 1\\Metode Avansate de Programare\\Lab\\BaschetLab8\\BaschetLab8\\files\\jucatori.csv";
        string filepathJucatoriActivi = "C:\\Users\\daria\\OneDrive\\Desktop\\Faculta\\Anul 2\\sem 1\\Metode Avansate de Programare\\Lab\\BaschetLab8\\BaschetLab8\\files\\jucatoriActivi.csv";
        string filepathMeci = "C:\\Users\\daria\\OneDrive\\Desktop\\Faculta\\Anul 2\\sem 1\\Metode Avansate de Programare\\Lab\\BaschetLab8\\BaschetLab8\\files\\meciuri.csv";
        string filepathEchipe = "C:\\Users\\daria\\OneDrive\\Desktop\\Faculta\\Anul 2\\sem 1\\Metode Avansate de Programare\\Lab\\BaschetLab8\\BaschetLab8\\files\\echipe.csv";

        AbstractRepo<int, Echipa<int>> echipaRepo = new RepoEchipe<int>(filepathEchipe);
        AbstractRepo<int, Jucator<int>> jucatorRepo = new RepoJucatori<int>(filepathJucatori, (RepoEchipe<int>)echipaRepo);
        AbstractRepo<int, Meci<int>> repoMeciuri = new RepoMeciuri<int>(filepathMeci, (RepoEchipe<int>)echipaRepo);
        AbstractRepo<int, JucatorActiv<int>> jucatorActivRepo = new RepoJucatoriActivi<int>(filepathJucatoriActivi);
        Service<int> service = new Service<int>((RepoMeciuri<int>)repoMeciuri, (RepoJucatori<int>)jucatorRepo, (RepoEchipe<int>)echipaRepo, (RepoJucatoriActivi<int>)jucatorActivRepo);
        UI<int> ui = new UI<int>(service);

        //task 1-Chicaco Bulls/San Antonio Spurs/Indiana Pacers
        //task 2-meci 1-San Antonio Spurs
        //task 3-12.12.2022, 5.1.2023
        //task 4-1/2
    }
}