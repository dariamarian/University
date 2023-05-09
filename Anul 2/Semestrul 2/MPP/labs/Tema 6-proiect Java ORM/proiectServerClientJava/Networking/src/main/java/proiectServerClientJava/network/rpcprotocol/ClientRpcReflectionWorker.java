package proiectServerClientJava.network.rpcprotocol;

import proiectServerClientJava.domain.*;
import proiectServerClientJava.domain.validators.ValidationException;
import proiectServerClientJava.service.IObserver;
import proiectServerClientJava.service.IService;
import proiectServerClientJava.service.MyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;


public class ClientRpcReflectionWorker implements Runnable, IObserver {
    private final IService service;
    private final Socket connection;

    private static final Logger logger= LogManager.getLogger();

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientRpcReflectionWorker(IService service, Socket connection) {
        logger.info("Creating worker");
        this.service = service;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
            logger.info("Worker created");
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                logger.error("Error in worker (reading): "+e);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Error in worker (sleeping): "+e);
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            logger.error("Error in worker (closing connection): "+e);
        }
    }

    private static final Response okResponse=new Response.Builder().type(ResponseType.OK).build();
    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        logger.traceEntry("method entered: "+handlerName+" with parameters "+request);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            logger.info("Method invoked: "+handlerName+" with response "+response);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            logger.error("Error in worker (invoking method handleRequest): "+e);
        }
        return response;
    }

    private Response handleADD_BILET(Request request){
        logger.traceEntry("method entered: handleADD_BILET with parameters "+request);
        Bilet bilet=(Bilet) request.data();
        try {
            service.addBilet(bilet);
            logger.info("Bilet bought");
            return new Response.Builder().type(ResponseType.OK).data(bilet).build();
        } catch (MyException e) {
            logger.error("Error in worker (solving method handleADD_BILET): "+e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
    }

    private Response handleGET_SPECTACOL(Request request) {
        logger.traceEntry("method entered: handleGET_SPECTACOL with parameters " + request);
        Long id = (Long) request.data();
        try {
            Spectacol spectacol = service.getSpectacol(id);
            logger.info("Spectacol found " + spectacol);
            return new Response.Builder().type(ResponseType.GET_SPECTACOL).data(spectacol).build();
        } catch (MyException e) {
            logger.error("Error in worker (solving method handleGET_SPECTACOL): " + e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleGET_ALL_SPECTACOLE(Request request){
        logger.traceEntry("method entered: handleGET_ALL_SPECTACOLE with parameters "+request);
        try {
            List<Spectacol> spectacole= (List<Spectacol>) service.getAllSpectacole();
            logger.info("Spectacole found "+spectacole);
            return new Response.Builder().type(ResponseType.GET_ALL_SPECTACOLE).data(spectacole).build();
        } catch (MyException e) {
            logger.error("Error in worker (solving method handleGET_ALL_SPECTACOLE): "+e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }
    private Response handleLOGIN(Request request){
        logger.traceEntry("method entered: handleLOGIN with parameters "+request);
        Angajat employee = (Angajat) request.data();
        try {
            Angajat foundEmployee = service.login(employee, this);
            logger.info("Employee logged in");
            return new Response.Builder().type(ResponseType.OK).data(foundEmployee).build();
        } catch (MyException e) {
            connected=false;
            logger.error("Error in worker (solving method handleLOGIN): "+e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Response handleLOGOUT(Request request){
        logger.traceEntry("method entered: handleLOGOUT with parameters "+request);
        Angajat employee=(Angajat) request.data();
        try {
            service.logout(employee, this);
            connected=false;
            logger.info("User logged out");
            return okResponse;
        } catch (MyException e) {
            logger.error("Error in worker (solving method handleLOGOUT): "+e);
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void sendResponse(Response response) throws IOException{
        logger.traceEntry("method entered: sendResponse with parameters "+response);
        output.writeObject(response);
        output.flush();
        logger.info("Response sent");
    }

    @Override
    public void biletAdded(Bilet bilet) throws MyException {
        Response resp=new Response.Builder().type(ResponseType.SOLD_BILET).data(bilet).build();
        logger.info("Bilet sold "+bilet);
        try {
            sendResponse(resp);
            logger.info("Response sent");
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error in worker (sending response): "+e);
        }
    }
}