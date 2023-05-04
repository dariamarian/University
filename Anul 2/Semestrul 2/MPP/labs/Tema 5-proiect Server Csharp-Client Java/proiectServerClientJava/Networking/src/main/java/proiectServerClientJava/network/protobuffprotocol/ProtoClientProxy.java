package proiectServerClientJava.network.protobuffprotocol;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import proiectServerClientJava.domain.*;
import proiectServerClientJava.service.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoClientProxy implements IService {
    private String host;
      private int port;

      private IObserver client;

      private InputStream input;
      private OutputStream output;
      private Socket connection;

      private BlockingQueue<ClientProtobufs.AppResponse> qresponses;
      private volatile boolean finished;
    private static final Logger logger = LogManager.getLogger();

    public ProtoClientProxy(String host, int port) {
          this.host = host;
          this.port = port;
          qresponses=new LinkedBlockingQueue<ClientProtobufs.AppResponse>();
      }

      public Angajat login(Angajat employee, IObserver client) throws MyException {
          initializeConnection();
          System.out.println("Login request ...");
          sendRequest(ProtoUtils.createLoginRequest(employee));
          ClientProtobufs.AppResponse response=readResponse();
          if (response.getType()==ClientProtobufs.AppResponse.Type.Ok){
              this.client=client;
              return ProtoUtils.getAngajat(response);
          }
          if (response.getType()==ClientProtobufs.AppResponse.Type.Error){
              String errorText=ProtoUtils.getError(response);
              closeConnection();
              throw new MyException(errorText);
          }
          return null;
      }

    public void addBilet(Bilet bilet) throws MyException {
        sendRequest(ProtoUtils.createAddBiletRequest(bilet));
        ClientProtobufs.AppResponse response=readResponse();
        if (response.getType()==ClientProtobufs.AppResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
        else {
            logger.info("Ticket added");
            System.out.println("Ticket added");
        }
    }

    public void logout(Angajat employee, IObserver client) throws MyException {
        sendRequest(ProtoUtils.createLogoutRequest(employee));
        ClientProtobufs.AppResponse response=readResponse();
        closeConnection();
        if (response.getType()==ClientProtobufs.AppResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            logger.error("Error logging out" + errorText);
            System.out.println("Error logging out" + errorText);
            throw new MyException(errorText);
        }
        else {
            logger.info("Logged out");
            System.out.println("Logged out");
        }
    }
    public Spectacol getSpectacol(Long id) throws MyException {
        sendRequest(ProtoUtils.createGetSpectacolRequest(id));
        ClientProtobufs.AppResponse response=readResponse();
        if (response.getType()==ClientProtobufs.AppResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
        Spectacol spectacol = ProtoUtils.getSpectacol(response);
        return spectacol;
    }

    public Iterable<Spectacol> getAllSpectacole() throws MyException {
        sendRequest(ProtoUtils.createGetAllSpectacoleRequest());
        ClientProtobufs.AppResponse response=readResponse();
        if (response.getType()==ClientProtobufs.AppResponse.Type.Error){
            String errorText=ProtoUtils.getError(response);
            throw new MyException(errorText);
        }
        Iterable<Spectacol> spectacole=ProtoUtils.getAllSpectacole(response);
        return spectacole;
    }

      private void closeConnection() {
          finished=true;
          try {
              input.close();
              output.close();
              connection.close();
              client=null;
              logger.info("Closed connection");
              System.out.println("Closed connection");
          } catch (IOException e) {
              logger.error("Error closing connection" + e);
              System.out.println("Error closing connection" + e);
          }

      }

      private void sendRequest(ClientProtobufs.AppRequest request)throws MyException{
          try {
              System.out.println("Sending request ..."+request);
              logger.info("Sending request ..." + request);
              request.writeDelimitedTo(output);
              output.flush();
              System.out.println("Request sent.");
              logger.info("Request sent.");
          } catch (IOException e) {
              logger.error("Error sending request " + e);
              throw new MyException("Error sending object "+e);
          }

      }

      private ClientProtobufs.AppResponse readResponse() throws MyException{
          ClientProtobufs.AppResponse response=null;
          try {
              response = qresponses.take();
              logger.info("Read response " + response);
          } catch (InterruptedException e) {
              logger.error("Error reading response " + e);
              throw new MyException("Error reading response " + e);
          }
          return response;
      }
      private void initializeConnection() throws MyException{
           try {
              connection=new Socket(host,port);
              output=connection.getOutputStream();
              input=connection.getInputStream();
              finished=false;
              startReader();
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
      private void startReader(){
          Thread tw=new Thread(new ReaderThread());
          tw.start();
      }


      private void handleUpdate(ClientProtobufs.AppResponse updateResponse){
          if (updateResponse.getType() == ClientProtobufs.AppResponse.Type.SoldBilet) {
              Bilet ticket = ProtoUtils.getBilet(updateResponse);
              logger.info("Ticket sold " + ticket);
              System.out.println("Ticket sold " + ticket);
              try {
                  client.biletAdded(ticket);
              } catch (MyException e) {
                  logger.error("Error handle update: " + e);
                  System.out.println("Error handle update: " + e);
              }
          }

      }
      private class ReaderThread implements Runnable{
          public void run() {
              while (!finished) {
                  try {
                      ClientProtobufs.AppResponse response = ClientProtobufs.AppResponse.parseDelimitedFrom(input);
                      System.out.println("response received " + response);
                      if (response != null) {
                          if (isUpdateResponse(response.getType())) {
                              handleUpdate(response);
                          } else {
                              try {
                                  qresponses.put(response);
                              } catch (InterruptedException e) {
                                  logger.error(e);
                              }
                          }
                      }
                  } catch (IOException e) {
                      System.out.println("Reading error " + e);
                  }
              }
          }
      }

    private boolean isUpdateResponse(ClientProtobufs.AppResponse.Type type){
        return type == ClientProtobufs.AppResponse.Type.SoldBilet;

    }
}
