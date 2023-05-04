package proiectServerClientJava.network.protobuffprotocol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import proiectServerClientJava.domain.Angajat;
import proiectServerClientJava.domain.Bilet;
import proiectServerClientJava.domain.Spectacol;
import proiectServerClientJava.domain.validators.ValidationException;
import proiectServerClientJava.service.IObserver;
import proiectServerClientJava.service.IService;
import proiectServerClientJava.service.MyException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;


public class ProtoClientWorker implements Runnable, IObserver {
     private IService server;
     private Socket connection;

     private InputStream input;
     private OutputStream output;
     private volatile boolean connected;

     private static final Logger logger = LogManager.getLogger();
    public ProtoClientWorker(IService server, Socket connection) {
         this.server = server;
         this.connection = connection;
         try{
             output=connection.getOutputStream() ;//new ObjectOutputStream(connection.getOutputStream());
             input=connection.getInputStream(); //new ObjectInputStream(connection.getInputStream());
             connected=true;
             logger.info("Worker created");
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void run() {

         while(connected){
             try {
                 System.out.println("Waiting requests ...");
                 ClientProtobufs.AppRequest request=ClientProtobufs.AppRequest.parseDelimitedFrom(input);
                 System.out.println("Request received: "+request);
                 ClientProtobufs.AppResponse response=handleRequest(request);
                 if (response!=null){
                    sendResponse(response);
                 }
             } catch (IOException e) {
                 e.printStackTrace();
             }
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
         try {
             input.close();
             output.close();
             connection.close();
         } catch (IOException e) {
             System.out.println("Error "+e);
         }
     }

     public void biletAdded(Bilet bilet) throws MyException {
         System.out.println("Bilet added  "+bilet);
         try {
             sendResponse(ProtoUtils.createAddBiletResponse(bilet));
         } catch (IOException e) {
             throw new MyException("Sending error: "+e);
         }
     }

     private ClientProtobufs.AppResponse handleRequest(ClientProtobufs.AppRequest request){
         ClientProtobufs.AppResponse response=null;
         switch (request.getType()){
             case Login:{
                 System.out.println("Login request ...");
                 Angajat angajat=ProtoUtils.getAngajat(request);
                 try {
                     Angajat found = server.login(angajat, this);
                     return ProtoUtils.createLoginResponse(found);
                 } catch (MyException e) {
                     connected=false;
                     return ProtoUtils.createErrorResponse(e.getMessage());
                 } catch (Exception e) {
                     throw new RuntimeException(e);
                 }
             }
             case Logout:{
                 System.out.println("Logout request");
                 Angajat angajat=ProtoUtils.getAngajat(request);
                 try {
                     server.logout(angajat, this);
                     connected=false;
                     return ProtoUtils.createOkResponse();
                 } catch (MyException e) {
                     return ProtoUtils.createErrorResponse(e.getMessage());
                 } catch (Exception e) {
                     throw new RuntimeException(e);
                 }
             }
             case AddBilet: {
                 System.out.println("AddBiletRequest ...");
                 Bilet bilet = ProtoUtils.getBilet(request);
                 try {
                     server.addBilet(bilet);
                     System.out.println("Bilet added");
                     return ProtoUtils.createOkResponse();
                 } catch (MyException e) {
                     return ProtoUtils.createErrorResponse(e.getMessage());
                 } catch (ValidationException e) {
                     throw new RuntimeException(e);
                 }
             }
             case GetSpectacol: {
                 System.out.println("GetSpectacol Request ...");
                 Long id = ProtoUtils.getIdSpectacol(request);
                 try {
                     Spectacol spectacol = server.getSpectacol(id);
                     return ProtoUtils.createGetSpectacolResponse(spectacol);
                 } catch (MyException e) {
                     return ProtoUtils.createErrorResponse(e.getMessage());
                 }
             }
             case GetAllSpectacole:{
                 System.out.println("GetAllSpectacole Request ...");
                 try {
                     List<Spectacol> spectacole= (List<Spectacol>)server.getAllSpectacole();
                     return ProtoUtils.createGetAllSpectacoleResponse(spectacole);
                 } catch (MyException e) {
                     return ProtoUtils.createErrorResponse(e.getMessage());
                 }
             }
         }
         return response;
     }

     private void sendResponse(ClientProtobufs.AppResponse response) throws IOException{
         System.out.println("sending response "+response);
         response.writeDelimitedTo(output);
         output.flush();
     }
}
