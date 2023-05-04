package proiectServerClientJava.network.utils;


import proiectServerClientJava.network.protobuffprotocol.ProtoClientWorker;
import proiectServerClientJava.service.IService;

import java.net.Socket;

public class ProtobuffConcurrentServer extends AbsConcurrentServer{
    private IService chatServer;
    public ProtobuffConcurrentServer(int port, IService chatServer) {
        super(port);
        this.chatServer = chatServer;
        System.out.println("Chat- ChatProtobuffConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {
        ProtoClientWorker worker=new ProtoClientWorker(chatServer,client);
        Thread tw=new Thread(worker);
        return tw;
    }
}
