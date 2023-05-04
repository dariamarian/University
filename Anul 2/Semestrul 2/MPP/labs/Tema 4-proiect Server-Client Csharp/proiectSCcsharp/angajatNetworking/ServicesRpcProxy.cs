using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Threading;
using proiectSCcsharp.services;
using proiectSCcsharp.domain;
using log4net;

namespace proiectSCcsharp.networking
{

    public class ServicesRpcProxy : IService
    {
        private static readonly ILog Logger = LogManager.GetLogger("ServicesRpcProxy");
        private readonly string host;
        private readonly int port;
        private IObserver angajatObserver;
        private NetworkStream stream;
        private IFormatter formatter;
        private TcpClient connection;
        private readonly Queue<Response> responses;
        private volatile bool finished;
        private EventWaitHandle waitHandle;

        public ServicesRpcProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<Response>();
        }

        private void CloseConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                waitHandle.Close();
                angajatObserver = null;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void SendRequest(Request request)
        {
            try
            {
                formatter.Serialize(stream, request);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new MyException("Error sending object " + e);
            }
        }

        private Response ReadResponse()
        {
            Response response = null;
            try
            {
                waitHandle.WaitOne();
                lock (responses)
                {
                    response = responses.Dequeue();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
            return response;
        }

        private void InitializeConnection()
        {
            try
            {
                connection = new TcpClient(host, port);
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                finished = false;
                waitHandle = new AutoResetEvent(false);
                StartReader();
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void StartReader()
        {
            var tw = new Thread(Run);
            tw.Start();
        }

        private void HandleUpdate(Response response)
        {
            if (response.Type != ResponseType.SOLD_BILET) return;
            var bilet = (Bilet)response.Data;
            Logger.Info("Bilet sold " + bilet);
            try
            {
                angajatObserver.BiletAdded(bilet);
            }
            catch (MyException e)
            {
                Logger.Error("Error handle update: " + e);
            }
        }

        private static bool IsUpdate(Response response)
        {
            return response.Type == ResponseType.SOLD_BILET;
        }

        public Angajat login(Angajat angajat, IObserver angajatObserver)
        {
            InitializeConnection();
            var req = new Request.Builder().Type(RequestType.LOGIN).Data(angajat).Build();
            SendRequest(req);
            var response = ReadResponse();
            switch (response.Type)
            {
                case ResponseType.OK:
                    this.angajatObserver = angajatObserver;
                    Logger.Info("Logged in");
                    return (Angajat)response.Data;
                case ResponseType.ERROR:
                    {
                        Logger.Error("Error logging in" + response.Data.ToString());
                        var err = response.Data.ToString();
                        CloseConnection();
                        throw new MyException(err);
                    }
                default:
                    return null;
            }
        }

        public void logout(Angajat angajat)
        {
            var req = new Request.Builder().Type(RequestType.LOGOUT).Data(angajat).Build();
            SendRequest(req);
            var response = ReadResponse();
            CloseConnection();
            if (response.Type == ResponseType.ERROR)
            {
                Logger.Error("Error logging out" + response.Data);
                var err = response.Data.ToString();
                throw new MyException(err);
            }
            Logger.Info("Logged out");
        }

        public void addBilet(String cumparatorName, long idSpectacol, int nrSeats)
        {
            Bilet bilet = new Bilet(cumparatorName, idSpectacol, nrSeats);
            var req = new Request.Builder().Type(RequestType.ADD_BILET).Data(bilet).Build();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                Logger.Error("Error adding bilet" + response.Data.ToString());
                var err = response.Data.ToString();
                throw new MyException(err);
            }
            Logger.Info("Bilet added");
            Console.WriteLine("Bilet added");
        }

        public Angajat getAngajatByUsername(String username)
        {
            var req = new Request.Builder().Type(RequestType.GET_ANGAJAT).Data(username).Build();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                Logger.Error("Error finding angajat" + response.Data.ToString());
                var err = response.Data.ToString();
                throw new MyException(err);
            }
            Logger.Info("Found angajat");
            return (Angajat)response.Data;
        }

        public Spectacol getSpectacol(long id)
        {
            var req = new Request.Builder().Type(RequestType.GET_SPECTACOL).Data(id).Build();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                Logger.Error("Error finding spectacol" + response.Data.ToString());
                var err = response.Data.ToString();
                throw new MyException(err);
            }
            Logger.Info("Found spectacol");
            return (Spectacol)response.Data;
        }

        public Spectacol getSpectacolByAll(String artistName, String time, String place)
        {
            Spectacol spectacol = new Spectacol(artistName, "", time, place, 0, 0);
            var req = new Request.Builder().Type(RequestType.GET_SPECTACOL_BY_ALL).Data(spectacol).Build();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                Logger.Error("Error finding spectacol" + response.Data.ToString());
                var err = response.Data.ToString();
                throw new MyException(err);
            }
            Logger.Info("Found spectacol");
            return (Spectacol)response.Data;
        }

        public List<Angajat> getAllAngajati()
        {
            var req = new Request.Builder().Type(RequestType.GET_ALL_ANGAJATI).Data(null).Build();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                Logger.Error("Error getting angajati" + response.Data.ToString());
                Console.WriteLine("Error getting angajati" + response.Data.ToString());
                var err = response.Data.ToString();
                throw new MyException(err);
            }
            Logger.Info("Got angajati");
            Console.WriteLine("Got angajati");
            return (List<Angajat>)response.Data;
        }

        public List<Spectacol> getAllSpectacole()
        {
            var req = new Request.Builder().Type(RequestType.GET_ALL_SPECTACOLE).Data(null).Build();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                Logger.Error("Error getting spectacole" + response.Data.ToString());
                Console.WriteLine("Error getting spectacole" + response.Data.ToString());
                var err = response.Data.ToString();
                throw new MyException(err);
            }
            Logger.Info("Got spectacole");
            Console.WriteLine("Got spectacole");
            return (List<Spectacol>)response.Data;
        }
        public List<SpectacolDTO> getAllSpectacoleCautate(String date)
        {
            var req = new Request.Builder().Type(RequestType.GET_ALL_SPECTACOLE_CAUTATE).Data(date).Build();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == ResponseType.ERROR)
            {
                Logger.Error("Error getting spectacole" + response.Data.ToString());
                Console.WriteLine("Error getting spectacole" + response.Data.ToString());
                var err = response.Data.ToString();
                throw new MyException(err);
            }
            Logger.Info("Got spectacole");
            Console.WriteLine("Got spectacole");
            return (List<SpectacolDTO>)response.Data;
        }

        protected virtual void Run()
        {
            while (!finished)
            {
                try
                {
                    var response = formatter.Deserialize(stream);
                    Console.WriteLine("response received " + response);
                    if (IsUpdate((Response)response))
                    {
                        HandleUpdate((Response)response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue((Response)response);
                        }
                        waitHandle.Set();
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine("Reading error " + e);
                }
            }
        }
    }

}