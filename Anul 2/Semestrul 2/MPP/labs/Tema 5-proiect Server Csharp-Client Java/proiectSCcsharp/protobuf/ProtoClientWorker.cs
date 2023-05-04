using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Chat.Protocol;
using Google.Protobuf;
using proiectSCcsharp.services;

namespace protobuf
{
    public class ProtoClientWorker:IObserver
    {
        private IService server;
        private TcpClient connection;

        private NetworkStream stream;
        //private IFormatter formatter;
        private volatile bool connected;
        public ProtoClientWorker(IService server, TcpClient connection)
        {
            this.server = server;
            this.connection = connection;
            try
            {

                stream = connection.GetStream();
                //      formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void run()
        {
            while (connected)
            {
                try
                {
                    AppRequest request = AppRequest.Parser.ParseDelimitedFrom(stream);
                    AppResponse response = handleRequest(request);
                    if (response != null)
                    {
                        sendResponse(response);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }

                try
                {
                    Thread.Sleep(1000);
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.StackTrace);
                }
            }
            try
            {
                stream.Close();
                connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Error " + e);
            }
        }
        public virtual void BiletAdded(proiectSCcsharp.domain.Bilet bilet)
        {
            Console.WriteLine("Bilet added  " + bilet);
            try
            {
                sendResponse(ProtoUtils.createAddBiletResponse(bilet));
            }
            catch (Exception e)
            {
                throw new MyException("Sending error: " + e);
            }
        }

        private AppResponse handleRequest(AppRequest request)
        {
            AppResponse response = null;
            AppRequest.Types.Type reqType = request.Type;
            switch (reqType)
            {
                case AppRequest.Types.Type.Login:
                    {
                        Console.WriteLine("Login request ...");
                        proiectSCcsharp.domain.Angajat angajat = ProtoUtils.GetAngajat(request);
                        try
                        {
                            lock (server)
                            {
                                return ProtoUtils.createLoginResponse(server.login(angajat, this));
                            }
                        }
                        catch (MyException e)
                        {
                            connected = false;
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case AppRequest.Types.Type.Logout:
                    {
                        Console.WriteLine("Logout request");
                        proiectSCcsharp.domain.Angajat angajat = ProtoUtils.GetAngajat(request);
                        try
                        {
                            lock (server)
                            {
                                server.logout(angajat);
                            }
                            connected = false;
                            return ProtoUtils.createOkResponse();

                        }
                        catch (MyException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
                case AppRequest.Types.Type.AddBilet:
                    {
                        Console.WriteLine("AddBiletRequest ...");
                        proiectSCcsharp.domain.Bilet bilet = ProtoUtils.GetBilet(request);
                        try
                        {
                            lock (server)
                            {
                                server.addBilet(bilet);
                            }
                            return ProtoUtils.createOkResponse();
                        }
                        catch (MyException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }

                case AppRequest.Types.Type.GetSpectacol:
                    {
                        Console.WriteLine("GetSpectacol Request ...");
                        try
                        {
                            lock (server)
                            {
                                return ProtoUtils.createGetSpectacolResponse(
                                server.getSpectacol(ProtoUtils.getIdSpectacol(request)));
                            }
                        }
                        catch (MyException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
           
                case AppRequest.Types.Type.GetAllSpectacole:
                    {
                        Console.WriteLine("GetAllSpectacole Request ...");
                        try
                        {
                            lock (server)
                            {
                                return ProtoUtils.createGetAllSpectacoleResponse(server.getAllSpectacole());
                            }
                        }
                        catch (MyException e)
                        {
                            return ProtoUtils.createErrorResponse(e.Message);
                        }
                    }
            }
            return response;
        }

        private void sendResponse(AppResponse response)
        {
            Console.WriteLine("sending response " + response);
            lock (stream)
            {
                response.WriteDelimitedTo(stream);
                stream.Flush();
            }
        }
    }
}
