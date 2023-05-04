using log4net;
using proiectSCcsharp.networking.utils;
using proiectSCcsharp.services;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;
using Chat.Protocol;
using Google.Protobuf;
using protobuf;

namespace proiectSCcsharp.networking.utils
{
    public class ProtobuffConcurrentServer:AbsConcurrentServer
    {
        private readonly IService service;
        private static readonly ILog logger = LogManager.GetLogger("RpcConcurrentServer");

        private ProtoClientWorker worker;
        public ProtobuffConcurrentServer(string host, int port, IService service) : base(host, port)
        {
            this.service = service;
            logger.Info("ProtobuffConcurrentServer created");
            Console.WriteLine(@"ProtobuffConcurrentServer created");
        }
        protected override Thread CreateWorker(TcpClient client)
        {
            worker = new ProtoClientWorker(service, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
