package ru.Voloshinskiy.Java2.Lesson6.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander V on 14.01.2017.
 */
public class Server {
    private ServerSocket serverSocket = null;
    private Socket  socket = null;
    private List<ClientHandler> clients = new ArrayList<>();
    private int nClients = 0;
    private int serverPort;

    public Server(int serverPort){
        this.serverPort = serverPort;
        try {
            serverSocket = new ServerSocket(this.serverPort);
            System.out.println("server is started");
            listenToClients();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
                System.out.println("server is closed");
                if (socket != null) socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
