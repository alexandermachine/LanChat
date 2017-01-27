package ru.Voloshinskiy.Java2.Lesson6.Server;

import ru.Voloshinskiy.Java2.Lesson6.Client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 * Created by Alexander V on 14.01.2017.
 */
public class Server {
    private ServerSocket serverSocket = null;
    private Socket socket = null;
    private List<ClientHandler> clients = new ArrayList<>();
    private int nClients = 0;
    private int serverPort;

    public Server(int serverPort, String dbName){
        this.serverPort = serverPort;
        try {
            serverSocket = new ServerSocket(this.serverPort);
            System.out.println("server is started");
            SQLHandler.connect(dbName);
            listenToClients();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void sendAll(String msg){
        for (ClientHandler ch: clients) {
            ch.sendMsg(msg);
        }
    }
    private void listenToClients() throws IOException {
        while(true) {


            socket = serverSocket.accept();

            System.out.println("client conected");
            clients.add(new ClientHandler(socket,this));

            clients.get(nClients).setClients(clients);
            new Thread(clients.get(nClients)).start();
            nClients++;
        }
    }
}
