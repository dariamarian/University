
import proiectServerClientJava.domain.Angajat;
import proiectServerClientJava.domain.Spectacol;
import proiectServerClientJava.domain.Bilet;
import proiectServerClientJava.domain.validators.AngajatValidator;
import proiectServerClientJava.domain.validators.BiletValidator;
import proiectServerClientJava.domain.validators.SpectacolValidator;
import proiectServerClientJava.domain.validators.Validator;
import proiectServerClientJava.network.utils.AbstractServer;
import proiectServerClientJava.network.utils.RpcConcurrentServer;
import proiectServerClientJava.network.utils.ServerException;
import proiectServerClientJava.persistence.repository.jdbc.RepoAngajat;
import proiectServerClientJava.persistence.repository.jdbc.RepoAngajatORM;
import proiectServerClientJava.persistence.repository.jdbc.RepoSpectacol;
import proiectServerClientJava.persistence.repository.jdbc.RepoBilet;
import proiectServerClientJava.server.Service;
import proiectServerClientJava.service.IService;

import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;

    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }
        Validator<Angajat> angajatValidator=new AngajatValidator();
        Validator<Spectacol> spectacolValidator=new SpectacolValidator();
        Validator<Bilet> biletValidator=new BiletValidator();
        RepoAngajatORM angajatRepository = new RepoAngajatORM();
        RepoSpectacol spectacolRepository = new RepoSpectacol(serverProps);
        RepoBilet biletRepository = new RepoBilet(serverProps);
        IService chatServerImpl=new Service(angajatValidator,spectacolValidator,biletValidator, angajatRepository, spectacolRepository, biletRepository);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+chatServerPort);
        AbstractServer server = new RpcConcurrentServer(chatServerPort, chatServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}