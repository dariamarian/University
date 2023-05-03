using log4net.Repository.Hierarchy;
using log4net;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using proiectSCcsharp.services;

namespace proiectSCcsharp.networking.utils
{
    public class RpcConcurrentServer : AbsConcurrentServer
    {
        private readonly IService service;
        private static readonly ILog logger = LogManager.GetLogger("RpcConcurrentServer");

        private ClientRpcReflectionWorker worker;
        public RpcConcurrentServer(string host, int port, IService service) : base(host, port)
        {
            this.service = service;
            logger.Info("RpcConcurrentServer created");
            Console.WriteLine(@"RpcConcurrentServer created");
        }
        protected override Thread CreateWorker(TcpClient client)
        {
            worker = new ClientRpcReflectionWorker(service, client);
            return new Thread(new ThreadStart(worker.Run));
        }

    }
}