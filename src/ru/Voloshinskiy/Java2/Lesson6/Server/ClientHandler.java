package ru.Voloshinskiy.Java2.Lesson6.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Alexander V on 16.01.2017.
 */
public class ClientHandler implements Runnable {
    Socket socket= null;
    PrintWriter dataOutputStream = null;
    Scanner dataInputStream = null;
    public ClientHandler(Socket s) throws IOException {
        socket = s;
        dataOutputStream = new PrintWriter(socket.getOutputStream());
        dataInputStream = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {
        System.out.println("Tread is run");
        sendMsg("Servers Tread is run");
            while(true){
                if (dataInputStream.hasNext()){
                    String msg = dataInputStream.nextLine();
                    sendMsg(msg);
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
