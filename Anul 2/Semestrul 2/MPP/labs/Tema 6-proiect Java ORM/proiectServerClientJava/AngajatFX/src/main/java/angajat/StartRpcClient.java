package angajat;

import proiectServerClientJava.network.rpcprotocol.ServicesRpcProxy;
import proiectServerClientJava.service.IService;
import angajat.controller.LogInController;
import angajat.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartRpcClient extends Application {
    private static int defaultChatPort = 55555;
    private static String defaultServer = "localhost";

    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartRpcClient.class.getResourceAsStream("/angajat.properties"));
            System.out.println("Employee properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find employee.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("server.host", defaultServer);
        int serverPort = defaultChatPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultChatPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IService service = new ServicesRpcProxy(serverIP, serverPort);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 210, 500);
        LogInController loginController = fxmlLoader.getController();
        loginController.SetService(service);

        FXMLLoader cloader = new FXMLLoader(getClass().getResource("MainInterface.fxml"));
        Parent parent=cloader.load();
        MainController mainController = cloader.getController();
        mainController.setService(service);
        loginController.setMainController(mainController);
        loginController.setParent(parent);

        primaryStage.setTitle("MPP server-client app");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}