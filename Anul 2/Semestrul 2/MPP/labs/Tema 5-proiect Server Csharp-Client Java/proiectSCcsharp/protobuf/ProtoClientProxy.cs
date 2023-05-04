using proiectSCcsharp.services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;
using Chat.Protocol;
using Google.Protobuf;

namespace protobuf
{
    internal class ProtoClientProxy:IService
    {
        private readonly string host;
        private readonly int port;
        private IObserver employeeObserver;
        private NetworkStream stream;
        private IFormatter formatter;
        private TcpClient connection;
        private readonly Queue<AppResponse> responses;
        private volatile bool finished;
        private EventWaitHandle waitHandle;

        public ProtoClientProxy(string host, int port)
        {
            this.host = host;
            this.port = port;
            responses = new Queue<AppResponse>();
        }

        private void CloseConnection()
        {
            finished = true;
            try
            {
                stream.Close();
                connection.Close();
                waitHandle.Close();
                employeeObserver = null;
                Console.WriteLine("Connection closed!");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        private void SendRequest(AppRequest request)
        {
            try
            {
                request.WriteDelimitedTo(stream);
                stream.Flush();
            }
            catch (Exception e)
            {
                throw new MyException("Error sending object " + e);
            }
        }

        private AppResponse ReadResponse()
        {
            AppResponse response = null;
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

        private void HandleUpdate(AppResponse response)
        {
            if (response.Type != AppResponse.Types.Type.SoldBilet) return;
            var ticket = ProtoUtils.GetBilet(response);
            Console.WriteLine("Ticket sold " + ticket);
            try
            {
                employeeObserver.BiletAdded(ticket);
            }
            catch (MyException e)
            {
                Console.WriteLine("Error handle update: " + e);
            }
        }

        private static bool IsUpdate(AppResponse response)
        {
            return response.Type == AppResponse.Types.Type.SoldBilet;
        }

        public proiectSCcsharp.domain.Angajat login(proiectSCcsharp.domain.Angajat employee, IObserver employeeObserver)
        {
            InitializeConnection();
            Console.WriteLine("Login request ...");
            var req = ProtoUtils.createLoginRequest(employee);
            SendRequest(req);
            var response = ReadResponse();
            switch (response.Type)
            {
                case AppResponse.Types.Type.Ok:
                    this.employeeObserver = employeeObserver;
                    Console.WriteLine("Logged in");
                    return ProtoUtils.GetAngajat(response);
                case AppResponse.Types.Type.Error:
                    {
                        var err = ProtoUtils.getError(response);
                        Console.WriteLine("Error logging in" + err);
                        CloseConnection();
                        throw new MyException(err);
                    }
                default:
                    return null;
            }
        }

        public void logout(proiectSCcsharp.domain.Angajat employee)
        {
            var req = ProtoUtils.createLogoutRequest(employee);
            SendRequest(req);
            var response = ReadResponse();
            CloseConnection();
            if (response.Type == AppResponse.Types.Type.Error)
            {
                var err = ProtoUtils.getError(response);
                Console.WriteLine("Error logging out" + err);
                throw new MyException(err);
            }
            Console.WriteLine("Logged out");
        }

        public void addBilet(proiectSCcsharp.domain.Bilet bilet)
        {
            var req = ProtoUtils.createAddBiletRequest(bilet);
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == AppResponse.Types.Type.Error)
            {
                var err = ProtoUtils.getError(response);
                Console.WriteLine("Error adding ticket" + err);
                throw new MyException(err);
            }
            Console.WriteLine("Ticket added");
        }

        public List<proiectSCcsharp.domain.Spectacol> getAllSpectacole()
        {
            var req = ProtoUtils.createGetAllSpectacoleRequest();
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == AppResponse.Types.Type.Error)
            {
                var err = ProtoUtils.getError(response);
                Console.WriteLine("Error getting shows" + err);
                throw new MyException(err);
            }
            Console.WriteLine("Got shows");
            return ProtoUtils.getAllSpectacole(response);
        }

        public proiectSCcsharp.domain.Spectacol getSpectacol(long id)
        {
            var req = ProtoUtils.createGetSpectacolRequest(id);
            SendRequest(req);
            var response = ReadResponse();
            if (response.Type == AppResponse.Types.Type.Error)
            {
                var err = ProtoUtils.getError(response);
                Console.WriteLine("Error finding spectacol" + err);
                throw new MyException(err);
            }
            Console.WriteLine("Found spectacol");
            return ProtoUtils.GetSpectacol(response);
        }

        protected virtual void Run()
        {
            while (!finished)
            {
                try
                {
                    var response = AppResponse.Parser.ParseDelimitedFrom(stream);
                    Console.WriteLine("response received " + response);
                    if (IsUpdate((AppResponse)response))
                    {
                        HandleUpdate((AppResponse)response);
                    }
                    else
                    {
                        lock (responses)
                        {
                            responses.Enqueue((AppResponse)response);
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
