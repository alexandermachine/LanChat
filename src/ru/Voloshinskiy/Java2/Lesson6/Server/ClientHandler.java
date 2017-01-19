package ru.Voloshinskiy.Java2.Lesson6.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Alexander V on 16.01.2017.
 */
public class ClientHandler implements Runnable {
    private Socket socket= null;
    private PrintWriter dataOutputStream = null;
    private Scanner dataInputStream = null;
    private List<ClientHandler> clients = null;
    private Server server;
    public ClientHandler(Socket socket, Server server) throws IOException {
        this.server = server;
        this.socket = socket;
        dataOutputStream = new PrintWriter(socket.getOutputStream());
        dataInputStream = new Scanner(socket.getInputStream());

    }

    public void setClients(List<ClientHandler> clients) {
        this.clients = clients;
    }

    @Override
    public void run() {

        System.out.println("Tread is run");
        sendMsg("Соеденение установленно");
            while(true){
                if (dataInputStream.hasNext()){
                    String msg = dataInputStream.nextLine();
                    Thread tMessageSender = new Thread(new MessageSender(msg,server));
                    tMessageSender.start();

                    if (msg.equalsIgnoreCase("END"))
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
    }

    public void sendMsg(String msg){
        dataOutputStream.println(msg);
        dataOutputStream.flush();
    }
}
