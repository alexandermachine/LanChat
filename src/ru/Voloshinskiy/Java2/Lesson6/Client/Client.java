package ru.Voloshinskiy.Java2.Lesson6.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Alexander V on 15.01.2017.
 */
public class Client {
    public static final int SERVER_PORT = 8189;
    public static final String SERVER_HOST = "127.0.0.1";
    public static void main(String[] args){
        System.out.println("Client app starts");
        try {
            Window wind =  new Window(SERVER_HOST, SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            Socket socket = new Socket(SERVER_HOST,SERVER_PORT);
//            ConnectToServer connection = new ConnectToServer(socket);
//            System.out.println("connect is created");
//            Thread tReceiveMessages = new Thread(connection);
//            tReceiveMessages.start();
//
//
//            Thread tSendMessages = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true){
//                        Scanner console = new Scanner(System.in);
//                        String msg;
//                        msg = console.nextLine();
//                        connection.sendMsg(msg);
//                        if (msg.equalsIgnoreCase("END")){
//                            try {
//                                socket.close();
//                                if (tReceiveMessages.isAlive())tReceiveMessages.interrupt();
//                                System.out.println("socket close");
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                            break;
//                        }
//
//                    }
//                }
//            });
//            tSendMessages.start();
//
//
//            tReceiveMessages.join();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        System.out.println("Client app ends");
    }

}
