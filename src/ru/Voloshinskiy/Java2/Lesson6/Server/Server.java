package ru.Voloshinskiy.Java2.Lesson6.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Alexander V on 14.01.2017.
 */
public class Server {

    public static void main(String[] args){
        ServerSocket serverSocket = null;
        Socket  socket = null;
        ClientHandler connection;
        try {
            while(true) {
                serverSocket = new ServerSocket(8189);
                System.out.println("server is started");
                socket = serverSocket.accept();
                System.out.println("client conected");
                connection = new ClientHandler(socket);
                Thread tReceiveMessages = new Thread(connection);
                tReceiveMessages.start();


                tReceiveMessages.join();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
}
