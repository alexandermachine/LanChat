package ru.Voloshinskiy.Java2.Lesson6.Client;

import com.sun.deploy.uitoolkit.ui.ConsoleController;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Alexander V on 14.01.2017.
 */
public class ConnectToServer {
    private Socket socket = null;
    private Scanner dataInputStream = null;
    private PrintWriter dataOutputStream = null;
//    Scanner console = new Scanner(System.in);
//    String name = null;
//    Window clientGUI = null;
    public ConnectToServer(String SERVER_HOST, int SERVER_PORT) throws IOException {
        socket = new Socket(SERVER_HOST,SERVER_PORT);;
        dataInputStream = new Scanner(socket.getInputStream());
        dataOutputStream = new PrintWriter(socket.getOutputStream());
    }


//    @Override
//    public void run() {
//        System.out.println("Tread is run");
//
//        while(true){
//            if (dataInputStream.hasNext()){
//                System.out.println(dataInputStream.nextLine());
//            }
//        }
//    }
//    public void sendMsg(String msg){
//        dataOutputStream.println(name + ": " + msg);
//        dataOutputStream.flush();
//    }

    public void sendMsg(String msg){
        dataOutputStream.println(msg);
        dataOutputStream.flush();
    }

    public Scanner getDataInputStream(){
        return dataInputStream;
    }

    public PrintWriter getDataOutputStream() {
        return dataOutputStream;
    }
}