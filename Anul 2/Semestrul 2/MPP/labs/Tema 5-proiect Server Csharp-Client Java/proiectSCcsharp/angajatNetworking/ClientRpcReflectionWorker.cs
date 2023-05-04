using System;
using System.Collections.Generic;
using System.Net.Sockets;
using System.Runtime.Serialization.Formatters.Binary;
using System.Runtime.Serialization;
using System.Threading;
using proiectSCcsharp.services;
using proiectSCcsharp.domain;
using System.Reflection;
using log4net;
using System.IO;
using proiectSCcsharp.networking;

namespace proiectSCcsharp.networking
{
    public class ClientRpcReflectionWorker : IObserver //, Runnable
    {

        private static readonly ILog Logger = LogManager.GetLogger("ClientRpcReflectionWorker");

        private readonly IService service;
        private readonly TcpClient connection;
        private readonly NetworkStream stream;
        private readonly IFormatter formatter;
        private volatile bool connected;
        public ClientRpcReflectionWorker(IService service, TcpClient connection)
        {
            this.service = service;
            this.connection = connection;
            try
            {
                stream = connection.GetStream();
                formatter = new BinaryFormatter();
                connected = true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
            }
        }

        public virtual void Run()
        {
            while (connected)
            {
                try
                {
                    var request = formatter.Deserialize(stream);
                    object response = HandleRequest((Request)request);
                    if (response != null)
                    {
                        SendResponse((Response)response);
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

        private Response HandleRequest(Request request)
        {
            Response response = null;
            var handlerName = "Handle" + request.Type;
            Console.WriteLine("HandlerName " + handlerName);
            try
            {
                var method = this.GetType().GetMethod(handlerName, new[] { typeof(Request) });
                response = (Response)method.Invoke(this, new[] { request });
                Console.WriteLine("Method " + handlerName + " invoked");
            }
            catch (TargetInvocationException ex)
            {
                Console.WriteLine(ex.InnerException);
            }
            catch (AmbiguousMatchException ex)
            {
                Console.WriteLine(ex.Message);
            }
            catch (TargetParameterCountException ex)
            {
                Console.WriteLine(ex.Message);
            }
            catch (TargetException ex)
            {
                Console.WriteLine(ex.Message);
            }
            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            return response;
        }

        public Response HandleLOGIN(Request request)
        {
            Logger.InfoFormat("method entered: handleLOGIN with parameters {0}", request);
            Angajat angajat = (Angajat)request.Data;
            try
            {
                var foundAngajat = service.login(angajat, this);
                Logger.Info("Angajat logged in");
                return new Response.Builder().Type(ResponseType.OK).Data(foundAngajat).Build();
            }
            catch (MyException e)
            {
                connected = false;
                Logger.Error("Error in worker (solving method handleLOGIN): {0}", e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        public Response HandleLOGOUT(Request request)
        {
            Logger.InfoFormat("method entered: handleLOGOUT with parameters {0}", request);
            Angajat angajat = (Angajat)request.Data;
            try
            {
                service.logout(angajat);
                connected = false;
                Logger.Info("User logged out");
                return new Response.Builder().Type(ResponseType.OK).Build(); ;
            }
            catch (MyException e)
            {
                Logger.Error("Error in worker (solving method handleLOGOUT): {0}", e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        public Response HandleADD_BILET(Request request)
        {
            Logger.InfoFormat("method entered: handleADD_BILET with parameters {0}", request);
            Bilet bilet = (Bilet)request.Data;
            try
            {
                service.addBilet(bilet);
                Logger.Info("Bilet bought");
                return new Response.Builder().Type(ResponseType.OK).Data(bilet).Build();
            }
            catch (MyException e)
            {
                Logger.Error("Error in worker (solving method handleADD_BILET): {0}", e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }
        public Response HandleGET_SPECTACOL(Request request)
        {

            Logger.InfoFormat("method entered: handleGET_SPECTACOL with parameters {0}", request);
            Console.WriteLine("method entered: handleGET_SPECTACOL with parameters");

            long id = (long)request.Data;
            try
            {
                var spectacol = service.getSpectacol(id);
                Logger.InfoFormat("Spectacol found {0}", spectacol);
                Console.WriteLine("Spectacol found " + spectacol);
                return new Response.Builder().Type(ResponseType.GET_SPECTACOL).Data(spectacol).Build();
            }
            catch (MyException e)
            {
                Logger.Error("Error in worker (solving method handleGET_SPECTACOL): {0}", e);
                Console.WriteLine("Error in worker (solving method handleGET_SPECTACOL): " + e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }
        public Response HandleGET_ALL_SPECTACOLE(Request request)
        {
            Logger.InfoFormat("method entered: handleGET_ALL_SPECTACOLE with parameters {0}", request);
            Console.WriteLine("method entered: handleGET_ALL_SPECTACOLE with parameters");
            try
            {
                List<Spectacol> spectacole = service.getAllSpectacole();
                Logger.InfoFormat("Spectacole found {0}", spectacole);
                Console.WriteLine(@"Spectacole found {0}", spectacole);
                return new Response.Builder().Type(ResponseType.GET_ALL_SPECTACOLE).Data(spectacole).Build();
            }
            catch (MyException e)
            {
                Logger.Error("Error in worker (solving method handleGET_ALL_SPECTACOLE): {0}", e);
                Console.WriteLine(@"Error in worker (solving method handleGET_ALL_SPECTACOLE): {0}", e);
                return new Response.Builder().Type(ResponseType.ERROR).Data(e.Message).Build();
            }
        }

        private void SendResponse(Response response)
        {
            Console.WriteLine("sending response " + response);
            lock (stream)
            {
                formatter.Serialize(stream, response);
                stream.Flush();
            }
        }

        public void BiletAdded(Bilet bilet)
        {
            var resp = new Response.Builder().Type(ResponseType.SOLD_BILET).Data(bilet).Build();
            Logger.Info($"Bilet sold {bilet}");
            Console.WriteLine($@"Bilet sold {bilet}");
            try
            {
                SendResponse(resp);
                Logger.Info("Response sent");
                Console.WriteLine("Response sent");
            }
            catch (IOException e)
            {
                Console.WriteLine(e);
                Logger.Error($"Error in worker (sending response): {e}");
                Console.WriteLine(@"Error in worker (sending response): {0}", e);
            }
        }
    }

}